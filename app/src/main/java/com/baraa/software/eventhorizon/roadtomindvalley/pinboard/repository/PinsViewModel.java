package com.baraa.software.eventhorizon.roadtomindvalley.pinboard.repository;

import android.graphics.Bitmap;

public class PinsViewModel {
    Bitmap image;
    String Category;

    public PinsViewModel(Bitmap image, String category) {
        this.image = image;
        Category = category;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
