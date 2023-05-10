package model;

import java.io.*;
import java.util.*;

/**
 * Object used to represent a user in the database.
 * 
 * @author Bryle Tan
 * @author Maanas Pimplikar
 */
public class User implements Serializable {

    /**
     * Username of the user.
     */
    private String userName;

    /**
     * List of type Albums belonging to the user.
     */
    private List<Album> albums;

    /**
     * List of String tag types that this user has used.
     */
    private List<String> tagTypes;

    /**
     * Constructor with a given username.
     * 
     * @param userName String username.
     */
    public User(String userName) {
        this.userName = userName;
        albums = new ArrayList<Album>();
        tagTypes = new ArrayList<String>();
    }

    /**
     * Getter for the album list.
     * 
     * @return List of type Album representing the user's albums.
     */
    public List<Album> getAlbums() {
        return albums;
    }

    /**
     * Getter to get an album with the passed name from this user.
     * 
     * @param albumName String name of the album.
     * @return Album object with the name <code>albumName</code>.
     */
    public Album getAlbum(String albumName) {
        for(Album a : this.albums) {
            if(a.getName().equals(albumName)) {
                return a;
            }
        }

        // should never be hit
        return null;
    }

    /**
     * Checks whether the user has an album with the passed name.
     * 
     * @param albumName String name of the album.
     * @return <code>true</code> if this user has an album with the name, <code>false</code> otherwise.
     */
    public boolean hasAlbum(String albumName) {
        for(Album a : this.albums) {
            if(a.getName().equals(albumName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Adds an album to this user's list of albums.
     * 
     * @param a Album to be added.
     */
    public void addAlbum(Album a) {
        albums.add(a);
    }

    /**
     * Removes an album from the list of albums.
     * 
     * @param a Album to be removed.
     */
    public void removeAlbum(Album a) {
        for(Photo p: a.getPhotos()) {
            p.removeAlbum(a);
        }
        albums.remove(a);
    }

    /**
     * Getter for this user's username.
     * 
     * @return String username.
     */
    public String getUserName() { 
        return this.userName; 
    }

    /**
     * Setter for this user's username.
     * 
     * @param newUserName String new username for this user.
     */
    public void setUserName(String newUserName) {
        this.userName = newUserName;
    }

    /**
     * Getter for the tag types belonging to this user.
     * 
     * @return List of type String containing the tag types.
     */
    public List<String> getTagTypes() {
        return this.tagTypes;
    }

    /**
     * Adds a tag type to the list of tag types.
     * 
     * @param tag String new tag type
     */
    public void addTagType(String tag) {
        tagTypes.add(tag);
    }

    /**
     * Removes a tag type from the user's list of tag types.
     * 
     * @param tag String tag type to be removed.
     */
    public void removeTagType(String tag) {
        tagTypes.remove(tag);
    }

    /**
     * Representing a user as a string.
     * 
     * @return String representation of a user.
     */
    public String toString() {
        return this.userName;
    }
}
