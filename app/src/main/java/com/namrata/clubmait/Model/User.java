package com.namrata.clubmait.Model;

public class User {
    private String Name;
    private String Email;
    private String bio;
    private String imageurl;
    private String id;

    public User() {
    }

    public User(String Name, String Email, String bio, String imageurl, String id) {
        this.Name = Name;
        this.Email = Email;
        this.bio = bio;
        this.imageurl = imageurl;
        this.id = id;
    }

    public  String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
