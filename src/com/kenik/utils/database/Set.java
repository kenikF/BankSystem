package com.kenik.utils.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Set {
    Mysql mysql = new Mysql();

    public void transferOp(int id, int sum, int ID) {
        Connection connection = mysql.setConnection();
        try (PreparedStatement ps = connection.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE userID = ?")) {
            ps.setInt(1, sum);
            ps.setInt(2, id);
            int rs = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (PreparedStatement ps = connection.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE userID = ?")) {
            ps.setInt(1, sum);
            ps.setInt(2, ID);
            int rs = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
