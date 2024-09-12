package view.gameView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.GameModel;
import view.leaderboardView.LeaderboardPresenter;
import view.leaderboardView.LeaderboardView;
import view.nameView.NamePresenter;
import view.nameView.NameView;


public class GamePresenter {

    private final GameModel model;
    private final GameView view;
    private final Scene currentScene;
    private final Stage currentStage;


    public GamePresenter(GameModel model, GameView view, Scene currentScene, Stage currentStage) {
        this.model = model;
        this.view = view;
        this.currentScene = currentScene;
        this.currentStage = currentStage;

        addEventHandlers();
        updateView();
    }

    private void addEventHandlers() {
        addKeyEventHandler();
        addHighScoreLabelHandler();
        addMiRestartHandler();
        addMiLeaderboardHandler();
        addMiExitHandler();
        addMiRulesHandler();
        addMiAboutHandler();
    }

    private void updateView() {
        showCells();
        updateScoreboard();
        if (isGameOver()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            designGameOverAlert(alert);
            alert.showAndWait();
            endSession();
        }
    }

    public static void showLeaderboardStage(Stage leaderboardStage, Scene scene) {
        leaderboardStage.setScene(scene);
        leaderboardStage.getIcons().add(new Image("/images/icon.png"));
        leaderboardStage.setTitle("2048 Leaderboard");
        leaderboardStage.setWidth(450);
        leaderboardStage.setHeight(650);
        leaderboardStage.showAndWait();
    }

    public static void showStartScreen(Stage nameStage) {
        GameModel gameModel = new GameModel();
        NameView nameView = new NameView();
        Scene nameScene = new Scene(nameView);
        new NamePresenter(gameModel, nameView, nameScene, nameStage);

        nameStage.setScene(nameScene);
        nameStage.getIcons().add(new Image("/images/icon.png"));
        nameStage.setTitle("2048");
        nameStage.setWidth(300);
        nameStage.setHeight(250);
        nameStage.show();
    }

    public static void showRules() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Rules");
        alert.setHeaderText("How to play");
        alert.setContentText("""
                        Use your arrow keys (or WASD/ZQSD) to move the tiles.
                        Tiles with the same number merge into one when they touch.
                        Add them up to reach 2048!""");
        alert.showAndWait();
    }

    public static void showAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("POTATOES!");
        alert.setContentText("""
                        This game was created as a school project.
                        I am truly grateful to my teacher for working hard so her students could have a better understanding of the material.
                                            
                        Based on 2048 by Gabriele Cirulli.
                                           
                        © 2024 Akshat Verma
                        Karel de Grote Hogeschool, Antwerp
                        """);
        alert.showAndWait();
    }

    private void addKeyEventHandler() {
        currentScene.setOnKeyPressed(new EventHandler<>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                handleKeyEvent(keyEvent);
            }
        });
    }
    private void handleKeyEvent(KeyEvent event) {
        switch (event.getCode()) {
            case UP, Z, W:
                model.slideUp();
                break;
            case LEFT, Q, A:
                model.slideLeft();
                break;
            case DOWN, S:
                model.slideDown();
                break;
            case RIGHT, D:
                model.slideRight();
                break;
            case R:
                restartGame();
                break;
            case P:
                endSession();
                break;
        }
        updateView();
    }

    private void addHighScoreLabelHandler() {
        view.getHighScoreLabel().setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                openLeaderboardWindow();
            }
        });
    }

    private void addMiRestartHandler() {
        view.getMiRestart().setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                restartGame();
            }
        });
    }

    private void addMiLeaderboardHandler() {
        view.getMiLeaderboard().setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                openLeaderboardWindow();
            }
        });
    }

    private void addMiExitHandler() {
        view.getMiExit().setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                endSession();
            }
        });
    }

    private void addMiRulesHandler() {
        view.getMiRules().setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showRules();
            }
        });
    }

    private void addMiAboutHandler() {
        view.getMiAbout().setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showAbout();
            }
        });
    }

    private void openLeaderboardWindow() {
        Stage leaderboardStage = new Stage();
        LeaderboardView leaderboardView = new LeaderboardView(true);
        Scene scene = new Scene(leaderboardView);
        scene.getStylesheets().add("/stylesheets/leaderboard.css");
        new LeaderboardPresenter(model, leaderboardView, leaderboardStage, currentStage);

        showLeaderboardStage(leaderboardStage, scene);
    }

    private void designGameOverAlert(Alert alert) {
        ImageView imageView = new ImageView(new Image("/images/icon.png"));
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        alert.setGraphic(imageView);
        if (model.isGameWon()) {
            alert.setTitle("VICTORY!");
            alert.setHeaderText("Winner Winner Chicken Dinner!!");
        } else {
            alert.setTitle("Game Over");
            alert.setHeaderText("Well Done. Better luck next time!");
        }
        alert.setContentText("Your Score:  " + model.getScore());
    }

    private void endSession() {
        model.updatePlayerList();
        model.updateLeaderboard();

        Stage nameStage = new Stage();
        showStartScreen(nameStage);

        currentStage.close();
    }

    private void restartGame() {
        model.updateLeaderboard();
        model.resetBoard();
        updateView();
    }

    private boolean isGameOver() {
        return model.isGameOver();
    }

    private int getTileValue(int row, int col) {
        return model.getTiles()[row][col].getValue();
    }

    private void showCells() {
        for (int row = 0; row < model.getBoardHeight(); row++) {
            for (int col = 0; col < model.getBoardWidth(); col++) {
                setCellView(view.getCells()[row][col], getTileValue(row, col));
            }
        }
    }

    private void setCellView(Label cell, int value) {
        cell.setText(value==0? "" : String.valueOf(value));
        cell.setAlignment(Pos.CENTER);
        cell.setBackground(new Background(new BackgroundFill(getCellColor(value), new CornerRadii(10), new Insets(7.5))));

    }

    private void updateScoreboard() {
        view.getCurrentScoreLabel().setText(String.format("%-8s\t %6d","Score:", model.getScore()));
        view.getHighScoreLabel().setText(String.format("%-8s\t %6s", "Best:", model.getHighScore()));
    }

    private Color getCellColor(int value) {
        // Return the appropriate color for the given cell value
        switch (value){
            case 2:
                return Color.rgb(238,228,218);
            case 4:
                return Color.rgb(237,224,200);
            case 8:
                return Color.rgb(242,177,121);
            case 16:
                return Color.rgb(245,249,99);
            case 32:
                return Color.rgb(246,124,95);
            case 64:
                return Color.rgb(246,94,59);
            case 128:
                return Color.rgb(237,207,114);
            case 256:
                return Color.rgb(237,204,97);
            case 512:
                return Color.rgb(237,200,80);
            case 1024:
                return Color.rgb(237,197,63);
            case 2048:
                return Color.rgb(237,194,46);
            default:
                return Color.CORNSILK;
        }
    }

}
