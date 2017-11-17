package com.example.vaksys_android.lovepiceditor.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vaksys-42 on 1/8/17.
 */

public class ApiResponse extends CommonResponse
{
    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("link")
    private String link;

    @SerializedName("image")
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
