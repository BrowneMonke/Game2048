package model;

import java.io.*;
import java.util.*;

public class GameModel {
    private final File leaderboardFile = new File("src/main/resources/leaderboard.txt");
    private final File gameStateFile = new File("src/main/resources/gameState.txt");
    private final File gameScoreFile = new File("src/main/resources/gameScore.txt");
    private Board board;
    private final ArrayList<Player> players;
    //private char userInput;
    private final Player player;
    private boolean isGameWon;


    public GameModel() {
        this.isGameWon = false;
        this.board = new Board();
        this.players = new ArrayList<>();
        this.player = new Player();
        this.loadPlayerList();
    }


    public void playGame(String playerName) {
        this.player.setName(playerName);
        addPlayer(player);
        this.updateLeaderboard();
    }

    public boolean isGameWon() {
        return isGameWon;
    }

    public int getBoardHeight() {
        return Board.HEIGHT;
    }

    public int getBoardWidth() {
        return Board.WIDTH;
    }

    public String getHighScore() {
        return this.board.getHighScore();
    }


    public int getScore() {
        return this.board.getScore();
    }

    public void setScore(int score) {
        this.board.setScore(score);
    }

    public Tile[][] getTiles() {
        return this.board.getTiles();
    }


    public void loadPlayerList() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.leaderboardFile));
            String line;
            while ((line = reader.readLine()) != null) {
                Player lbPlayer = new Player(line.split(";")[0]);
                lbPlayer.setScore(Integer.parseInt(line.split(";")[1]));
                this.players.add(lbPlayer);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void updatePlayerList() {
        Collections.sort(this.players);
        while (this.players.size() > 18) {
            this.players.remove(18);
        }
    }

    public void updateLeaderboard() {
        try (FileWriter writer = new FileWriter(this.leaderboardFile)) {
            Collections.sort(this.players);
            for (Player player : this.players) {
                writer.write(player.getName() + ";" + player.getScore() + System.lineSeparator());
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public boolean isGameOver() {
        for (int row = 0; row < Board.HEIGHT; row++) {
            for (int col = 0; col < Board.WIDTH; col++) {
                Tile currentTile = this.board.getTiles()[row][col];

                if (currentTile.getValue() == 0 || this.board.adjacentTiles(currentTile).contains(currentTile)) {
                    return false;
                }
                if (currentTile.getValue() == 2048) {
                    this.isGameWon = true;
                    return true;
                }
            }
        }
        return true;
    }

    public void resetBoard() {
        this.board = new Board();
    }

    public void slideUp() {
        this.board.slideUp();
        addTile();
    }
    public void slideDown() {
        this.board.slideDown();
        addTile();
    }
    public void slideLeft() {
        this.board.slideLeft();
        addTile();
    }
    public void slideRight() {
        this.board.slideRight();
        addTile();
    }

    public void addTile() {
        if (this.board.isMoved()) {
            this.board.addTile();
            player.setScore(this.getScore());
            updateLeaderboard();
            saveGameState();
        }
        this.board.setMoved(false);
        this.board.resetMerged();
    }

    public ArrayList<String> getLeaderboardData() {
        int lines = fileLineCounter(leaderboardFile);
        ArrayList<String> leaderboardData = new ArrayList<>();
        for (int pos = 0; pos < lines; pos++) {
            Player currentPlayer = this.players.get(pos);
            String leaderboardLine = String.format("  %-15d%-30s%10d", pos + 1, currentPlayer.getName(), currentPlayer.getScore());
            leaderboardData.add(leaderboardLine);
        }
        return leaderboardData;
    }

    private int fileLineCounter(File file) {
        int lines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.readLine() != null) {
                lines++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public void saveGameState() {
        try (FileWriter writer = new FileWriter(this.gameStateFile)) {
            for (int row = 0; row < getBoardHeight(); row++) {
                for (int col = 0; col < getBoardWidth(); col++) {
                    writer.write(Integer.toString(this.getTiles()[row][col].getValue()));
                    if (col != getBoardWidth() - 1) {
                        writer.write(";");
                    }
                }
                writer.write(System.lineSeparator());
            }
            saveGameScore();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void loadGameState() {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(this.gameStateFile));
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                String[] tileRow = line.split(";");

                for (int col = 0; col < tileRow.length; col++) {
                    this.getTiles()[row][col].setValue(Integer.parseInt(tileRow[col]));
                }
                row++;
            }
            this.setScore(loadGameScore());
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveGameScore() {
        try (FileWriter writer = new FileWriter(this.gameScoreFile)) {
                writer.write(this.player.getName() + ";" + this.getScore());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public int loadGameScore() {
        int score=-1;
        try (BufferedReader reader = new BufferedReader(new FileReader(this.gameScoreFile))){
            score = Integer.parseInt(reader.readLine().split(";")[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return score;
    }

    public String loadPlayerName() {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.gameScoreFile))){
            this.player.setName(reader.readLine().split(";")[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.player.getName();
    }

}

