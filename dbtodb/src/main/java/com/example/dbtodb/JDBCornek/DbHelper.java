package com.example.dbtodb.JDBCornek;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbHelper {
    private final String userName = "dbtodbtest2";
    private final String password = "dbtodbtest2";
    private final String dbUrl ="jdbc:mysql://localhost:3307/dbtodbtest2";

    private DbHelper(){}

    public static DbHelper getHelper(){
        return new DbHelper();
    }
    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(dbUrl,userName,password);
    }

    public void showErrorMessage(SQLException ex){
        System.out.println("Error: " + ex.getMessage());
        System.out.println("Error Code: " + ex.getErrorCode());
    }
}
