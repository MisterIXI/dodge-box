

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        Application.launch(args);
        // getting started

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub

		BorderPane border = new BorderPane();
		Scene mainScene = new Scene(border);
		primaryStage.setScene(mainScene);
		primaryStage.show();
    }

}
