package com.prakriti.ituneswebservice;

import com.prakriti.ituneswebservice.model.ItunesData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonItunesParser {
// returns value of passed key in passed json object
    // tagName refers to the key in key-value pair

    public static ItunesData getItunesData(String url) throws JSONException {
        // url to access itunes data online
        // throws exception in case of url issues, etc

        ItunesData itunesData = new ItunesData();
        JSONObject jsonObject = new JSONObject(url);

        // json object is structured as -> json object contains json array of result json objects
        JSONArray resultsArray = jsonObject.getJSONArray("results"); // accessing array with key "results"
        JSONObject artistObject = resultsArray.getJSONObject(0); // first object inside array

        itunesData.setTypeName(artistObject.getString("wrapperType"));
        itunesData.setArtistName(artistObject.getString("artistName"));
        itunesData.setSongName(artistObject.getString("trackName"));
        itunesData.setAlbumName(artistObject.getString("collectionName"));
        itunesData.setGenreName(artistObject.getString("primaryGenreName"));
        itunesData.setArtworkUrl(artistObject.getString("artworkUrl100"));

        return itunesData;
    }

}
