package be.kdg.integration1.team22.yahtzee.model;

/*
    • Score.java: class that represents a score
        • It should contain a player ID.
        • It should contain an array of ScorePerCategory.
        • It should have a toString method that returns the scores per category of the player.
        • It should have a method that saves the score of a player in a database.
        • It should have a method that looks up the score of a player in a database.

    • ScorePerCategory.java: class that represents a score per category
        • It should contain a category.
        • It should contain a score.
        • It should have a method that returns whether the score is set or not.
 */

import java.sql.*;

public class Score {
    private final int playerId;
    private final String playerName;
    private final ScorePerCategory[] scores = new ScorePerCategory[Category.values().length];

    private final Database db = new Database();

    public Score(int playerId, String playerName) throws SQLException {
        this.playerId = playerId;
        this.playerName = playerName;
        for (int i = 0; i < Category.values().length; i++) {
            this.scores[i] = new ScorePerCategory(Category.values()[i]);
        }
    }

    public void saveScore() {
        db.saveScore();
    }

    public void lookUpScore(String input) {
        db.lookUpPlayer(input);
    }

    public void getLeaderboard() {
        db.getLeaderboard();
    }

    public void stop() throws SQLException {
        db.close();
    }

    public int getPlayerId() {
        return playerId;
    }

    public ScorePerCategory[] getScores() {
        return scores;
    }

    public boolean isScoreSet(int category) {
        return scores[category].isSet();
    }

    public int getScore(int category) {
        return scores[category].getScore();
    }

    public void setScore(int category, int score) {
        scores[category].setScore(score);
    }

    public void resetScore(int category) {
        scores[category].resetScore();
    }

    public int getTotalScore() {
        int totalScore = 0;
        for (ScorePerCategory score : scores) {
            if (score.getScore() != -1) {
                totalScore += score.getScore();
            }
        }
        return totalScore;
    }

    public int getBonus() {
        int bonus = 0;

        if (getTotalScore() >= 63) {
            bonus = 35;
        }

        return bonus;
    }

    public int getGrandTotal() {
        return getTotalScore() + getBonus();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (ScorePerCategory score : scores) {
            sb.append(score).append("\n");
        }

        sb.append("Total score: ").append(getTotalScore()).append("\n");
        sb.append("Bonus: ").append(getBonus()).append("\n");
        sb.append("Grand total: ").append(getGrandTotal());

        return sb.toString();
    }

    public int getCategoryScore(Category value) {
        // get the score for the given category
        for (ScorePerCategory score : scores) {
            if (score.getCategory() == value) {
                return score.getScore();
            }
        }
        return 0;
    }

    public void setScorePerCategory(Category value, int score) {
        // set the score for the given category
        for (ScorePerCategory scorePerCategory : scores) {
            if (scorePerCategory.getCategory() == value) {
                scorePerCategory.setScore(score);
            }
        }
    }

    private static class ScorePerCategory {
        private final Category category;
        private int score;

        public ScorePerCategory(Category category) {
            this.category = category;
            this.score = -1;
        }

        public Category getCategory() {
            return category;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public boolean isSet() {
            return score != -1;
        }

        public void resetScore() {
            score = 0;
        }

        @Override
        public String toString() {
            if (score == -1) {
                return category + ": --";
            } else {
                return category + ": " + score;
            }
        }
    }

    private class Database {
        private final String url = "jdbc:postgresql://localhost:5432/ascii22";

        private final String user = "postgres";

        private final Connection db;

        private Database() throws SQLException {
            db = DriverManager.getConnection(url, user, System.getenv("DB_PASSWORD"));
        }

        private void createTable() throws SQLException {
            // Create database table with player name, grand total, date and time
            String sql = "CREATE TABLE IF NOT EXISTS scores ("
                    + "id SERIAL PRIMARY KEY,"
                    + "player_name VARCHAR(255) NOT NULL,"
                    + "grand_total INTEGER NOT NULL,"
                    + "date DATE NOT NULL,"
                    + "time TIME NOT NULL"
                    + ");";
            Statement stmt = db.createStatement();
            stmt.execute(sql);
        }

        private void saveScore() {
            // Save score to database
            try {
                createTable();
                String sql = "INSERT INTO scores (player_name, grand_total, date, time) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt = db.prepareStatement(sql);
                pstmt.setString(1, playerName);
                pstmt.setInt(2, getGrandTotal());
                pstmt.setDate(3, new Date(System.currentTimeMillis()));
                pstmt.setTime(4, new Time(System.currentTimeMillis()));
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        private void lookUpPlayer(String input) {
            // Look up player in database
            try {
                createTable();
                String sql = "SELECT * FROM scores WHERE player_name = ?";
                PreparedStatement pstmt = db.prepareStatement(sql);
                pstmt.setString(1, input);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    System.out.println(rs.getString("player_name") + "\t" +
                            rs.getInt("grand_total") + "\t" +
                            rs.getDate("date") + "\t" +
                            rs.getTime("time"));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        private void getLeaderboard() {
            // Get the top 10 players from the database
            try {
                createTable();
                String sql = "SELECT * FROM scores ORDER BY grand_total DESC LIMIT 10";
                Statement stmt = db.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.println(rs.getString("player_name") + "\t" +
                            rs.getInt("grand_total") + "\t" +
                            rs.getDate("date") + "\t" +
                            rs.getTime("time"));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        private void close() throws SQLException {
            db.close();
        }
    }
}

