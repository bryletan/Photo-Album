package view;

import model.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.scene.control.Button;
import javafx.scene.image.Image;

/**
 * Substate that is accessed from the PhotoViewState when opening a photo in the list view.
 * Class is used to implement the functionality of the SlideShowController.
 */
public class SlideShowState extends PhotoState {

    /**
     * Reference to the singleton instance of SlideShowState.
     */
    private static SlideShowState instance = null;

    /**
     * The current album.
     */
    private Album currentAlbum;

    /**
     * Sets the current index of opened photo in the album. Used when transition to next/prev in the slideshow.
     */
    private int index;

    /**
     * Empty constructor of SlideShowState.
     */
    private SlideShowState() {

    }

    /**
     * Empty enter method.
     */
    public void enter() {}

    /**
     * Accessed when opening a photo in the PhotoViewState. 
     * Sets the image to the image view and sets the SlideShowState to visible.
     * 
     * @param p Selected photo.
     * @throws FileNotFoundException
     */
    public void enter(Photo p) throws FileNotFoundException {
        setImage(p);
        photoController.slideshow.setVisible(true);
    }

    /**
     * Method to process event of Button press.
     * Implements next, prev, and back buttons.
     * 
     * @return PhotoState representing the current state of the application.
     */
    public PhotoState processEvent() {
        Button b = (Button)lastEvent.getSource();

        if(b == photoController.slideshowController.nextButton) {
            if(index != currentAlbum.getSize()-1) {
                index ++;
            }
            else {
                index = 0;
            }
            try {
                setImage(currentAlbum.getPhotos().get(index));
            }
            catch (Exception e) {

            }
            return photoController.currentState;
        }

        else if(b == photoController.slideshowController.prevButton) {
            if(index != 0) {
                index --;
            }
            else {
                index = currentAlbum.getSize()-1;
            }
            try {
                setImage(currentAlbum.getPhotos().get(index));
            }
            catch (Exception e) {

            }
            return photoController.currentState;
        }

        // go back to photo view
        else if(b == photoController.slideshowController.backButton) {
            leave();
            photoController.photoViewState.enter();
            return photoController.photoViewState;
        }

        // default
        return photoController.currentState;
    }

    /**
     * Method that sets the SlideShowState to non-visible.
     */
    public void leave() {
        photoController.slideshow.setVisible(false);
    }

    /**
     * Sets current index of image listview, used when entering SlideShowState
     * 
     * @param i int index.
     */
    public void setIndex(int i) {
        this.index = i;
    }

    /**
     * Sets a photo onto the ImageView found within SlideShowController
     * 
     * @param p Photo to be displayed.
     * @throws FileNotFoundException
     */
    public void setImage(Photo p) throws FileNotFoundException {
        InputStream stream = new FileInputStream(p.getPath());
        Image image = new Image(stream);
        
        photoController.slideshowController.imageView.setImage(image);
        centerImage();

        photoController.slideshowController.photoNameLabel.setText(p.toString());
        photoController.slideshowController.captionArea.setText(p.getCaption());
        photoController.slideshowController.tagArea.setText(p.getTags());
        photoController.slideshowController.dateArea.setText(p.getDate());
    }

    /**
     * Helper method to center image to the middle of the image view.
     * Called inside the setImage() method.
     */
    public void centerImage() {
        Image img = photoController.slideshowController.imageView.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = photoController.slideshowController.imageView.getFitWidth() / img.getWidth();
            double ratioY = photoController.slideshowController.imageView.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if(ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            photoController.slideshowController.imageView.setX((photoController.slideshowController.imageView.getFitWidth() - w) / 2);
            photoController.slideshowController.imageView.setY((photoController.slideshowController.imageView.getFitHeight() - h) / 2);

        }
    }

    /**
     * Sets the current album. 
     * 
     * @param album Album.
     */
    public void setCurrentAlbum(Album album) {
        this.currentAlbum = album;
    }

    /**
     * Method to return the singleton instance of SlideShowState.
     * 
     * @return Instance of SlideShowState.
     */
    public static SlideShowState getInstance() {
        if(instance == null) {
            instance = new SlideShowState();
        }

        return instance;
    }
}