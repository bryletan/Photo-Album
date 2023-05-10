package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

/**
 * Controller used to control the "slideshow.fxml" file.
 * 
 * @author Bryle Tan
 * @author Maanas Pimplikar
 */
public class SlideShowController {
    
    /**
     * Reference to the singleton instance of PhotoController.
     */
    protected PhotoController photoController;

    /**
     * ImageView object used to display the photo.
     */
    @FXML public ImageView imageView;

    /**
     * Label object used to show the name of the photo.
     */
    @FXML public Label photoNameLabel;

    /**
     * Button object with id "nextButton". Used to move to the next photo in the album.
     */
    @FXML public Button nextButton;

    /**
     * Button object with id "prevButton". Used to move to the previous photo in the album.
     */
    @FXML public Button prevButton;

    /**
     * Button object with id "backButton". Used to go back to viewing all album photos.
     */
    @FXML public Button backButton;

    /**
     * TextArea object with id "captionArea" used to display the caption of the photo.
     */
    @FXML public TextArea captionArea;

    /**
     * TextArea object with id "tagArea" used to show the tags of the photo.
     */
    @FXML public TextArea tagArea;

    /**
     * TextArea object with id "dateAtea" used to show the date of the photo.
     */
    @FXML public TextArea dateArea;

    /**
     * Method used to handle all events.
     * 
     * @param event ActionEvent object obtained when an event is triggered.
     */
    public void handleEvent(ActionEvent event) {
        photoController.processEvent(event);
    }

    /**
     * Setter method to add the singleton instance of PhotoController.
     * 
     * @param photoController PhotoController object.
     */
    public void setPhotoController(PhotoController photoController) {
        this.photoController = photoController;
    }
}
