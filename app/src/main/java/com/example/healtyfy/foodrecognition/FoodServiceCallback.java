package com.example.healtyfy.foodrecognition;

import org.json.JSONObject;


public interface FoodServiceCallback<T> {

    /**
     * Indicates that the upload operation has finished. This method is called even if the
     * upload hasn't completed successfully.
     */
    void finishRecognition(JSONObject response, FoodRecognitionException exception);
}
