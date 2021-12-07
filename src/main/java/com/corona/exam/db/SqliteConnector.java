package com.corona.exam.db;
import com.corona.exam.model.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteConnector {

    Logger logger = LoggerFactory.getLogger(SqliteConnector.class);

    Connection conn = null;

    /**
     * Connect to a sample database
     */
    public  Connection connect() {
        try {
            // db parameters
            String dir = System.getProperty("user.dir");

            String url = "jdbc:sqlite:"+ dir+"/EXAM.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            logger.info("Connection to SQLite has been established.");

        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return conn;
    }

    public void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Country> getCountriesFromRegion(String region){
        List<Country> countries = new ArrayList<>();
        String sql = "SELECT * FROM COUNTRIES WHERE REGION = ?";
        try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, region);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                countries.add(Country.CountryBuilder.aCountry()
                        .withRegion(resultSet.getString("REGION"))
                        .withName(resultSet.getString("COUNTRY"))
                        .withTotalCases(resultSet.getInt("TOTAL_CASES"))
                        .withTotalTests(resultSet.getInt("TOTAL_TESTS") )
                        .withActiveCases(resultSet.getInt("ACTIVE_CASES"))
                        .build());
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return countries;
    }

    public void insertCountry(String region, String country, Integer totalCases, Integer totalTests, Integer activeCases) {
            String sql = "INSERT INTO COUNTRIES(REGION, COUNTRY, TOTAL_CASES, TOTAL_TESTS, ACTIVE_CASES) VALUES(?,?,?,?,?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, region);
                pstmt.setString(2, country);
                pstmt.setInt(3, totalCases);
                pstmt.setInt(4, totalTests);
                pstmt.setInt(5, activeCases);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                logger.info(e.getMessage());
            }
        }


    public void insertCountries(String[][] table) {
        for(int i = 9; i< 233; i++){
            logger.info("inserting "+  table[i][1] + " at index " + table[i][0]);
            insertCountry(table[i][15], table[i][1],
                    parseNumberFromTable(table[i][2]),
                    parseNumberFromTable(table[i][12]),
                    parseNumberFromTable(table[i][8]));
        }
    }

    private Integer parseNumberFromTable(String numberFromTable){
        if(numberFromTable.equalsIgnoreCase("N/A") || numberFromTable.equalsIgnoreCase("")){
            return -1;
        } else {
            return Integer.valueOf(numberFromTable.replaceAll(",", ""));
        }
    }

}
