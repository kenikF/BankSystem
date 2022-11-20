package com.kenik.utils.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class If {
    public int isIdentifier(int eIdentifier) throws SQLException {
        Mysql mysql = new Mysql();
        Connection connection = mysql.setConnection();
        boolean is = false;
        try (PreparedStatement ps = connection.prepareStatement("SELECT userID FROM `accounts` WHERE `userID` = ?")) {
            ps.setInt(1, eIdentifier);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    is = true;
                }
            }
        }
        if(is) {
            connection.close();
            return 1;
        }
        return 0;
    }

    public int isPIN(int ePIN) throws SQLException {
        Mysql mysql = new Mysql();
        Connection connection = mysql.setConnection();
        boolean is = false;
        try (PreparedStatement ps = connection.prepareStatement("SELECT userID FROM `accounts` WHERE `pinCode` = ?")) {
            ps.setInt(1, ePIN);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    is = true;
                }
            }
        }
        if(is) {
            connection.close();
            return 1;
        }
        return 0;
    }

    public int isAdmin(int eIdentifier) throws SQLException {
        Mysql mysql = new Mysql();
        Connection connection = mysql.setConnection();
        boolean is = false;
        try (PreparedStatement ps = connection.prepareStatement("SELECT isAdmin FROM `accounts` WHERE `userID` = ?")) {
            ps.setInt(1, eIdentifier);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    if(rs.getInt(1) == 1) {
                        is = true;
                    }
                }
            }
        }
        if(is) {
            connection.close();
            return 1;
        } else {
            return 0;
        }
    }
}
