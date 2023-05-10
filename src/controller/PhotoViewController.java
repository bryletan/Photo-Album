package controller;

import model.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Controller used to control "photoview.fxml" file.
 * 
 * @author Bryle Tan
 * @author Maanas Pimplikar
 */
public class PhotoViewController {

    /**
     * Reference to singleton instance of PhotoController.
     */
    protected PhotoController photoController;

    /**
     * Text object that shows the name of the album.
     */
    @FXML public Text title;

    /**
     * ListView of type Photo that shows the photos that are in the curent album.
     */
    @FXML public ListView<Photo> photoListView;

    /**
     * Button object with id "logoutButton". Used for logging out.
     */
    @FXML public Button logoutButton;

    /**
     * Button object with id "removeButton". Used for removing a photo from the album.
     */
    @FXML public Button removeButton;

    /**
     * Button object with id "openButton". Used for opening a photo.
     */
    @FXML public Button openButton;

    /**
     * Button object with id "selectButton". Used for adding a new photo to the album.
     */
    @FXML public Button selectButton;

    /**
     * Button object with id "backButton". Used for going back to the non-admin subsystem.
     */
    @FXML public Button backButton;

    /**
     * Button object with id "captionEditButton". Used to edit the caption of a photo.
     */
    @FXML public Button captionEditButton;

    /**
     * Button object with id "captionDoneButton". Used when the user is done editing the caption.
     */
    @FXML public Button captionDoneButton;

    /**
     * Button object with id "addTagButton". Used for adding a new tag to the photo.
     */
    @FXML public Button addTagButton;

    /**
     * Button object with id "delTagButton". Used for deleting a tag of the photo.
     */
    @FXML public Button delTagButton;
    
    /**
     * Button object with id "addTagTypeButton". Used for adding a new tag type.
     */
    @FXML public Button addTagTypeButton;

    /**
     * Button object with id "delTagTypeButton". Used for deleting a new tag type.
     */
    @FXML public Button delTagTypeButton;

    /**
     * Button object with id "copyButton". Used for copying a photo to another album.
     */
    @FXML public Button copyButton;

    /**
     * Button object with id "createAlbumButton". Used for creating an album with the searched photos.
     */
    @FXML public Button createAlbumButton;

    /**
     * Button object with id "moveButton". Used to move a photo to another album.
     */
    @FXML public Button moveButton;

    /**
     * TextArea object with id "captionArea" that displays the caption of a photo.
     */
    @FXML public TextArea captionArea;

    /**
     * TextArea object with id "tagArea" that displays the tags of a photo.
     */
    @FXML public TextArea tagArea;

    /**
     * TextField object with id "tagValueField" that takes in a new tag value for a photo.
     */
    @FXML public TextField tagValueField;

    /**
     * TextField object with id "tagTypeField" that takes in a new tag type.
     */
    @FXML public TextField tagTypeField;

    /**
     * ComboBox of type String that allows the user to choose between their tag types.
     */
    @FXML public ComboBox<String> tagsCBox;

    /**
     * Label object with id "photoName" used to show the name of the photo.
     */
    @FXML public Label photoName;

    /**
     * Label object with id "photoDate" used to show the date of the photo.
     */
    @FXML public Label photoDate;

    /**
     * Label object with id "photoAlbumList" used to show which albums the photo is a part of.
     */
    @FXML public Label photoAlbumList;

    /**
     * Label object with id "searchInfo" used to show the search conditions. 
     */
    @FXML public Label searchInfo;

    /**
     * Event method called by all events.
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
