package com.corona.exam.scraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Scraper {

    public static final String HTTPS_WWW_WORLDOMETERS_INFO_CORONAVIRUS = "https://www.worldometers.info/coronavirus/";
    public static final String TABLE_MAIN_TABLE_COUNTRIES_TODAY = "table#main_table_countries_today";
    Logger logger = LoggerFactory.getLogger(Scraper.class);

    public StringBuffer getDataFromUrl() throws IOException {
        String url = HTTPS_WWW_WORLDOMETERS_INFO_CORONAVIRUS;
        URL obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");


        int responseCode = con.getResponseCode();
        logger.info("\nSending 'GET' request to URL : " + url);
        logger.info("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response;
    }

    public  String[][] parseTable(StringBuffer response) {
        Document doc = Jsoup.parse(response.toString());
        Elements tables = doc.select(TABLE_MAIN_TABLE_COUNTRIES_TODAY);
        String[][] trtd = new String[0][];
        for (Element table : tables) {
            Elements trs = table.select("tr");
            trtd = new String[trs.size()][];
            for (int i = 0; i < trs.size(); i++) {
                Elements tds = trs.get(i).select("td");
                trtd[i] = new String[tds.size()];
                for (int j = 0; j < tds.size(); j++) {
                    trtd[i][j] = tds.get(j).text();
                }
            }
        }
        return trtd;
    }


}
