package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import view.*;
import model.*;

/**
 * Highest level controller that controls all substates and all other controllers.
 * 
 * @author Bryle Tan
 * @author Maanas Pimplikar
 */
public class PhotoController {

    /**
     * PhotoState object to keep track of the application's current state.
     */
    public PhotoState currentState; 

    /**
     * Singleton instance of the login state.
     */
    public LoginState loginState;

    /**
     * Singleton instance of the admin state.
     */
    public AdminState adminState;

    /**
     * Singleton instance of the non-admin state.
     */
    public NonAdminState nonAdminState;

    /**
     * Singleton instance of the photo view state.
     */
    public PhotoViewState photoViewState;

    /**
     * Singleton instance of the caption editing state.
     */
    public CaptionEditSubstate captionEditState;

    /**
     * Singleton instance of the slide show state.
     */
    public SlideShowState slideShowState;

    /**
     * AnchorPane object that takes the login page.
     */
    @FXML public AnchorPane login;

    /**
     * AnchorPane object that takes the admin subsystem page.
     */
    @FXML public AnchorPane admin;

    /**
     * AnchorPane object that takes the non-admin subsystem page.
     */
    @FXML public AnchorPane nonadmin;
    
    /**
     * AnchorPane object that takes the photo view page.
     */
    @FXML public AnchorPane photoview;

    /**
     * AnchorPane object that takes the slide show page.
     */
    @FXML public AnchorPane slideshow;

    /**
     * Singleton instance of AdminController.
     */
    @FXML public AdminController adminController;
    
    /**
     * Singleton instance of LoginController.
     */
    @FXML public LoginController loginController;

    /**
     * Singleton instance of NonAdminController.
     */
    @FXML public NonAdminController nonadminController;

    /**
     * Singleton instance of PhotoViewController.
     */
    @FXML public PhotoViewController photoviewController;

    /**
     * Singleton instance of SlideShowController.
     */
    @FXML public SlideShowController slideshowController;

    /**
     * ObservableList of type User. Contains all the users of the application.
     */
    public static ObservableList<User> userList = FXCollections.observableArrayList();

    /**
     * Start method that is called when the application is launched.
     * 
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     */
    public void start() throws IOException, FileNotFoundException, ClassNotFoundException {
        /**
         * File reading for userList. PhotoController handles the file reading while AdminController handles the adding/removing of users
         */
        FileInputStream userSave = new FileInputStream("data/users.dat");
        ObjectInputStream ois = new ObjectInputStream(userSave);
        User temp;

        try {
            while((temp = (User)ois.readObject()) != null) {
                userList.add(temp);
            }
        }
        catch(Exception e) {
            //catching the EOFException that occurs with OIS objects
        }

        ois.close();
        adminController.userListView.setItems(userList);

        loginController.setPhotoController(this);
        adminController.setPhotoController(this);
        nonadminController.setPhotoController(this);
        photoviewController.setPhotoController(this);
        slideshowController.setPhotoController(this);

        PhotoState.photoController = this;

        loginState = LoginState.getInstance();
        adminState = AdminState.getInstance();
        nonAdminState = NonAdminState.getInstance();
        photoViewState = PhotoViewState.getInstance();
        captionEditState = CaptionEditSubstate.getInstance();
        slideShowState = SlideShowState.getInstance();

        currentState = loginState;
        currentState.enter();
    }

    /**
     * Method that is called when any controller calls their handleEvent method.
     * Deligates the event to the PhotoState that corresponds to the current state of the system.
     * 
     * @param e ActionEvent object obtained when an event is triggered.
     */
    public void processEvent(ActionEvent e) {
        PhotoState.lastEvent = e;
        currentState = currentState.processEvent();
    }
}