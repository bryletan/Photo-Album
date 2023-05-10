package view;

import javafx.event.ActionEvent;
import controller.*;

/**
 * Abstract class that all states extend.
 * 
 * @author Bryle Tan
 * @author Maanas Pimplikar
 */
public abstract class PhotoState {
    
    /**
     * Reference to the singleton instance of PhotoController.
     */
    public static PhotoController photoController;

    /**
     * Reference to the most-recent event of the application.
     */
    public static ActionEvent lastEvent;

    /**
     * Called when entering the state.
     */
    public abstract void enter();

    /**
     * Called when leaving the state.
     */
    public abstract void leave();

    /**
     * Method to process an event on the state.
     * 
     * @return PhotoState representing the current state of the application.
     */
    public abstract PhotoState processEvent();

    /**
     * String representation of the state.
     */
    public String toString() {
        return getClass().toString();
    }
}
