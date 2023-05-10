package view;

import app.Photos;
import model.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Substate that is entered from the NonAdminState when opening an album. 
 * Class is used to implement the functionality of the PhotoViewController.
 * 
 * @author Bryle Tan
 * @author Maanas Pimplikar
 */
public class PhotoViewState extends PhotoState {

    /**
     * List of stock photo accessed from the PhotoController.
     */
    public List<Photo> stockPhotos = new ArrayList<Photo>();

    /**
     * Reference to the singleton instance of PhotoViewState.
     */
    private static PhotoViewState instance = null;

    /**
     * List of tag types. Used when adding a tag type to the selected photo.
     */
    private ObservableList<String> tagTypeObsList = FXCollections.observableArrayList();
    
    /**
     * String representing the search criteria.
     */
    private String searchCriteria;

    /**
     * The current album.
     */
    private Album currentAlbum;

    /**
     * Empty constructor of PhotoViewState.
     */
    private PhotoViewState() {

    }

    /**
     * Accessed when opening an album from NonAdminState.
     * Method that sets the PhotoViewState to visisble. Sets up the current album's information.
     */
    public void enter() {
        photoController.photoviewController.title.setText(currentAlbum.getName());

        // if photoview is called by the search button, enable the "save to album" feature
        if(currentAlbum.isSearchedAlbum()) {
            photoController.photoviewController.searchInfo.setText(this.searchCriteria);
            photoController.photoviewController.createAlbumButton.setDisable(false);
        }

        // if the user is the stock user
        if(currentAlbum.getName().equals("stock")) {
            // if its first time launching app, make the stock photos
            if(stockPhotos.size() == 0) {
                Path currentRelativePath = Paths.get("");
                String crp = currentRelativePath.toAbsolutePath().toString();
                String p1 = crp+"/data/stock/Aurora Borealis.jpg";
                String p2 = crp+"/data/stock/Grand Canyon.jpeg";
                String p3 = crp+"/data/stock/Lake Moraine.jpg";
                String p4 = crp+"/data/stock/Machu Picchu.jpg";
                String p5 = crp+"/data/stock/The Colosseum.jpg";

                Photo photo1 = new Photo("Aurora Borealis", p1);
                photo1.setCaption("A natural light display in Earth's sky, predominantly seen in high-altitute regions. A result of disturbances in the magnetospehere caused by the solar wind.");
                photo1.addTag("Location", "Arctic Circle");
                photo1.addAlbum(currentAlbum);

                Photo photo2 = new Photo("Grand Canyon", p2);
                photo2.setCaption("A steep-sided canyon carved by the Colorado River in Arizona. The canyon is 277 miles long, up to 18 miles wide and attains a depth of over a mile!");
                photo2.addTag("Location", "Nevada, United States");
                photo2.addAlbum(currentAlbum);

                Photo photo3 = new Photo("Lake Moraine", p3);
                photo3.setCaption("A glaciallu fed lake in Alberta, Canada. Situated in the Valley of the Ten Peaks at an evolution of approx. 1,884 meters!");
                photo3.addTag("Location", "Alberta, Canada");
                photo3.addAlbum(currentAlbum);

                Photo photo4 = new Photo("Machu Picchu", p4);
                photo4.setCaption("A 15th-century Inca citadel located in southern Peru. Sits on a 2,430 meter mountain range!");
                photo4.addTag("Location", "Cusco Region, Peru");
                photo4.addAlbum(currentAlbum);

                Photo photo5 = new Photo("The Colosseum", p5);
                photo5.setCaption("Located in the centre of Rome, Italy. The largest ancient amphitheatre ever built despite its age!");
                photo5.addTag("Location", "Rome, Italy");
                photo5.addAlbum(currentAlbum);

                stockPhotos.add(photo1);
                stockPhotos.add(photo2);
                stockPhotos.add(photo3);
                stockPhotos.add(photo4);
                stockPhotos.add(photo5);

                currentAlbum.setPhotos(this.stockPhotos);
            }
        }

        // updates listView cell factory to use ImageView instead of toString() of Photo object only
        photoController.photoviewController.photoListView.setCellFactory(param -> new ListCell<Photo>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(Photo p, boolean empty) {
                super.updateItem(p, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    File file = new File(p.getPath());
                    Image image = new Image(file.toURI().toString());
                    imageView.setImage(image);
                    imageView.setFitWidth(50);
                    imageView.setPreserveRatio(true);
                    setText(p.toString());
                    setGraphic(imageView);
                }
            }
        });

        loadPhotos();
        loadTags();
        
        // loads ObservableList of tag types into the ComboBox
        photoController.photoviewController.tagsCBox.setItems(tagTypeObsList);
        photoController.photoviewController.tagsCBox.getSelectionModel().selectFirst();

        photoController.photoview.setVisible(true);
        photoController.photoviewController.captionArea.promptTextProperty().set("Select a photo...");
        photoController.photoviewController.tagArea.promptTextProperty().set("Select a photo...");

        // add event listener for click events
        photoController.photoviewController.photoListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Photo selectedPhoto = photoController.photoviewController.photoListView.getSelectionModel().getSelectedItem();
                if(selectedPhoto != null) {
                    updateLabels();

                    // double click
                    if(event.getClickCount() == 2) {
                        try {
                            photoController.photoviewController.openButton.fire();
                        }
                        catch (Exception e){
            
                        }
                    }
                }
            }
        });
    }

    /**
     * Method to process event of Button press. 
     * Implements add, edit, open, remove, copy, move to, back, and logout button.
     * 
     * @return PhotoState representing the current state of the application.
     */
    public PhotoState processEvent() {
        Button b = (Button)lastEvent.getSource();

        // editing caption
        if(b == photoController.photoviewController.captionEditButton) {
            Photo selectedPhoto = photoController.photoviewController.photoListView.getSelectionModel().getSelectedItem();

            if(selectedPhoto == null) {
                Popups.noSelectionError();
                return photoController.currentState;
            }

            photoController.captionEditState.setPhoto(selectedPhoto);
            photoController.captionEditState.enter();
            return photoController.captionEditState;
        }
        // adding a photo
        else if(b == photoController.photoviewController.selectButton) {
            // filechooser object
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Open photo");

            // added filters to only allow for specific files (he mentioned in FAQs)
            chooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.bmp", "*.gif", "*.jpg", "*.jpeg", "*.png"));

            File selectedFile = chooser.showOpenDialog(Photos.mainStage);

            if(selectedFile == null) {
                Popups.noSelectionError();
                return photoController.currentState;
            }
            
            try {
                String photoLocation = selectedFile.getAbsolutePath();
                String fileName = selectedFile.getName();
                String photoName = fileName.substring(0, fileName.lastIndexOf('.'));
                Photo newPhoto = new Photo(photoName, photoLocation);

                if(currentAlbum.hasPhoto(newPhoto)) {
                    Popups.duplicateError();
                    return photoController.currentState;
                }

                currentAlbum.addPhoto(newPhoto);
                loadPhotos();
            } catch (Exception e) {
                e.printStackTrace();
            }

            photoController.photoviewController.photoListView.getSelectionModel().select(currentAlbum.getSize() - 1);

        }
        // opening a photo
        else if(b == photoController.photoviewController.openButton) {
            Photo selectedPhoto = photoController.photoviewController.photoListView.getSelectionModel().getSelectedItem();

            if(selectedPhoto == null) {
                Popups.noSelectionError();
                return photoController.currentState;
            }

            File file = new File(selectedPhoto.getPath());
            if(!file.exists()) {
                Popups.fileNotFoundError();
                return photoController.currentState;
            }

            // open the slideshow state
            try {
                leave();
                photoController.slideShowState.setIndex(photoController.photoviewController.photoListView.getSelectionModel().getSelectedIndex());
                photoController.slideShowState.setCurrentAlbum(currentAlbum);
                photoController.slideShowState.enter(selectedPhoto);
                return photoController.slideShowState;
            }
            catch (Exception e){

            }
            return photoController.currentState;
        } 
        // going back to the nonadmin subsystem
        else if(b == photoController.photoviewController.backButton) {
            leave();
            photoController.nonAdminState.enter();
            return photoController.nonAdminState;
        }
        // removing a photo
        else if(b == photoController.photoviewController.removeButton) {
            Photo photoToRemove = photoController.photoviewController.photoListView.getSelectionModel().getSelectedItem();
            int pos = photoController.photoviewController.photoListView.getSelectionModel().getSelectedIndex();

            if(photoToRemove == null) {
                Popups.noSelectionError();
                return photoController.currentState;
            }

            currentAlbum.removePhoto(photoToRemove);
            loadPhotos();

            photoController.photoviewController.photoListView.getSelectionModel().select(pos);

        } 
        // adding a tag type
        else if(b == photoController.photoviewController.addTagTypeButton) {
            String tagToAdd = photoController.photoviewController.tagTypeField.getText();

            if(tagTypeObsList.contains(tagToAdd)) {
                Popups.duplicateError();
                photoController.photoviewController.tagTypeField.clear();
                return photoController.currentState;
            }

            if(tagToAdd.equals("")) {
                Popups.noValueError();
                return photoController.currentState;
            }

            // add to obslist and user tag types list
            tagTypeObsList.add(tagToAdd);
            photoController.loginState.currentUser.addTagType(tagToAdd);
            photoController.photoviewController.tagTypeField.clear();
        }
        // deleting a tag type
        else if(b == photoController.photoviewController.delTagTypeButton) {
            String tagToDel = photoController.photoviewController.tagTypeField.getText();

            if(tagToDel.equals("")) {
                Popups.noValueError();
                return photoController.currentState;
            }

            if(!tagTypeObsList.contains(tagToDel)) {
                Popups.noTagTypeError();
                return photoController.currentState;
            }

            // delete tag type from the tag type obslist
            tagTypeObsList.remove(tagToDel);
            photoController.loginState.currentUser.removeTagType(tagToDel);
            photoController.photoviewController.tagTypeField.clear();
        }
        // adding a tag
        else if(b == photoController.photoviewController.addTagButton) {
            Photo selectedPhoto = photoController.photoviewController.photoListView.getSelectionModel().getSelectedItem();

            if(selectedPhoto == null) {
                Popups.noSelectionError();
                return photoController.currentState;
            }

            String tagType = photoController.photoviewController.tagsCBox.getSelectionModel().getSelectedItem();

            if(tagType == null) {
                Popups.noValueError();
                return photoController.currentState;
            }

            String tagValue = photoController.photoviewController.tagValueField.getText();

            if(tagValue.equals("")) {
                Popups.noValueError();
                return photoController.currentState;
            }

            if(selectedPhoto.hasTag(tagType, tagValue)) {
                Popups.duplicateError();
                photoController.photoviewController.tagValueField.setText("");
                return photoController.currentState;
            }

            // if all checks are passed, add the tag
            selectedPhoto.addTag(tagType, tagValue);
            updateLabels();
            // clear the field after
            photoController.photoviewController.tagValueField.clear();
        }
        // deleting a tag
        else if(b == photoController.photoviewController.delTagButton) {
            Photo selectedPhoto = photoController.photoviewController.photoListView.getSelectionModel().getSelectedItem();

            if(selectedPhoto == null) {
                Popups.noSelectionError();
                return photoController.currentState;
            }

            String tagType = photoController.photoviewController.tagsCBox.getSelectionModel().getSelectedItem();

            if(tagType == null) {
                Popups.noValueError();
                return photoController.currentState;
            }

            String tagValue = photoController.photoviewController.tagValueField.getText();

            if(tagValue.equals("")) {
                Popups.noValueError();
                return photoController.currentState;
            }

            // if all checks are passed, remove the tag
            if(selectedPhoto.hasTag(tagType, tagValue)) {
                selectedPhoto.removeTag(tagType, tagValue);
            }
            else {
                Popups.noTagError();
                return photoController.currentState;
            }

            // clear the field after
            updateLabels();
            photoController.photoviewController.tagValueField.clear();
            
        }
        // logout
        else if(b == photoController.photoviewController.logoutButton) {
            leave();
            photoController.loginState.enter();
            return photoController.loginState;
        }
        // copying photos/creating new album out of searched photos
        else if(b == photoController.photoviewController.copyButton) {
            Photo toBeCopied = photoController.photoviewController.photoListView.getSelectionModel().getSelectedItem();

            if(toBeCopied == null) {
                Popups.noSelectionError();
                return photoController.currentState;
            }
            
            String destinationAlbum = Popups.copySinglePhoto();

            // if destinationAlbum is empty or is not present in the user's albums, then create a new album
            if(!photoController.loginState.currentUser.hasAlbum(destinationAlbum) || destinationAlbum.equals("")) {
                Popups.needInformationError();
                return photoController.currentState;
            } else {
                Album destination = photoController.loginState.currentUser.getAlbum(destinationAlbum);

                if(destination.hasPhoto(toBeCopied)) {
                    Popups.duplicateError();
                    return photoController.currentState;
                }

                destination.addPhoto(toBeCopied);
            }

            // should not leave photoview
            return photoController.currentState;
        }
        // if the searched photos are being created into their own album
        else if(b == photoController.photoviewController.createAlbumButton) {
            String newAlbumName = Popups.createAlbumFromSearch();

            if(newAlbumName.equals("")) {
                Popups.noValueError();
                return photoController.currentState;
            }

            if(photoController.loginState.currentUser.hasAlbum(newAlbumName)) {
                Popups.duplicateError();
                return photoController.currentState;
            }

            currentAlbum.setName(newAlbumName);

            for(Photo p: currentAlbum.getPhotos()) {
                p.addAlbum(currentAlbum);
            }

            photoController.loginState.currentUser.addAlbum(currentAlbum);

            // go back to nonadmin subsystem
            leave();
            currentAlbum.setSearched(false);
            photoController.nonAdminState.enter();
            return photoController.nonAdminState;
        }
        // if the photo is being moved
        else if(b == photoController.photoviewController.moveButton) {
            Photo toBeMoved = photoController.photoviewController.photoListView.getSelectionModel().getSelectedItem();

            if(toBeMoved == null) {
                Popups.noSelectionError();
                return photoController.currentState;
            }

            String destination = Popups.movePhoto();

            if(!photoController.loginState.currentUser.hasAlbum(destination) || destination.equals("")) {
                Popups.noValueError();
                return photoController.currentState;
            }

            Album destinationAlbum = photoController.loginState.currentUser.getAlbum(destination);

            if(destinationAlbum.hasPhoto(toBeMoved)) {
                Popups.duplicateError();
                return photoController.currentState;
            }

            destinationAlbum.addPhoto(toBeMoved);

            photoController.photoviewController.removeButton.fire();
            return photoController.currentState;
        }

        // default
        return photoController.currentState;
    }

    /**
     * Helper method to set the photo list view to the current album's photos.
     */
    public void loadPhotos() {
        photoController.photoviewController.photoListView.setItems(currentAlbum.getObservablePhotos());
    }

    /**
     * Helper method to load the current tags and tag types of the current user.
     */
    public void loadTags() {
        User currentUser = photoController.loginState.currentUser;

        for(String tag : currentUser.getTagTypes()) {
            tagTypeObsList.add(tag);
        }

        // adding default tags if the user does not already have them saved
        if(!tagTypeObsList.contains("Location")) {
            tagTypeObsList.add("Location");
        }

        if(!tagTypeObsList.contains("Person")) {
            tagTypeObsList.add("Person");
        }
    }

    /**
     * Method that sets the PhotoViewState to non-visible.
     */
    public void leave() {
        //currentAlbum = null;
        tagTypeObsList.clear();

        // clear all information areas
        photoController.photoviewController.photoName.setText("");
        photoController.photoviewController.photoDate.setText("");
        photoController.photoviewController.photoAlbumList.setText("");
        photoController.photoviewController.searchInfo.setText("");
        photoController.photoviewController.captionArea.clear();
        photoController.photoviewController.tagValueField.clear();
        photoController.photoviewController.tagArea.clear();

        photoController.photoviewController.createAlbumButton.setDisable(true);
        photoController.photoview.setVisible(false);
    }

    /**
     * Sets current album.
     * 
     * @param album Album.
     */
    public void setCurrentAlbum(Album album) {
        this.currentAlbum = album;
    }

    /**
     * Sets search criteria
     * 
     * @param s Criteria
     */
    public void setCriteria(String s) {
        this.searchCriteria = s;
    }

    /**
     * Helper method to update the labels of the current selected photo in the list view.
     */
    public void updateLabels() {
        // clear the pre-existing labels
        photoController.photoviewController.photoName.setText("");
        photoController.photoviewController.photoDate.setText("");
        photoController.photoviewController.photoAlbumList.setText("");
        photoController.photoviewController.captionArea.clear();
        photoController.photoviewController.tagValueField.clear();
        photoController.photoviewController.tagArea.clear();
        
        Photo selectedPhoto = photoController.photoviewController.photoListView.getSelectionModel().getSelectedItem();

        if(selectedPhoto != null) {
            photoController.photoviewController.photoName.setText(selectedPhoto.toString());
            photoController.photoviewController.photoDate.setText(selectedPhoto.getDate());
            photoController.photoviewController.photoAlbumList.setText("In albums: " + selectedPhoto.getAlbums());

            // change the caption
            if(!selectedPhoto.getCaption().equals("")) {
                photoController.photoviewController.captionArea.setText(selectedPhoto.getCaption());                
            } else {
                photoController.photoviewController.captionArea.promptTextProperty().set("Click on \"Edit\" to enter a caption...");
            }
            
            if(selectedPhoto.hasTags()) {
                photoController.photoviewController.tagArea.setText(selectedPhoto.getTags());
            } else {
                photoController.photoviewController.tagArea.promptTextProperty().set("Enter tags...");
            }
        }
    }

    /**
     * Method to return the singleton instance of PhotoViewState.
     * 
     * @return Instance of PhotoViewState.
     */
    public static PhotoViewState getInstance() {
        if(instance == null) {
            instance = new PhotoViewState();
        }

        return instance;
    }
}
