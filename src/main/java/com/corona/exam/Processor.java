package com.corona.exam;

import com.corona.exam.csv.CsvWriter;
import com.corona.exam.db.SqliteConnector;
import com.corona.exam.model.Country;
import com.corona.exam.scrapping.Scraper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

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


        StringBuffer response = scraper.getDataFromUrl();

        String[][] table = scraper.parseTable(response);

        SqliteConnector sqliteConnector = new SqliteConnector();
        sqliteConnector.connect();

        sqliteConnector.insertCountries(table);

        List<Country> countries = sqliteConnector.getCountriesFromRegion("Europe");

        countries.forEach(country ->      logger.info(" | " + country.getName() + " | " +  country.getTotalCases() + " | " +
                country.getTotalTests() + " | " + country.getActiveCases()));



        sqliteConnector.closeConnection();
    }
}
