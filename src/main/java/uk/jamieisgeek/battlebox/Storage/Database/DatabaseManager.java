package uk.jamieisgeek.battlebox.Storage.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
    private final Database database;
    public DatabaseManager(Database database) {
        this.database = database;

        this.migrate();
    }

    public void migrate() {
        try (Connection connection = database.getConnection()) {
            String scoreTable = "CREATE TABLE IF NOT EXISTS " + database.getTablePrefix() + "scores"
                    + "(id INT AUTO_INCREMENT,"
                    + "player TEXT,"
                    + "score INT,"
                    + "PRIMARY KEY (id))";
            connection.prepareStatement(scoreTable).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePlayerScore(String playerName, int score) {
        try (Connection connection = database.getConnection()) {
            String stmt = "INSERT INTO " + database.getTablePrefix() + "scores (player, score) VALUES (?, ?) ON DUPLICATE KEY UPDATE score = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(stmt);
            preparedStatement.setString(1, playerName);
            preparedStatement.setInt(2, score);
            preparedStatement.setInt(3, score);

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getPlayerScore(String playerName) {
        try (Connection connection = database.getConnection()) {
            String stmt = "SELECT score FROM " + database.getTablePrefix() + "scores WHERE player = ?";
            PreparedStatement prep = connection.prepareStatement(stmt);
            prep.setString(1, playerName);
            prep.execute();

            ResultSet rs = prep.getResultSet();
            if(rs.next()) {
                return rs.getInt("score");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
