package view.leaderboardView;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class LeaderboardView extends VBox {
    private MenuItem miGoBack;
    private MenuItem miAbout;
    private MenuItem miRules;
    private MenuItem miExitGame;
    private ListView<String> leaderboardListView;
    private Label goBackLabel;


    public LeaderboardView() {
        initialiseNodes();
        layoutNodes();
    }

    public LeaderboardView(boolean isInGame) {
        initialiseNodes();
        if (isInGame) {
            initialiseExitNode();
        }
        layoutNodes();
    }

    private void initialiseExitNode() {
        this.miExitGame = new MenuItem("Exit Game");
    }

    private void initialiseNodes() {
        //Create menu items
        this.miAbout = new MenuItem("About");
        this.miRules = new MenuItem("Rules");
        this.miGoBack = new MenuItem("Go Back");

        // Create the Label for the leaderboard back button
        this.goBackLabel = new Label("Back");

        // Create the ListView to display leaderboard entries
        this.leaderboardListView = new ListView<>();
    }

    private void layoutNodes() {
        //menu
        MenuBar menuBar = createMenu();
        //Page title
        Label titleLabel = createTitleLabel();
        //High scores list
        styleLeaderboard();
        //goBack label
        styleGoBackLabel();

        this.getChildren().addAll(menuBar, titleLabel, leaderboardListView, new Label(), goBackLabel);//"new Label()" is used here to position the goBackLabel correctly
        this.setAlignment(Pos.TOP_CENTER);
        this.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
    }

    ListView<String> getLeaderboardListView() {
        return leaderboardListView;
    }

    MenuItem getMiGoBack() {
        return miGoBack;
    }

    MenuItem getMiAbout() {
        return miAbout;
    }

    MenuItem getMiRules() {
        return miRules;
    }

    MenuItem getMiExitGame() {
        return this.miExitGame;
    }

    Label getGoBackLabel() {
        return goBackLabel;
    }


    private MenuBar createMenu() {
        Menu optionsMenu;
        if (this.miExitGame == null) {
            optionsMenu = new Menu("Options", null, miGoBack);
        } else {
            optionsMenu = new Menu("Options", null, miGoBack, new SeparatorMenuItem(), miExitGame);
        }
        Menu helpMenu = new Menu("Help", null, miRules, new SeparatorMenuItem() , miAbout);
        return new MenuBar(optionsMenu, helpMenu);
    }

    private Label createTitleLabel() {
        Label titleLabel = new Label("Leaderboard");
        titleLabel.getStyleClass().add("title-label");
        titleLabel.setPrefSize(300, 100);
        titleLabel.setBackground(new Background(new BackgroundFill(Color.BURLYWOOD, new CornerRadii(7), new Insets(10))));
        titleLabel.setAlignment(Pos.CENTER);

        return titleLabel;
    }

    private void styleLeaderboard() {
        this.leaderboardListView.getStyleClass().add("list-view");
        this.leaderboardListView.setPrefSize(450, 387);
        this.leaderboardListView.setMaxSize(450, 387);
    }

    private void styleGoBackLabel() {
        this.goBackLabel.getStyleClass().add("back-button");
        this.goBackLabel.setPrefSize(150, 50);
        this.goBackLabel.setMaxSize(150, 75);
        this.goBackLabel.setBackground(new Background(new BackgroundFill(Color.BURLYWOOD, new CornerRadii(7), new Insets(10))));
        this.goBackLabel.setAlignment(Pos.CENTER);
    }

}
