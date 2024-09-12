
package model;

import java.io.*;

public class Score {
    private int highScore;
    private int score;
    private final File scoreFile = new File("src/main/resources/leaderboard.txt");

    public Score() {
        this.score = 0;
        this.highScore = Integer.parseInt(this.getHighScore());
    }

    public String getHighScore() {
        String highScore = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(this.scoreFile))) {
            highScore = reader.readLine().split(";")[1];
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return highScore == null ? "0" : highScore;
    }

    public void addToScore(int score) {
        this.score += score;
        if (this.score > this.highScore) {
            this.highScore = this.score;
        }
    }

    public int getScore() {
        return this.score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return String.format("\tScore: %-4d \t\t\t\t High Score: %-4s", this.getScore(), this.getHighScore());
    }
}
