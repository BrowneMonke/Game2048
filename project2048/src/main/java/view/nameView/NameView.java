package view.nameView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NameView extends VBox {
    private TextField nameField;
    private Button resumeGameButton;
    private Button newGameButton;
    private Button leaderboardButton;
    private Button exitButton;

    public NameView() {
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        nameField = new TextField();
        resumeGameButton = new Button("Resume Game");
        newGameButton = new Button("New Game");
        leaderboardButton = new Button("Leaderboard");
        exitButton = new Button("Exit");
    }

    private void layoutNodes() {
        Label nameLabel = new Label("Enter your name: ");

        VBox leftButtonsVBox = createButtonsVBox(resumeGameButton, leaderboardButton);
        VBox rightButtonsVBox = createButtonsVBox(newGameButton, exitButton);
        HBox buttonHBox = createButtonsHBox(leftButtonsVBox, rightButtonsVBox);

        this.setNameViewLayout(nameLabel, buttonHBox);
    }

    TextField getNameField() {
        return this.nameField;
    }

    Button getNewGameButton() {
        return this.newGameButton;
    }

    Button getResumeGameButton() {
        return this.resumeGameButton;
    }

    Button getLeaderboardButton() {
        return this.leaderboardButton;
    }

    Button getExitButton() {
        return this.exitButton;
    }

    private VBox createButtonsVBox(Button button1, Button button2) {
        VBox buttonsVBox = new VBox();
        buttonsVBox.setSpacing(20);
        buttonsVBox.setAlignment(Pos.CENTER);

        buttonsVBox.getChildren().addAll(button1, button2);

        return buttonsVBox;
    }

    private HBox createButtonsHBox(VBox leftButtonsVBox, VBox rightButtonsVBox) {
        HBox buttonsHBox = new HBox();
        buttonsHBox.setAlignment(Pos.CENTER);
        buttonsHBox.setPadding(new Insets(15, 0, 0, 0));
        buttonsHBox.setSpacing(30);
        buttonsHBox.getChildren().addAll(leftButtonsVBox, rightButtonsVBox);

        return buttonsHBox;
    }

    private void setNameViewLayout(Label nameLabel, HBox buttonHBox) {
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(nameLabel, nameField, buttonHBox);
        this.setSpacing(10);
        this.setPadding(new Insets(20));
    }

}
