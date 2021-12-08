package com.corona.exam;

import com.corona.exam.csv.CsvWriter;
import com.corona.exam.db.SqliteConnector;
import com.corona.exam.model.Country;
import com.corona.exam.model.Region;
import com.corona.exam.scraping.Scraper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Processor {

    private static Logger logger = LoggerFactory.getLogger(Processor.class);

    SqliteConnector sqliteConnector;

    Scraper scraper;

    CsvWriter csvWriter;


    public Processor(SqliteConnector sqliteConnector, Scraper scraper, CsvWriter csvWriter) {
        this.sqliteConnector = sqliteConnector;
        this.scraper = scraper;
        this.csvWriter = csvWriter;
    }


    public void process() throws IOException {

        String region = askUserForRegion();

        ZoneId zonedId = ZoneId.of( "America/Montreal" );

        LocalDate today = LocalDate.now( zonedId );

        StringBuffer response = scraper.getDataFromUrl();

        String[][] table = scraper.parseTable(response);

        sqliteConnector.connect();

        int todaysCount = sqliteConnector.checkIfTodaysDataAlreadyInDb(today);

        if(todaysCount == 0) {
            sqliteConnector.insertCountries(table, today);
        }

        List<Country> countries = sqliteConnector.getCountriesFromRegion(region);

        writeToConsole(countries);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yy_MM_dd");

        csvWriter.createCsv(region, today.format(dateTimeFormatter), countries);

        sqliteConnector.closeConnection();
    }

    private String askUserForRegion() {
        logger.info("Enter region");

        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(Region.values()).forEach(name -> {
            stringBuilder.append(name.getRegionName());
            stringBuilder.append(" ");
        });

        Scanner sc = new Scanner(System.in);

        String region = sc.nextLine();
        while (!regionIsValid(region)){
            logger.info("Enter valid region, available choices are " + stringBuilder);
            region = sc.nextLine();
        }

        return region;
    }

    private boolean regionIsValid(String region){
      return  Arrays.stream(Region.values()).anyMatch(regi-> regi.getRegionName().equalsIgnoreCase(region));
    }

    private void writeToConsole(List<Country> countries){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        stringBuilder.append(" | COUNTRY | TOTAL_CASES | TOTAL_TESTS | ACTIVE_CASES | \n" );
        countries.forEach(country ->      stringBuilder.append(" | " + country.getName() + " | " +  country.getTotalCases() + " | " +
                country.getTotalTests() + " | " + country.getActiveCases() + " | " + "\n"));
        logger.info(stringBuilder.toString());
    }
}
