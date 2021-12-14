package com.corona.exam.csv;

import com.corona.exam.scraping.Scraper;
import com.corona.exam.model.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {

    Logger logger = LoggerFactory.getLogger(Scraper.class);

    private static String fileName  = "export_%s_%s";

    public void createCsv(String region, String date, List<Country> countries){
        logger.info("creating csv file");


        try(FileWriter fw = new FileWriter(String.format(fileName, region, date) + ".csv")){
            fw.append("COUNTRY, TOTAL_CASES, TOTAL_TESTS, ACTIVE_CASES \n");
            for(Country country : countries){
                fw.append(country.getRegion())
                        .append(" , ").append(country.getName())
                        .append(" , ").append(String.valueOf(country.getTotalCases()))
                        .append(" , ").append(String.valueOf(country.getTotalTests()))
                        .append(" , ").append(String.valueOf(country.getActiveCases()))
                        .append("\n");
            }

            logger.info("CSV File was created successfully.");

        } catch (IOException e) {
            logger.error(String.valueOf(e));
        }
    }
}
