package view;

import model.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * Substate that the LoginState enters when the user enters a valid username in the login.
 * Class is used to implement the functionality of the NonAdminController.
 * 
 * @author Bryle Tan
 * @author Maanas Pimplikar
 */
public class NonAdminState extends PhotoState {

    /**
     * Reference to the singleton instance of NonAdminState.
     */
    private static NonAdminState instance = null;

    /**
     * List of current user's albums. Used in displaying album list view.
     */
    private ObservableList<Album> albums;

    /**
     * List with OR/AND. Used for searching tags with conjunction/disjunction.
     */
    private ObservableList<String> tagSearchObsList = FXCollections.observableArrayList();

    /**
     * List with months of the year. Used for searching dates. 
     */
    private ObservableList<String> monthsObsList = FXCollections.observableArrayList();

    /**
     * Empty constructor of NonAdminState.
     */
    private NonAdminState() {
        
    }

    /**
     * Accessed when logged in from LoginState or leaving the PhotoViewState.
     * Method that sets the NonAdminState to visible.
     */
    public void enter() {
        photoController.nonadmin.setVisible(true);
        photoController.nonadminController.albumsText.setWrapText(true);
        photoController.nonadminController.albumsText.setText(photoController.loginState.currentUser.getUserName() + "'s Albums");

        // load the months search combobox
        if(monthsObsList.size() != 12) {
            loadMonths();
        }
        // load the tag search combobox
        if(tagSearchObsList.size() < 2) {
            tagSearchObsList.add("OR");
            tagSearchObsList.add("AND");
        }
        photoController.nonadminController.searchCBox.setItems(tagSearchObsList);

        // load the current user's data by calling the method
        loadData(photoController.loginState.currentUser);

        // adding the event listener for updating the labels
        photoController.nonadminController.albumListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> updateLabels());

        // adding the event listener to add double-click functionality
        photoController.nonadminController.albumListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Album selectedAlbum = photoController.nonadminController.albumListView.getSelectionModel().getSelectedItem();
                if(selectedAlbum != null && event.getClickCount() == 2) {
                    photoController.nonadminController.openButton.fire();
                }
            }
        });
    }
    
    /**
     * Updates the labels of the current album selected
     */
    public void updateLabels() {
        if(albums.size() == 0) {
            photoController.nonadminController.albumName.setText("");
            photoController.nonadminController.albumSize.setText("");
            photoController.nonadminController.albumDate.setText("");
            return;
        }

        Album album = photoController.nonadminController.albumListView.getSelectionModel().getSelectedItem();
        
        if(album == null) {
            return;
        }
        
        photoController.nonadminController.albumName.setText(album.getName());
        if(album.getSize() == 1) {
            photoController.nonadminController.albumSize.setText("1 photo");
        }
        else {
            photoController.nonadminController.albumSize.setText(album.getSize() + " photos");
        }
        photoController.nonadminController.albumDate.setText(album.getDate());
    }

    /**
     * Takes the selected album and sets it as the current album in PhotoViewState, then transitions to PhotoViewState.
     * 
     * @param selectedAlbum Album selected in list view.
     * @return PhotoViewState.
     */
    public PhotoState openAlbum(Album selectedAlbum) {
        leave();

        // set the current album in the state class
        photoController.photoViewState.setCurrentAlbum(selectedAlbum);

        photoController.photoViewState.enter();
        return photoController.photoViewState;
    }
    
    /**
     * Method to process event of Button press. 
     * Implements open, add, rename, delete, search and logout button.
     * 
     * @return PhotoState representing the current state of the application.
     */
    public PhotoState processEvent() {
        Button b = (Button)lastEvent.getSource();

        // opening album
        if(b == photoController.nonadminController.openButton) {
            Album selectedAlbum = photoController.nonadminController.albumListView.getSelectionModel().getSelectedItem();

            if(selectedAlbum == null) {
                Popups.noSelectionError();
                return photoController.currentState;
            }

            return openAlbum(selectedAlbum);
        }

        // adding album
        else if(b == photoController.nonadminController.createButton) {
            String name = photoController.nonadminController.newAlbumName.getText();
            if(name.equals("")) {
                Popups.noValueError();
                return photoController.currentState;
            }
            
            // check for duplicate album names
            if(photoController.loginState.currentUser.hasAlbum(name)) {
                Popups.duplicateError();
                return photoController.currentState;
            }

            // add album to current user 
            Album newAlbum = new Album(name);
            photoController.loginState.currentUser.addAlbum(newAlbum);
            loadData(photoController.loginState.currentUser);

            photoController.nonadminController.newAlbumName.setText(""); // reset the value after creating
            return photoController.currentState;
        } 

        // renaming album
        else if(b == photoController.nonadminController.renameButton) {
            Album selectedAlbum = photoController.nonadminController.albumListView.getSelectionModel().getSelectedItem();
            if(selectedAlbum == null) {
                Popups.noSelectionError();
                return photoController.currentState;
            }

            String name = photoController.nonadminController.newAlbumName.getText();
            if(name.equals("")) {
                Popups.noValueError();
                return photoController.currentState;
            }
            
            if(photoController.loginState.currentUser.hasAlbum(name)) {
                Popups.duplicateError();
                return photoController.currentState;
            }

            selectedAlbum.setName(name);
            loadData(photoController.loginState.currentUser);

            photoController.nonadminController.newAlbumName.setText(""); // reset the value after creating
            return photoController.currentState;
        }
        // deleting album
        else if(b == photoController.nonadminController.deleteButton) {
            Album albumToRemove = photoController.nonadminController.albumListView.getSelectionModel().getSelectedItem();
            int pos = photoController.nonadminController.albumListView.getSelectionModel().getSelectedIndex();

            if(albums.size() == 0) {
                Popups.noSelectionError();
                return photoController.currentState;
            }

            photoController.loginState.currentUser.removeAlbum(albumToRemove);

            loadData(photoController.loginState.currentUser);
            
            // fixes an issue where listview would not select an album after removing the last album from the list
            if(pos == photoController.loginState.currentUser.getAlbums().size()) {
                photoController.nonadminController.albumListView.getSelectionModel().select(pos-1);
            }
            
            photoController.nonadminController.albumListView.getSelectionModel().select(pos);

            return photoController.currentState;
        }
        // searching for photos
        else if(b == photoController.nonadminController.searchButton) {
            leave();
            // search for the photos and add to this album
            Album searchedPhotos = new Album();

            // get the conditions
            // time conditions
            long timeLowerBound = 0;
            long timeUpperBound = Long.MAX_VALUE;

            // if the user chose a date for lower bound then change timeLowerBound
            String lowerBoundDay = photoController.nonadminController.startDayField.getText();
            String lowerBoundMonth = photoController.nonadminController.startMonthsCBox.getSelectionModel().getSelectedItem();
            String lowerBoundYear = photoController.nonadminController.startYearsField.getText();

            if(lowerBoundMonth != null && !lowerBoundYear.equals("")) {
                String lowerDate = lowerBoundMonth + " " + lowerBoundDay + ", " + lowerBoundYear;
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

                try {
                    Date temp = (Date)sdf.parse(lowerDate);
                    timeLowerBound = temp.getTime();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
            String upperBoundDay = photoController.nonadminController.endDayField.getText();
            String upperBoundMonth = photoController.nonadminController.endMonthsCBox.getSelectionModel().getSelectedItem();
            String upperBoundYear = photoController.nonadminController.endYearsField.getText();
            if(upperBoundMonth != null && !upperBoundYear.equals("")) {
                String upperDate = upperBoundMonth + " " + upperBoundDay + ", " + upperBoundYear;
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

                try {
                    Date temp = (Date)sdf.parse(upperDate);
                    timeUpperBound = temp.getTime();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            // tag conditions
            String[] tag1 = photoController.nonadminController.searchFirstTag.getText().split("=");
            String[] tag2 = photoController.nonadminController.searchSecondTag.getText().split("=");
            String op = photoController.nonadminController.searchCBox.getSelectionModel().getSelectedItem();

            List<Photo> foundPhotos = new ArrayList<Photo>();

            // if not searching for tags
            if(op == null && tag1.length != 2 && tag2.length != 2) {
                for(Album a: photoController.loginState.currentUser.getAlbums()) {
                    for(Photo p: a.getPhotos()) {
                        // check the conditions
                        // if the conditions match, then add to searchedPhotos
                        if(p.getTimeModified() >= timeLowerBound && p.getTimeModified() <= timeUpperBound) {
                            foundPhotos.add(p);
                        }
                    }
                }
            }
            // if searching for only one tag
            else if(op == null) {
                // there shouldnt be a second tag if there is no conjunction or disjunction
                if(tag2.length == 2) {
                    Popups.noConjunctionError();
                    return photoController.currentState;
                }

                for(Album a: photoController.loginState.currentUser.getAlbums()) {
                    for(Photo p: a.getPhotos()) {
                        // check the conditions
                        // if the conditions match, then add to searchedPhotos
                        if(p.getTimeModified() >= timeLowerBound && p.getTimeModified() <= timeUpperBound) {
                            if(p.hasTag(tag1[0], tag1[1])) {
                                foundPhotos.add(p);
                            }
                        }
                    }
                }
            }
            else {
                // both tags need to be there if there is a conjunction or disjunction
                if(tag1.length != 2 || tag2.length != 2) {
                    Popups.needInformationError();
                    return photoController.currentState;
                }

                for(Album a: photoController.loginState.currentUser.getAlbums()) {
                    for(Photo p: a.getPhotos()) {
                        // check the conditions
                        // if the conditions match, then add to searchedPhotos
                        if(p.getTimeModified() >= timeLowerBound && p.getTimeModified() <= timeUpperBound) {
                            if(op.equals("OR") && (p.hasTag(tag1[0], tag1[1]) || p.hasTag(tag2[0], tag2[1]))) {
                                foundPhotos.add(p);
                            } else if(op.equals("AND") && (p.hasTag(tag1[0], tag1[1]) && p.hasTag(tag2[0], tag2[1]))) {
                                foundPhotos.add(p);
                            }
                        }
                    }
                }
            }

            searchedPhotos.setPhotos(foundPhotos);
            
            // clear all fields
            photoController.nonadminController.searchFirstTag.clear();
            photoController.nonadminController.searchSecondTag.clear();
            photoController.nonadminController.startYearsField.clear();
            photoController.nonadminController.endYearsField.clear();
            photoController.nonadminController.startMonthsCBox.getSelectionModel().clearSelection();
            photoController.nonadminController.endMonthsCBox.getSelectionModel().clearSelection();
            photoController.nonadminController.searchCBox.getSelectionModel().clearSelection();
        
            String criteria = "Search criteria: ";
            
            // if searched with time criteria
            if(timeLowerBound != 0 || timeUpperBound != Long.MAX_VALUE) {
                Date lower = new Date(timeLowerBound);
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
                String lowerString = sdf.format(lower);
                String upperString = "";
                if(timeUpperBound != Long.MAX_VALUE) {
                    Date upper = new Date(timeUpperBound);
                    upperString = sdf.format(upper);
                }
                else {
                    upperString = "the end of time";
                }

                criteria += "Between " + lowerString + " to " + upperString;
            }
            else {
                criteria += "\n" + tag1[0] + "=" + tag1[1];
                if(tag2.length == 2) {
                    criteria += " " + op + " ";
                    criteria += tag2[0] + "=" + tag2[1];
                }
            } 

            searchedPhotos.setSearched(true);
            photoController.photoViewState.setCriteria(criteria);
            return openAlbum(searchedPhotos);
        }
        // logging out
        else {
            photoController.nonAdminState.leave();
            photoController.loginState.enter();
            return photoController.loginState;
        }
    }

    /**
     * Gets the current user's albums and sets it to album listview
     * @param user
     */
    private void loadData(User user) {
        // load the user's albums
        albums = FXCollections.observableArrayList(photoController.loginState.currentUser.getAlbums());
        photoController.nonadminController.albumListView.setItems(albums);
    }

    /**
     * Helper method to add the months of the year into a choicebox
     */
    private void loadMonths() {
        monthsObsList.add("Jan");
        monthsObsList.add("Feb");
        monthsObsList.add("Mar");
        monthsObsList.add("Apr");
        monthsObsList.add("May");
        monthsObsList.add("Jun");
        monthsObsList.add("Jul");
        monthsObsList.add("Aug");
        monthsObsList.add("Sep");
        monthsObsList.add("Oct");
        monthsObsList.add("Nov");
        monthsObsList.add("Dec");

        photoController.nonadminController.startMonthsCBox.setItems(monthsObsList);
        photoController.nonadminController.endMonthsCBox.setItems(monthsObsList);
    }
    
    /**
     * Method that sets the NonAdminState to non-visible.
     */
    public void leave() {
        photoController.nonadmin.setVisible(false);
    }

    /**
     * Method to return the singleton instance of NonAdminState.
     * 
     * @return Instance of NonAdminState.
     */
    public static NonAdminState getInstance() {
        if(instance == null) {
            instance = new NonAdminState();
        }

        return instance;
    }
}
