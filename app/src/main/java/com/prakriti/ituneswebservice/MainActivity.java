package com.prakriti.ituneswebservice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.prakriti.ituneswebservice.model.ItunesData;

public class MainActivity extends AppCompatActivity {
// check URL not working??
    
    private Bitmap bitmap;
    private String coverUrl;

    private TextView txtTypeName, txtSongName, txtArtistName, txtAlbumName, txtGenreName;
    private ImageView imgCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTypeName = findViewById(R.id.txtTypeName);
        txtSongName = findViewById(R.id.txtSongName);
        txtArtistName = findViewById(R.id.txtArtistName);
        txtAlbumName = findViewById(R.id.txtAlbumName);
        txtGenreName = findViewById(R.id.txtGenreName);

        imgCover = findViewById(R.id.imgCover); // try loading with Picasso external library

        findViewById(R.id.btnGetInfo).setOnClickListener(v -> {
            GetItunesDataInBackground getData = new GetItunesDataInBackground(MainActivity.this);
            getData.execute();
        });

    }

    // inner class for Async Jobs -> bg thread
    private class GetItunesDataInBackground extends AsyncTask<String, Void, ItunesData> {
    // input - String url

        private Context context;
        private ProgressDialog progressDialog; // try with progress bar + progress percentage

        public GetItunesDataInBackground(Context context) {
            this.context = context;
            progressDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Fetching from Itunes...");
            progressDialog.show();
        }

        @Override
        protected ItunesData doInBackground(String... strings) {
            ItunesData itunesData = new ItunesData();
            ItunesHTTPClient itunesHTTPClient = new ItunesHTTPClient();
            try {
                String data = itunesHTTPClient.getItunesJsonData(); // returns string buffer

                itunesData = JsonItunesParser.getItunesData(data);
                coverUrl = itunesData.getArtworkUrl(); // string url
                bitmap = itunesHTTPClient.getBitmapFromUrl(coverUrl);
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
            return itunesData;
        }

        @Override
        protected void onPostExecute(ItunesData itunesData) {
            super.onPostExecute(itunesData);
            // set UI components
            txtSongName.setText(itunesData.getSongName());
            txtArtistName.setText(itunesData.getArtistName());
            txtAlbumName.setText(itunesData.getAlbumName());
            txtTypeName.setText(itunesData.getTypeName());
            txtGenreName.setText(itunesData.getGenreName());
            imgCover.setImageBitmap(bitmap);

            if(progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

}