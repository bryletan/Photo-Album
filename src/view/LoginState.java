package view;

import controller.*;
import model.*;

import javafx.scene.control.Button;

/**
 * Initial substate that the application will enter when application is launched. 
 * Class is used to implement the functionality of the LoginController.
 * 
 * @author Bryle Tan
 * @author Maanas Pimplikar
 */
public class LoginState extends PhotoState {

    /**
     * Reference to the singleton instance of LoginState.
     */
    private static LoginState instance = null;

    /**
     * Sets currentUser to the User that logs into the application.
     */
    protected User currentUser;

    /**
     * Empty constructor of LoginState.
     */
    private LoginState() {

    }

    /**
     * Accessed when logging out of the NonAdminState.
     * Method that sets the LoginState to visible.
     */
    public void enter() {
        photoController.loginController.usernameField.setText("");
        photoController.login.setVisible(true);
    }

    /**
     * Method to process event of Button press. 
     * Implements login button.
     * 
     * @return PhotoState representing the current state of the application.
     */
    public PhotoState processEvent() {
        Button b = (Button)lastEvent.getSource();

        //logging in
        if(b == photoController.loginController.loginButton) {
            String username = photoController.loginController.usernameField.getText();
                
            if(username.equals("admin")) {
                leave();
                photoController.adminState.enter();
                return photoController.adminState;
            }

            for(User user: PhotoController.userList) {
                if(username.equals(user.getUserName())) {
                    leave();
                    currentUser = user;
                    photoController.nonAdminState.enter();
                    return photoController.nonAdminState;
                }
            }
            Popups.incorrectLogin();
            photoController.loginController.usernameField.setText(""); //reset textfield if incorrect username
        
        }

        return photoController.currentState;
    }

    /**
     * Method that sets the LoginState to non-visible.
     */
    public void leave() {
        PhotoState.photoController.login.setVisible(false);
    }

    /**
     * Method to return the singleton instance of LoginState.
     * 
     * @return Instance of LoginState.
     */
    public static LoginState getInstance() {
        if(instance == null) {
            instance = new LoginState();
        }

        return instance;
    }
}
