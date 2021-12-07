package com.corona.exam.config;

import com.corona.exam.Processor;
import com.corona.exam.csv.CsvWriter;
import com.corona.exam.db.SqliteConnector;
import com.corona.exam.scrapping.Scraper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {


    @Bean
    public Processor processor(SqliteConnector sqliteConnector, Scraper scraper, CsvWriter csvWriter){
        return new Processor(sqliteConnector, scraper, csvWriter);
    }

    @Bean
    public CsvWriter csvWriter(){
        return new CsvWriter();
    }

    @Bean
    public Scraper scraper(){
        return new Scraper();
    }

    @Bean
    public SqliteConnector sqliteConnector(){
        return new SqliteConnector();
    }

}
