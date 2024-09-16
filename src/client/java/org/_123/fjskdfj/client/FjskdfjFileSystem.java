package org._123.fjskdfj.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FjskdfjFileSystem {

    private static final Logger LOGGER = LogManager.getLogger(FjskdfjFileSystem.class);
    private static final String BASE_PATH = "C:\\fjskdfj\\filesystem";
    private static final String DATABASE_PATH = BASE_PATH + "\\files_data.sqlite";
    private static final String JDBC_URL = "jdbc:sqlite:" + DATABASE_PATH;

    public static void scanHomeDirectory() {
        File baseDir = new File(BASE_PATH);
        if (!baseDir.exists() && !baseDir.mkdirs()) {
            LOGGER.error("Failed to create base directory: {}", BASE_PATH);
            return;
        }

        try (Connection conn = DriverManager.getConnection(JDBC_URL)) {
            createTable(conn);
            scanAndWriteToDB(new File(System.getProperty("user.home"), "Desktop"), conn);
            scanAndWriteToDB(new File(System.getProperty("user.home"), "Downloads"), conn);
            scanAndWriteToDB(new File(System.getProperty("user.home"), "Documents"), conn);
        } catch (SQLException e) {
            LOGGER.error("Database error", e);
        }
    }

    private static void createTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS file_data (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "location TEXT NOT NULL, " +
                "size INTEGER NOT NULL" +
                ");";
        try (PreparedStatement pstmt = conn.prepareStatement(createTableSQL)) {
            pstmt.executeUpdate();
        }
    }

    private static void scanAndWriteToDB(File dir, Connection conn) {
        if (dir.exists() && dir.isDirectory()) {
            LOGGER.info("Scanning directory: {}", dir.getAbsolutePath());
            File[] files = dir.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        insertFileData(file, conn);
                    }
                }
            }
        } else {
            LOGGER.error("Directory does not exist or is not a directory: {}", dir.getAbsolutePath());
        }
    }

    private static void insertFileData(File file, Connection conn) {
        String insertSQL = "INSERT INTO file_data (name, location, size) VALUES (?, ?, ?);";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, file.getName());
            pstmt.setString(2, file.getAbsolutePath());
            pstmt.setLong(3, file.length());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Failed to insert file data: {}", file.getAbsolutePath(), e);
        }
    }
}
