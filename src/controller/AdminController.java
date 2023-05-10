package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Button;

import model.*;

/**
 * Class used to control "admin.fxml" file.
 * 
 * @author Bryle Tan
 * @author Maanas Pimplikar
 */
public class AdminController {

    /**
     * Reference to the singleton instance of PhotoController.
     */
    protected PhotoController photoController;

    /**
     * Text object with the id "userListText". Label for the listview showing all users.
     */
    @FXML public Text userListText;

    /**
     * Button object with the id "createButton". Used for creating a new user.
     */
    @FXML public Button createButton;

    /**
     * Button object with the id "deleteButton". Used for deleting an existing user.
     */
    @FXML public Button deleteButton;

    /**
     * Button object with the id "logoutButton". Used for logging out.
     */
    @FXML public Button logoutButton;

    /**
     * TextField object with the id "addUser". Used to get name of the new user.
     */
    @FXML public TextField addUser;

    /**
     * ListView object of type User. Shows all users that are currently in the system.
     */
    @FXML public ListView<User> userListView;
 
    /**
     * Method to add a new user to the list of users.
     * 
     * @param e ActionEvent object obtained when the "createButton" is pressed.
     */
    public void addUser(ActionEvent e) {
        User userToAdd = new User(addUser.getText());

        for(User u : PhotoController.userList) {
            if(u.getUserName().equals(userToAdd.getUserName())) {
                Popups.duplicateError();
                return; 
            }
        }

        PhotoController.userList.add(userToAdd);
        userListView.setItems(PhotoController.userList);
        addUser.setText("");
    }

    /**
     * Event handling method called by all events.
     * 
     * @param event ActionEvent object obtained when an event is triggered.
     */
    public void handleEvent(ActionEvent event) {
        photoController.processEvent(event);
    }

    /**
     * Getter for the list of users.
     * 
     * @return ObservableList of type User.
     */
    public ObservableList<User> getList() {
        return PhotoController.userList;
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
