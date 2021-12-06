package com.corona.exam;
import java.sql.*;

public class SqliteConnector {

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

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
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
                System.out.println(e.getMessage());
            }
        }

    public void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printRegion(String region){
        String sql = "SELECT * FROM COUNTRIES WHERE REGION = ?";
        try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, region);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                System.out.println(resultSet.getString("COUNTRY") +  resultSet.getInt("TOTAL_CASES") +
                        resultSet.getInt("TOTAL_TESTS") + resultSet.getInt("ACTIVE_CASES"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
