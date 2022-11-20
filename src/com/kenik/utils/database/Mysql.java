package com.kenik.utils.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Mysql {
    private final String database = "bank";
    private final String username = "root";
    private final String password = "";
    private final String url = "jdbc:mysql://localhost:3306/"+database;

    public Connection setConnection() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUserName(int ID) {
        Connection connection = setConnection();
        try (PreparedStatement ps = connection.prepareStatement("SELECT `name` FROM `accounts` WHERE `userID` = ?")) {
            ps.setInt(1, ID);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return rs.getString(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public int getBalance(int ID) {
        Connection connection = setConnection();
        try(PreparedStatement ps = connection.prepareStatement("SELECT `balance` FROM `accounts` WHERE `userID` = ?")) {
            ps.setInt(1, ID);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public List getAllId() {
        Connection connection = setConnection();
        try(PreparedStatement ps = connection.prepareStatement("SELECT `userID` FROM `accounts`")) {
            try (ResultSet rs = ps.executeQuery()) {
                List<Integer> allId = new ArrayList<>();
                while(rs.next()) {
                    allId.add(rs.getInt(1));
                }
                return allId;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAccount(int id) {
        Connection connection = setConnection();
        try(PreparedStatement ps = connection.prepareStatement("DELETE FROM `accounts` WHERE `userID` = ?")) {
            ps.setInt(1, id);
            int rs = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addBalance(int id, int sum) {
        Connection connection = setConnection();
        try(PreparedStatement ps = connection.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE userID = ?")) {
            ps.setInt(1, sum);
            ps.setInt(2, id);
            int rs = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
