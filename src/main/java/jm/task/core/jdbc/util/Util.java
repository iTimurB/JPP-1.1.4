package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Property;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/testt?useSSL=false&serverTimezone=UTC";
    private static final String NAME = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public Util() {
    }

    Connection connection;

    public Connection getConnection() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            return connection;
        } catch (ClassNotFoundException| SQLException e) {
            System.out.println("DB connection error");
            e.printStackTrace();
        }
        return null;
    }

    public void getClose() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
