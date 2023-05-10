package model;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Object representing an image.
 * 
 * @author Maanas Pimplikar
 * @author Bryle Tan
 */
public class Photo implements Serializable, Comparable<Photo> {
    
    /**
     * Name of the photo.
     */
    private String name;

    /**
     * HashMap storing the tags of the photo.
     */
    private HashMap<String, HashSet<String>> tags;

    /**
     * String caption of the photo.
     */
    private String caption;

    /**
     * String of the path of the photo.
     */
    private String path;

    /**
     * Long representing the last modified time of the file.
     */
    private long timeModified;

    /**
     * List of type Album representing the albums that this photo is a part of.
     */
    private List<Album> inAlbumList;

    /**
     * Default constructor for a Photo object.
     */
    public Photo() {

    }

    /**
     * Constructor with name and path of the photo.
     * 
     * @param path local device path to the image
     * @param name name of the photo
     */
    public Photo(String name, String path) {
        File file = new File(path);
        this.timeModified = file.lastModified();
        this.name = name;
        this.path = path;
        this.tags = new HashMap<String, HashSet<String>>();
        this.caption = "";
        this.inAlbumList = new ArrayList<Album>();
    }
    
    /**
     * Equals method for a photo object.
     * 
     * @param photo Photo that this photo is being compared to.
     * @return <code>true</code> if they are equal, <code>false</code> if otherwise.
     */
    public boolean equals(Photo photo) {
        return this.path.equals(photo.path);
    }

    /**
     * String representation of a photo.
     * 
     * @return String represenation of photo object.
     */
    public String toString() {
        return this.name;
    }

    /**
     * Checks whether the user has added any tags to this photo.
     * 
     * @return <code>true</code> if there are tags, <code>false</code>.
     */
    public boolean hasTags() {
        return this.tags.size() > 0;
    }

    /**
     * Checks whether this photo contains a tag with type <code>tagType</code> with a value <code>tagValue</code>.
     * 
     * @param tagType String type of the tag.
     * @param tagValue String value for the tag.
     * @return <code>true</code> if the photo tags contain the pair, <code>false</code> otherwise.
     */
    public boolean hasTag(String tagType, String tagValue) {
        if(this.tags.containsKey(tagType) && this.tags.get(tagType).contains(tagValue)) {
            return true;
        }

        return false;
    }

    /**
     * Setter method to set the caption of this photo.
     * 
     * @param newCaption String caption.
     */
    public void setCaption(String newCaption) {
        this.caption = newCaption;
    }

    /**
     * Adds a tag with type <code>tagType</code> and value <code>tagValue</code>.
     * 
     * @param tagType String type of the tag.
     * @param tagValue String value of the tag.
     */
    public void addTag(String tagType, String tagValue) {
        // if the type is already in the dictionary
        if(this.tags.containsKey(tagType)) {
            this.tags.get(tagType).add(tagValue);
        } 
        // if the type is not already in the dictionary
        else {
            HashSet<String> newSet = new HashSet<>();

            // initialize the set with the tag
            newSet.add(tagValue);

            this.tags.put(tagType, newSet);
        }
    }

    /**
     * Removes a tag with type <code>tagType</code> and value <code>tagValue</code>.
     * 
     * @param tagType String type of the tag.
     * @param tagValue String value of the tag.
     */
    public void removeTag(String tagType, String tagValue) {
        this.tags.get(tagType).remove(tagValue);
        if(this.tags.get(tagType).size() == 0) {
            this.tags.remove(tagType);
        }
    }

    /**
     * Adds an album to the list of albums.
     * 
     * @param a Album to be added.
     */
    public void addAlbum(Album a) {
        this.inAlbumList.add(a);
    }

    /**
     * Removes an album from the list of albums.
     * 
     * @param a Album to be removed.
     */
    public void removeAlbum(Album a) {
        this.inAlbumList.remove(a);
    }
    
    /**
     * Getter for the tags of this photo.
     * 
     * @return A single string containing all the tags. 
     */
    public String getTags() {
        StringBuilder tagBuilder = new StringBuilder();
        
        for(String key : this.tags.keySet()) {
            Set<String> vals = this.tags.get(key);
            tagBuilder.append(key + " : ");
            tagBuilder.append(String.join("\n" + key + ": ", vals));
            tagBuilder.append("\n");
        }

        return tagBuilder.toString();
    }

    /**
     * Getter for the albums that this photo is a part of.
     * 
     * @return A single string containing all albums.
     */
    public String getAlbums() {
        List<String> vals = new ArrayList<String>();
        for(Album a : this.inAlbumList) {
            vals.add(a.getName());
        }

        return String.join(", ", vals);
    }

    /**
     * Getter for the photo caption.
     * 
     * @return String caption.
     */
    public String getCaption() {
        return this.caption;
    }

    /**
     * Getter for the path of this photo.
     * 
     * @return String path.
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Getter for the last modified date of this photo, in the format "MMM dd, yyyy" 
     * 
     * @return String date.
     */
    public String getDate() {
        Date date = new Date(this.timeModified);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        return sdf.format(date);
    }

    /**
     * Getter for the last modified date of this photo, as a long.
     * 
     * @return long number, epoch time.
     */
    public long getTimeModified() {
        return this.timeModified;
    }

    /**
     * Getter method for the name of this photo.
     * 
     * @return String name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Compare method for a photo object.
     * 
     * @param p Photo that this photo is being compared to.
     * @return <code>1</code> if photo1 time is greater than photo2 time, 
     *         <code>-1</code> if photo1 time is less than photo2 time, 
     *         <code>0</code> if they are equal.
     */
    @Override
    public int compareTo(Photo p) {
        if(this.timeModified > p.timeModified) {
            return 1;
        }
        else if (this.timeModified < p.timeModified) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
