package be.kdg.integration1.team22.yahtzee.model;

/*
    • Score.java: class that represents a score
        • It should contain a player ID.
        • It should contain an array of ScorePerCategory.
        • It should have a toString method that returns the scores per category of the player.

    • ScorePerCategory.java: class that represents a score per category
        • It should contain a category.
        • It should contain a score.
        • It should have a method that returns whether the score is set or not.
 */

public class Score {
    private final int playerId;
    private final ScorePerCategory[] scores = new ScorePerCategory[Category.values().length];

    public Score(int playerId) {
        this.playerId = playerId;
        for (int i = 0; i < Category.values().length; i++) {
            this.scores[i] = new ScorePerCategory(Category.values()[i]);
        }
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

        sb.append("Player ID: ").append(playerId).append("\n");

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
            return score != 0;
        }

        public void resetScore() {
            score = 0;
        }

        @Override
        public String toString() {
            if (score == -1) {
                return category + ": not set";
            } else {
                return category + ": " + score;
            }
        }
    }
}

