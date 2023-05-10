package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.*;

/**
 * Album object used to store the photos.
 * 
 * @author Maanas Pimplikar
 * @author Bryle Tan
 */
public class Album implements Serializable {
    
    /**
     * Name of the album.
     */
    private String name;

    /**
     * Size of the album.
     */
    private int size;

    /**
     * List of Photos.
     */
    private List<Photo> albumPhotos;

    /**
     * Boolean to denote whether this was a searched album.
     */
    private boolean isSearched;

    /**
     * Default class constructor
     */
    public Album() {
        this.name = "new Album";
        this.size = 0;
        albumPhotos = new ArrayList<Photo>();
        this.isSearched = false;
    }

    /**
     * Album object constructor with a name.
     * 
     * @param name Name of the album.
     */
    public Album(String name) {
        this.name = name;
        this.size = 0;
        albumPhotos = new ArrayList<Photo>();
        this.isSearched = false;
    }

    /**
     * Adds a photo to the list of photos.
     * 
     * @param newPhoto Photo object for the photo that is being added.
     */
    public void addPhoto(Photo newPhoto) {
        newPhoto.addAlbum(this);
        albumPhotos.add(newPhoto);
        this.size++;
    }
    
    /**
     * Removes a photo from the list of photos.
     * 
     * @param photo  Photo object for the photo that is being removed.
     */
    public void removePhoto(Photo photo) {
        this.albumPhotos.remove(photo);
        photo.removeAlbum(this);
        this.size--;
    }

    /**
     * Sets the name of the album.
     * 
     * @param name String for the new name of the album.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the photos list to a given list.
     * 
     * @param newPhotos List of type Photo.
     */
    public void setPhotos(List<Photo> newPhotos) {
        this.albumPhotos = newPhotos;
        this.size = newPhotos.size();
    }

    /**
     * Checks if the album contains a photo.
     * 
     * @param newPhoto Photo object that is being checked for.
     * @return <code>true</code> if the photo exists in the album, <code>false</code> otherwise.
     */
    public boolean hasPhoto(Photo newPhoto) {
        for(Photo p: this.albumPhotos) {
            if(p.getName().equals(newPhoto.getName())){
                return true;
            }
        }

        return false;
    }

    /**
     * Setter for the isSearched attribute.
     * 
     * @param bool boolean denoting whether the album was searched for.
     */
    public void setSearched(boolean bool) {
        this.isSearched = bool;
    }

    /**
     * Getter method to check if the album was searched for.
     * 
     * @return <code>true</code> if the album is being searched for, <code>false</code> if otherwise.
     */
    public boolean isSearchedAlbum() {
        return this.isSearched;
    }

    /**
     * Gets the name of the album.
     * 
     * @return name of the album as a String.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the size of the album.
     * 
     * @return size of the album as an integer.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Gets the range of the dates of photos in the album.
     * 
     * @return String representation of the range.
     */
    public String getDate() {
        // error checks
        if(albumPhotos.size() == 0) {
            return "";
        }
        if(albumPhotos.size() == 1) {
            return albumPhotos.get(0).getDate();
        }   

        // get the range of dates that the album will display in the non-admin user subsystem
        Photo earliestPhoto = albumPhotos.get(0);
        Photo latestPhoto = albumPhotos.get(0);
        
        for(Photo p : this.albumPhotos) {
            if(p.compareTo(earliestPhoto) < 0) {
                earliestPhoto = p;
            }
            
            if (p.compareTo(latestPhoto) > 0) {
                latestPhoto = p;
            }
        }
        String earliestDate = earliestPhoto.getDate();
        String latestDate = latestPhoto.getDate();

        return earliestDate + " - " + latestDate;
    }

    /**
     * Gets the list of all photos in the album.
     * 
     * @return List of type Photo.
     */
    public List<Photo> getPhotos() {
        return this.albumPhotos;
    }

    /**
     * Gets the list of all photos in the album as an observable list.
     * 
     * @return ObservableList of type Photo.
     */
    public ObservableList<Photo> getObservablePhotos() {
        return FXCollections.observableArrayList(this.albumPhotos);
    }
    
    /**
     * Checks if the album is equal to another.
     * 
     * @param other Album being compared to.
     * @return <code>true</code> if the albums are equal, <code>false</code> if otherwise.
     */
    public boolean equals(Album other) {
        return this.name.equals(other.getName());
    }

    /**
     * Returns a string representation of the album.
     * 
     * @return String representation of the album.
     */
    public String toString() {
        return this.name;
    }
}
