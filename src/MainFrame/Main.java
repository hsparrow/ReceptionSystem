package MainFrame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Database.DatabaseApi;
import org.opencv.core.Core;

/**
 *
 * @author KanBaru
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainFrame/Face.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root, 1200, 800);

            primaryStage.setTitle("Face Recognition");
            primaryStage.setScene(scene);
            primaryStage.show();

            Controller controller = loader.getController();

            primaryStage.setOnCloseRequest((event -> controller.setClosed()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        launch(args);
        
         DatabaseApi api=new DatabaseApi();
         api.getcon();
         api.displayAllStudent();
    }
}
