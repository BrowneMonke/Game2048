import javafx.application.Application;
import javafx.stage.Stage;
import view.gameView.GamePresenter;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        GamePresenter.showStartScreen(primaryStage);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

