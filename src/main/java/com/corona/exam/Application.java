package com.corona.exam;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class Application {




    public static void main(String[] args) throws IOException {


        StringBuffer response = getDataFromUrl();


        String[][] table = parseTable(response);

        SqliteConnector sqliteConnector = new SqliteConnector();
        sqliteConnector.connect();


        sqliteConnector.printRegion("Europe");

        for(int i = 9; i< 232; i++){
            System.out.println("inserting "+  table[i][1] + " at index " + table[i][0]);
            sqliteConnector.insertCountry(table[i][15], table[i][1],
                    parseNumberFromTable(table[i][2]),
                    parseNumberFromTable(table[i][12]),
                    parseNumberFromTable(table[i][8]));
        }

        System.out.println("");
    }

    private static StringBuffer getDataFromUrl() throws IOException {
        String url = "https://www.worldometers.info/coronavirus/";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");


        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print result
        System.out.println(response);
        return response;
    }

    private static String[][] parseTable(StringBuffer response) {
        Document doc = Jsoup.parse(response.toString());
        Elements tables = doc.select("table#main_table_countries_today");
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

    private static Integer parseNumberFromTable(String numberFromTable){
        if(numberFromTable.equalsIgnoreCase("N/A") || numberFromTable.equalsIgnoreCase("")){
            return -1;
        } else {
            return Integer.valueOf(numberFromTable.replaceAll(",", ""));
        }
    }
}
