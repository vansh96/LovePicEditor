package com.example.vaksys_android.lovepiceditor.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vaksys-42 on 22/7/17.
 */

public class Appresponse extends CommonResponse
{
    @SerializedName("row")
    public List<Category> row;

    public List<Category> getRow()
    {
        return row;
    }

    public void setRow(List<Category> row) {
        this.row = row;
    }

    public class Category
    {
        @SerializedName("id")
        private int id;

        @SerializedName("topic")
        private String topic;

        @SerializedName("description")
        private String description;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
