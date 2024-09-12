package view.leaderboardView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import model.GameModel;
import view.gameView.GamePresenter;

import java.util.ArrayList;

public class LeaderboardPresenter {
    private final GameModel model;
    private final LeaderboardView view;
    private final Stage currentStage;
    private Stage parentStage;

    public LeaderboardPresenter(GameModel model, LeaderboardView view, Stage currentStage) {
        this.model = model;
        this.view = view;
        this.currentStage = currentStage;
        addEvenHandlers();
        updateView();
    }

    public LeaderboardPresenter(GameModel model, LeaderboardView view, Stage currentStage, Stage parentStage) {
        this(model, view, currentStage);
        this.parentStage = parentStage;
    }

    private void addEvenHandlers() {
        addMiGoBackHandler();
        addMiExitGameHandler();
        addMiRulesHandler();
        addMiAboutHandler();
        addGoBackLabelHandler();
    }

    private void updateView() {
        ArrayList<String> leaderboardEntries = model.getLeaderboardData();  // Get data from GaeModel
        view.getLeaderboardListView().getItems().setAll(leaderboardEntries);  // Update ListView items
    }

    private void addMiGoBackHandler() {
        view.getMiGoBack().setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                currentStage.close();
            }
        });
    }

    private void addMiExitGameHandler() {
        if (view.getMiExitGame() != null) {
            view.getMiExitGame().setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    model.updatePlayerList();
                    model.updateLeaderboard();

                    Stage nameStage = new Stage();
                    GamePresenter.showStartScreen(nameStage);

                    currentStage.close();
                    parentStage.close();
                }
            });
        }
    }
    
    private void addMiRulesHandler() {
        view.getMiRules().setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GamePresenter.showRules();
            }
        });
    }

    private void addMiAboutHandler() {
        view.getMiAbout().setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GamePresenter.showAbout();
            }
        });
    }

    private void addGoBackLabelHandler() {
        view.getGoBackLabel().setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                currentStage.close();
            }
        });
    }

}
