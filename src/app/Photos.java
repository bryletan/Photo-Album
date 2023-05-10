package app;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;

import controller.*;
import model.*;

/**
 * This class contains the main method and is the class that runs the Photo Album application
 * 
 * @author Bryle Tan
 * @author Maanas Pimplikar
 */
public class Photos extends Application {
    
    /**
     * The application's main stage.
     */
    public static Stage mainStage;

    /**
     * The application's main scene.
     */
    public static Scene scene;

    /**
     * Controller used to control the entire application.
     */
    private PhotoController photoController; 

    /**
     * Application start method.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/photo.fxml"));
            AnchorPane root = (AnchorPane)loader.load();

            scene = new Scene(root);

            mainStage.setScene(scene);
            mainStage.setTitle("Photo Album");
            mainStage.setResizable(false);
            mainStage.show();

            photoController = loader.getController();
            photoController.start();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


    /**
     * Application stop method. Called when the application is exited.
     */
    @Override
    public void stop() throws IOException { 
        FileOutputStream userSave = new FileOutputStream("data/users.dat");
        ObjectOutputStream oos = new ObjectOutputStream(userSave);
        
        ObservableList<User> updatedList = photoController.adminController.getList();

        for(User user : updatedList) {
            oos.writeObject(user);
        }

        oos.close();

        photoController.photoViewState.stockPhotos.clear();
    }

    /**
     * Main method for the class.
     * 
     * @param args arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
