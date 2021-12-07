package com.corona.exam.csv;

import com.corona.exam.scrapping.Scraper;
import com.corona.exam.model.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {

    Logger logger = LoggerFactory.getLogger(Scraper.class);

    public void createCsv(String filename, List<Country> countries){
        logger.info("creating csv file: " + filename);

        try(FileWriter fw = new FileWriter(filename + ".csv")){

            for(Country country : countries){
                fw.append(country.getRegion() + " , " + country.getName()
                        + " , " + country.getTotalCases() + " , " + country.getTotalTests()
                        + " , " + country.getActiveCases());
            }

            logger.info("CSV File is created successfully.");

        } catch (IOException e) {
            logger.error(String.valueOf(e));
        }
    }
}
