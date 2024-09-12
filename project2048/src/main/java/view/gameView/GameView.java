package view.gameView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class GameView extends BorderPane {

    private MenuItem miExit;
    private MenuItem miRestart;
    private MenuItem miLeaderboard;
    private MenuItem miAbout;
    private MenuItem miRules;
    private Label currentScoreLabel;
    private Label highScoreLabel;
    private final int boardHeight;
    private final int boardWidth;
    private Label[][] cells;
    private static final int tileSize = 100;


    public GameView(int boardHeight, int boardWidth) {
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        //Create menuItems:
        this.miExit = new MenuItem("Exit");
        this.miAbout = new MenuItem("About");
        this.miRules = new MenuItem("Rules");
        this.miLeaderboard = new MenuItem("Leaderboard");
        this.miRestart = new MenuItem("Restart");

        //Create gridCells
        this.cells = new Label[boardHeight][boardWidth];

        //Score labels
        this.currentScoreLabel = new Label();
        this.highScoreLabel = new Label();
    }

    private void layoutNodes() {
        addMenu();
        //Score
        HBox scoreHBox = createScoreBox();
        scoreHBox.setAlignment(Pos.TOP_CENTER);
        //Board
        GridPane boardGrid = createBoardGrid();
        boardGrid.setAlignment(Pos.CENTER);

        addScoreAndBoardVBox(scoreHBox, boardGrid);
        this.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
    }

    Label[][] getCells() {
        return this.cells;
    }

    Label getCurrentScoreLabel() {
        return currentScoreLabel;
    }
    Label getHighScoreLabel() {
        return highScoreLabel;
    }

    MenuItem getMiExit() {
        return miExit;
    }

    MenuItem getMiRestart() {
        return miRestart;
    }

    MenuItem getMiLeaderboard() {
        return miLeaderboard;
    }

    MenuItem getMiAbout() {
        return miAbout;
    }

    MenuItem getMiRules() {
        return miRules;
    }



    private HBox createScoreBox() {
        designScoreLabel(currentScoreLabel);
        designScoreLabel(highScoreLabel);

        return new HBox(currentScoreLabel,highScoreLabel);
    }

    private GridPane createBoardGrid() {
        GridPane boardGrid = new GridPane();
        boardGrid.setBackground(new Background(new BackgroundFill(Color.BURLYWOOD, new CornerRadii(10), null)));
        boardGrid.setPrefSize(boardWidth*tileSize + 20, boardHeight*tileSize + 20);
        boardGrid.setMaxSize(boardWidth*tileSize + 20, boardHeight*tileSize + 20);
        for (int row = 0; row < this.boardHeight; row++) {
            for (int col = 0; col < this.boardWidth; col++) {
                cells[row][col] = new Label();
                cells[row][col].setPrefSize(tileSize, tileSize);
                boardGrid.add(cells[row][col], col, row);
            }
        }
        return boardGrid;
    }

    private void designScoreLabel(Label label) {
        label.setPrefSize(boardWidth*tileSize/2.0, tileSize);
        label.setStyle("-fx-font-size: 20; -fx-font-weight: 600");
        label.setBackground(new Background(new BackgroundFill(Color.BURLYWOOD, new CornerRadii(7), new Insets(10))));
        label.setAlignment(Pos.CENTER);
    }

    private void addMenu() {
        Menu gameMenu = new Menu("Options", null, miRestart, miLeaderboard, new SeparatorMenuItem(), miExit);
        Menu helpMenu = new Menu("Help", null, miRules, new SeparatorMenuItem(), miAbout);
        MenuBar menuBar = new MenuBar(gameMenu, helpMenu);
        this.setTop(menuBar);
    }

    private void addScoreAndBoardVBox(HBox scoreHBox, GridPane boardGrid) {
        VBox scoreAndBoardVBox = new VBox(10);
        scoreAndBoardVBox.setAlignment(Pos.CENTER);
        scoreAndBoardVBox.getChildren().addAll(scoreHBox, boardGrid);
        this.setCenter(scoreAndBoardVBox);
    }

}
