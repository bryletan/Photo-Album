package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import model.*;

/**
 * Controller used to control "nonadmin.fxml" file.
 * 
 * @author Bryle Tan
 * @author Maanas Pimplikar
 */
public class NonAdminController {
    
    /**
     * Reference to singleton instance of PhotoController.
     */
    protected PhotoController photoController;
    
    /**
     * ListView of type Album to display the albums for a user.
     */
    @FXML public ListView<Album> albumListView;

    /**
     * Button object with id "createButton". Used for creating a new album.
     */
    @FXML public Button createButton;

    /**
     * Button object with id "searchButton". Used for searching for photos.
     */
    @FXML public Button searchButton;

    /**
     * Button object with id "deleteButton". Used for deleting an album.
     */
    @FXML public Button deleteButton;

    /**
     * Button object with id "logoutButton". Used for logging out.
     */
    @FXML public Button logoutButton;

    /**
     * Button object with id "openButton". Used for opening an album.
     */
    @FXML public Button openButton;

    /**
     * Button object with id "renameButton". Used for renaming an album.
     */
    @FXML public Button renameButton;
    
    /**
     * Label object with id "albumName". Label to show the album's name.
     */
    @FXML public Label albumName;

    /**
     * Label object with id "albumSize". Label to show the album's size.
     */
    @FXML public Label albumSize;

    /**
     * Label object with id "albumDate". Label to show the album's date range.
     */
    @FXML public Label albumDate;

    /**
     * Label object with id "albumsText". Label to show the the current album's name.
     */
    @FXML public Label albumsText;

    /**
     * TextField object with id "newAlbumName". Used to get the name for the new album.
     */
    @FXML public TextField newAlbumName;

    /**
     * TextField object with id "searchFirstTag". Used to get the first tag criteria.
     */
    @FXML public TextField searchFirstTag;

    /**
     * TextField object with id "searchSecondTag". Used to get the second tag criteria.
     */
    @FXML public TextField searchSecondTag;

    /**
     * TextField object with id "startYearsField". Used to get the lower bound's year.
     */
    @FXML public TextField startYearsField;

    /**
     * TextField object with id "endYearsField". Used to get the upper bound's year.
     */
    @FXML public TextField endYearsField;

    /**
     * TextField object with id "startDayField". Used to get the lower bound's date.
     */
    @FXML public TextField startDayField;

    /**
     * TextField object with id "startDayField". Used to get the upper bound's date.
     */
    @FXML public TextField endDayField;

    /**
     * ChoiceBox object of type String. Has the choices for the conjunction.
     */
    @FXML public ChoiceBox<String> searchCBox;

    /**
     * ChoiceBox object of type String. Has the choices for the lower bound's months.
     */
    @FXML public ChoiceBox<String> startMonthsCBox;

    /**
     * ChoiceBox object of type String. Has the choices for the upper bound's months.
     */
    @FXML public ChoiceBox<String> endMonthsCBox;

    /**
     * Event handling method called by all action events.
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
