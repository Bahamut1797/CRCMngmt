package com.bohemiamates.crcmngmt.Models;

public class Badge {
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Badge{" +
                "image='" + image + '\'' +
                '}';
    }
}
