package view;

import javafx.scene.control.Button;
import model.Photo;

/**
 * Substate that the PhotoViewState enters when the user is editing the caption
 * of a selected Photo.
 * 
 * Disables all other buttons until the user presses the Done button.
 */
public class CaptionEditSubstate extends PhotoState{

    /**
     * Reference to the singleton instance of CaptionEditSubstate.
     */
    private static CaptionEditSubstate instance = null;

    /**
     * Photo with caption being edited.
     */
    private Photo editPhoto;

    /**
     * Empty constructor of CaptionEditSubstate.
     */
    private CaptionEditSubstate() {

    }

    /**
     * Sets up required buttons and the caption area.
     */
    public void enter() {
        // make it editable
        photoController.photoviewController.captionArea.setEditable(true);

        // change the hint
        photoController.photoviewController.captionArea.promptTextProperty().set("Enter a caption...");

        // disable all other buttons
        photoController.photoviewController.logoutButton.setDisable(true);
        photoController.photoviewController.removeButton.setDisable(true);
        photoController.photoviewController.openButton.setDisable(true);
        photoController.photoviewController.selectButton.setDisable(true);
        photoController.photoviewController.backButton.setDisable(true);
        photoController.photoviewController.captionEditButton.setDisable(true);
        photoController.photoviewController.addTagButton.setDisable(true);
        photoController.photoviewController.addTagTypeButton.setDisable(true);
        photoController.photoviewController.moveButton.setDisable(true);
        photoController.photoviewController.copyButton.setDisable(true);
        photoController.photoviewController.delTagButton.setDisable(true);
        photoController.photoviewController.delTagTypeButton.setDisable(true);
        // enable the done button
        photoController.photoviewController.captionDoneButton.setDisable(false);
    }

    /**
     * Resets buttons and sets caption for photo.
     */
    public void leave() {
        this.editPhoto = null;

        // disable editing
        photoController.photoviewController.captionArea.setEditable(true);

        // re-enable all buttons
        photoController.photoviewController.logoutButton.setDisable(false);
        photoController.photoviewController.removeButton.setDisable(false);
        photoController.photoviewController.openButton.setDisable(false);
        photoController.photoviewController.selectButton.setDisable(false);
        photoController.photoviewController.backButton.setDisable(false);
        photoController.photoviewController.captionEditButton.setDisable(false);
        photoController.photoviewController.addTagButton.setDisable(false);
        photoController.photoviewController.addTagTypeButton.setDisable(false);
        photoController.photoviewController.moveButton.setDisable(false);
        photoController.photoviewController.copyButton.setDisable(false);
        photoController.photoviewController.delTagButton.setDisable(false);
        photoController.photoviewController.delTagTypeButton.setDisable(false);

        // re-disable the done button
        photoController.photoviewController.captionDoneButton.setDisable(true);
    }

    /**
     * Sets Photo object to photo with caption being edited.
     * @param photo Photo that is being edited.
     */
    public void setPhoto(Photo photo) {
        this.editPhoto = photo;
    }

    /**
     * Method to process event of Button press. 
     * Implements done button.
     * 
     * @return PhotoState representing the current state of the application.
     */
    public PhotoState processEvent() {
        Button b = (Button)lastEvent.getSource();

        if(b == photoController.photoviewController.captionDoneButton) {
            String newCaption = photoController.photoviewController.captionArea.getText();
            this.editPhoto.setCaption(newCaption);

            leave();
            photoController.photoViewState.enter();
            return photoController.photoViewState;
        }

        // default
        return photoController.currentState;
    }

    /**
     * Method to return the singleton instance of CaptionEditSubstate.
     * 
     * @return Instance of CaptionEditSubstate.
     */
    public static CaptionEditSubstate getInstance() {
        if(instance == null) {
            instance = new CaptionEditSubstate();
        }

        return instance;
    }
}
