package view;

import java.io.*;

import controller.*;
import model.*;

import javafx.collections.*;
import javafx.scene.control.Button;

/**
 * Substate that the LoginState enters when the user enters "admin" in the login. 
 * Class is used to implement the functionality of the AdminController.
 * 
 * @author Bryle Tan
 * @author Maanas Pimplikar
 */
public class AdminState extends PhotoState{

    /**
     * Reference to the singleton instance of AdminState.
     */
    private static AdminState instance = null;

    /**
     * Empty constructor of AdminState.
     */
    private AdminState() {
        
    }

    /**
     * Method that sets the AdminState to visible.
     */
    public void enter() {
        photoController.admin.setVisible(true);   
    }

    /**
     * Method to process event of Button press. 
     * Implements add, delete, and logout buttons. 
     * 
     * @return PhotoState representing the current state of the application.
     */
    public PhotoState processEvent() {
        Button b = (Button)lastEvent.getSource();

        //adding user
        if(b == photoController.adminController.createButton) {
            if(photoController.adminController.addUser.getText().equals("")) {
                Popups.noValueError();
                return photoController.adminState;
            }
            User userToAdd = new User(photoController.adminController.addUser.getText());

            for(User u : PhotoController.userList) {
                if(u.getUserName().equals(userToAdd.getUserName())) {
                    Popups.duplicateError();
                    return photoController.currentState;
                }
            }

            PhotoController.userList.add(userToAdd);
            photoController.adminController.userListView.setItems(PhotoController.userList);
            photoController.adminController.addUser.setText("");
            return photoController.adminState;
        }
        //deleting user
        else if(b == photoController.adminController.deleteButton) {
            User userToRemove = photoController.adminController.userListView.getSelectionModel().getSelectedItem();
            int userIndex = photoController.adminController.userListView.getSelectionModel().getSelectedIndex();
            if(userToRemove == null) {
                Popups.noUserError();
                return photoController.currentState;
            }
            if(userToRemove.getUserName().equals("stock")) {
                Popups.deleteStockError();
                return photoController.currentState;
            }
            if(PhotoController.userList.size() == 0) {
                Popups.noSelectionError();
                return photoController.currentState;
            }

            PhotoController.userList.remove(userToRemove);
            photoController.adminController.userListView.getSelectionModel().select(userIndex);
        }
        //logging out
        else {
            //when logging out, save the users in userlist
            try {
                FileOutputStream userSave = new FileOutputStream("data/users.dat");
                ObjectOutputStream oos = new ObjectOutputStream(userSave);
                
                ObservableList<User> updatedList = photoController.adminController.getList();
    
                for(User user : updatedList) {
                    oos.writeObject(user);
                }
    
                oos.close();
            }
            catch (Exception e) {

            }
            
            photoController.adminState.leave();
            photoController.loginState.enter();
            return photoController.loginState;
        }
        
        return photoController.currentState;
    }

    /**
     * Method that sets the AdminState to non-visible.
     */
    public void leave() {
        PhotoState.photoController.admin.setVisible(false);
    }

    /**
     * Method to return the singleton instance of AdminState.
     * 
     * @return Instance of AdminState.
     */
    public static AdminState getInstance() {
        if(instance == null) {
            instance = new AdminState();
        }

        return instance;
    }
}
