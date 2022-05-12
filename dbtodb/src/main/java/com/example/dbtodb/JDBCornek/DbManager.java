package com.example.dbtodb.JDBCornek;

import java.sql.*;

public class DbManager {

    public ResultSet get(Connection connection) throws SQLException{
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("select * from users");
        return set;
    }

    public void add(Connection connection,int id,String name,String email){
        try {
            PreparedStatement statement = connection.prepareStatement("insert into users values(?,?,?)");
            statement.setString(1, String.valueOf(id));
            statement.setString(2,name);
            statement.setString(3,email);
            statement.execute();
            System.out.println("Kayıt Eklendi.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Error Code: " + e.getErrorCode());
        }

    }

    public void update(Connection connection,int id,String userName){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "update user set userName = ? where userId = ?");
            statement.setString(1,userName);
            statement.setString(2, String.valueOf(id));
            statement.execute();
            System.out.println("Kayıt güncellendi.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Error Code: " + e.getErrorCode());
        }
    }

    public void delete(Connection connection,int id){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "delete from user where userId = ?");
            statement.setString(1, String.valueOf(id));
            statement.execute();
            System.out.println("Kayıt silindi.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Error Code: " + e.getErrorCode());
        }
    }
}
