package graphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class MainWindow extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainWindowFXML.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.setTitle("Ford-Bellman algorithm");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.show();
    }
}