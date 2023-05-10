package model;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;

/**
 * Class containing static methods for popups.
 * 
 * @author Maanas Pimplikar
 * @author Bryle Tan
 */
public class Popups {
    /**
     * Used to get name of the album a photo is being copied to.
     * 
     * @return String name of the destination album.
     */
    public static String copySinglePhoto() {
        TextInputDialog td = new TextInputDialog();
        td.setTitle("Enter the name of the album...");
        td.setHeaderText("Note: please enter a valid album name.");

        Optional<String> albumName = td.showAndWait();

        if(albumName.isEmpty()) {
            return "";
        }
        
        return albumName.get();
    }

    /**
     * Used to get the name of the new album that will contain the searched photos.
     * 
     * @return String name of the new album.
     */
    public static String createAlbumFromSearch() {
        TextInputDialog td = new TextInputDialog();
        td.setTitle("Enter the name of new album...");
        td.setHeaderText("Note: Please enter a valid name.");

        Optional<String> albumName = td.showAndWait();

        if(albumName.isEmpty()) {
            return "";
        }

        return albumName.get();
    }

    /**
     * Used to get the name of the album that the photo is being moved to.
     * 
     * @return String name of the destination album.
     */
    public static String movePhoto() {
        TextInputDialog td = new TextInputDialog();
        td.setTitle("Enter name of destination album...");
        td.setHeaderText("Note: Please enter a valid album name.");

        Optional<String> albumName = td.showAndWait();

        if(albumName.isEmpty()) {
            return "";
        }

        return albumName.get();
    }
    
    /**
     * Used when a user tries to log in with a username that is not contained in the database.
     */
    public static void incorrectLogin() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Login Error");
        alert.setHeaderText("Incorrect username.");
        alert.setContentText("Press OK to continue.");
        alert.showAndWait();
    }

    /**
     * Used when the user has not selected an album or a photo.
     */
    public static void noSelectionError() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("ERROR! There is no selected item.");
        alert.setContentText("Press OK to continue.");
        alert.showAndWait();
    }
    
    /**
     * Used when the admin has not selected a user in the admin subsystem.
     */
    public static void noUserError() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("ERROR! There is no selected user.");
        alert.setContentText("Press OK to continue.");
        alert.showAndWait();
    }

    /**
     * Used when the user is trying to input a duplicate value.
     */
    public static void duplicateError() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("ERROR! This value already exists.");
        alert.setContentText("Press OK to continue.");
        alert.showAndWait();
    }

    /**
     * Used when the user tries to input a blank value.
     */
    public static void noValueError() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("ERROR! There is no value written.");
        alert.setContentText("Press OK to continue.");
        alert.showAndWait();
    }

    /**
     * Used when additional information is needed for a given action.
     */
    public static void needInformationError() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("ERROR! Provide more information.");
        alert.setContentText("Press OK to continue.");
        alert.showAndWait();
    }

    /**
     * Used when the user is trying to search using two tags without a conjunction.
     */
    public static void noConjunctionError() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("ERROR! Provide an AND/OR to search for two tags.");
        alert.setContentText("Press OK to continue.");
        alert.showAndWait();
    }

    /**
     * Used when the admin tries to delete the stock user.
     */
    public static void deleteStockError() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("ERROR! The stock user cannot be deleted.");
        alert.setContentText("Press OK to continue.");
        alert.showAndWait();
    }

    /**
     * Used when a photo's path does not exist on the user's machine.
     */
    public static void fileNotFoundError() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("ERROR! The selected photo does not exist on your machine.");
        alert.setContentText("Press OK to continue.");
        alert.showAndWait();
    }

    /**
     * Used when the user is trying to delete a tag that is not applied to a photo.
     */
    public static void noTagError() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("ERROR! Unable to delete tag since it does not exist.");
        alert.setContentText("Press OK to continue.");
        alert.showAndWait();
    }

    /**
     * Used when the user is trying to delete a tag type that is not in the user's subsystem.
     */
    public static void noTagTypeError() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("ERROR! Unable to delete tag type since it does not exist.");
        alert.setContentText("Press OK to continue.");
        alert.showAndWait();
    }
}
