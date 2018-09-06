package com.baraa.software.eventhorizon.roadtomindvalley.pinboard;

import android.graphics.Bitmap;

public class PinsViewModel {
    Bitmap image;
    String Category;
    String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
