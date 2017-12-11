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
            //【最终版】用脸登陆login界面
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainFrame/LoginFace.fxml"));

            AnchorPane root = loader.load();
            Scene scene = new Scene(root, 700, 400);

            primaryStage.setTitle("Face Recognition");
            primaryStage.setScene(scene);
            primaryStage.show();

            //【测试用】直接进入主界面
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainFrame/Face.fxml"));
//          
//            AnchorPane root = loader.load();
//            Scene scene = new Scene(root, 1400, 800);

//            primaryStage.setTitle("Face Recognition");
//            primaryStage.setScene(scene);
//            primaryStage.show();
//            Controller controller = loader.getController();
//            primaryStage.setOnCloseRequest((event -> controller.setClosed()));
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
//         api.displayAllStudent();
    }
}
