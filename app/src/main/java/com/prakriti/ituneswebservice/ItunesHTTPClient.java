package com.prakriti.ituneswebservice;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ItunesHTTPClient {

    private static String BASE_URL = "https://itunes.apple.com/search?term=michael+jackson";

    public String getItunesJsonData() throws IOException {

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) (new URL(BASE_URL)).openConnection(); // url.openConnection()
            httpURLConnection.setRequestMethod("GET"); // GET data from server
            httpURLConnection.setDoInput(true); // using URL Conn for input -> true
//            httpURLConnection.setDoOutput(true); -> posting output to url
            httpURLConnection.connect();

            // connect established, read the response
            StringBuffer stringBuffer = new StringBuffer();
            inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); // pass inputStream to inputStreamReader
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + "\n");
            }
            inputStream.close();
            httpURLConnection.disconnect();
            return stringBuffer.toString();
        }
        catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
        finally {
            inputStream.close();
            httpURLConnection.disconnect();
        }
    }

    // get image url
    public Bitmap getBitmapFromUrl(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            InputStream inputStream = url.openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

}
