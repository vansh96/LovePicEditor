package com.example.vaksys_android.lovepiceditor.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vaksys-42 on 22/7/17.
 */

public class CommonResponse
{
    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;
    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
