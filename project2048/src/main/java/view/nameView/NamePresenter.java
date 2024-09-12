package view.nameView;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.GameModel;
import view.gameView.GamePresenter;
import view.gameView.GameView;
import view.leaderboardView.LeaderboardPresenter;
import view.leaderboardView.LeaderboardView;

public class NamePresenter {
    private final GameModel model;
    private final NameView view;
    private final Stage currentStage;
    private final Scene currentScene;
    private boolean isResume;

    public NamePresenter(GameModel model, NameView view, Scene currentScene, Stage currentStage) {
        this.model = model;
        this.view = view;
        this.currentScene = currentScene;
        this.currentStage = currentStage;
        this.isResume = false;

        addEventHandlers();
    }

    private void addEventHandlers() {
        addResumeGameButtonHandler();
        addNewGameButtonHandler();
        addLeaderboardButtonHandler();
        addExitButtonHandler();
        currentScene.setOnKeyPressed(this::handleKeyEvent);
    }

    private void startGame() {
        String playerName = view.getNameField().getText();
        if (playerName.isEmpty()) {
            showEnterNameAlert();
        } else {
            model.playGame(playerName);
            if (this.isResume) model.loadGameState();
            showGameStage();
            // Close the current stage after showing the game stage
            currentStage.close();
        }
    }

    private void handleKeyEvent(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            startGame();
        }
    }

    private void addResumeGameButtonHandler() {
        view.getResumeGameButton().setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                isResume = true;
                view.getNameField().setText(model.loadPlayerName());
                startGame();
            }
        });
    }

    private void addNewGameButtonHandler() {
        view.getNewGameButton().setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                startGame();
            }
        });
    }

    private void addLeaderboardButtonHandler() {
        view.getLeaderboardButton().setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showLeaderboardStage();
            }
        });
    }

    private void addExitButtonHandler() {
        view.getExitButton().setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                currentStage.close();
                Platform.exit();
            }
        });
    }

    private void showEnterNameAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Easy there!");
        alert.setContentText("Enter your name to play!");
        alert.showAndWait();
    }

    private void showGameStage() {
        Stage gameStage = new Stage();
        GameView view = new GameView(model.getBoardHeight(), model.getBoardWidth());
        Scene gameScene = new Scene(view);
        new GamePresenter(model, view, gameScene, gameStage);

        gameStage.setScene(gameScene);
        gameStage.getIcons().add(new Image("/images/icon.png"));
        gameStage.setTitle("2048");
        gameStage.setWidth(450);
        gameStage.setHeight(650);
        gameStage.show();
    }

    private void showLeaderboardStage() {
        Stage leaderboardStage = new Stage();
        LeaderboardView leaderboardView = new LeaderboardView();
        Scene scene = new Scene(leaderboardView);
        scene.getStylesheets().add("/stylesheets/leaderboard.css");
        new LeaderboardPresenter(model, leaderboardView, leaderboardStage);

        GamePresenter.showLeaderboardStage(leaderboardStage, scene);
    }

}
