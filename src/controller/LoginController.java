package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

/**
 * Controller used to control "login.fxml" file.
 * 
 * @author Bryle Tan
 * @author Maanas Pimplikar
 */
public class LoginController {
    
    /**
     * Reference to the singleton instance of PhotoController.
     */
    protected PhotoController photoController;

    /**
     * TextField object with id "usernameField". Used to get the username of user that is
     * trying to log into the system.
     */
    @FXML public TextField usernameField;

    /**
     * Button object with id "loginButton". Used when the user wants to login.
     */
    @FXML public Button loginButton;

    /**
     * Event handling method called by all events.
     * 
     * @param event ActionEvent object obtained when an event is triggered.
     */
    public void handleEvent(ActionEvent event) {
        photoController.processEvent(event);
    }

    /**
     * Setter method to set the singleton instance of PhotoController.
     * 
     * @param photoController PhotoController object.
     */
    public void setPhotoController(PhotoController photoController) {
        this.photoController = photoController;
    }
}
