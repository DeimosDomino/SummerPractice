package graphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class MainWindow extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainWindowFXML.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage.setScene(scene);

        MainWindowController controller = loader.getController();
        controller.setPrimaryStage(stage);
        controller.setup();

        stage.setTitle("Ford-Bellman algorithm");
        stage.getIcons().add(new Image(getClass().getResource("images/iconLogo.png").toURI().toString()));
        stage.setWidth(800);
        stage.setHeight(600);
        stage.show();
    }
}