package com.distributing.data.utilities;

import android.net.Uri;
import android.util.Log;

import com.distributing.data.models.GoogleBookModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    private static final String BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
    private static final String QUERY_PARAM = "q";
    private static final String MAX_RESULTS = "maxResults";
    private static final String PRINT_TYPE = "printType";

    public static ArrayList<GoogleBookModel> getBookInfo(String queryString) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String jsonResponse = null;

        Log.d("API", queryString);

        ArrayList<GoogleBookModel> bookResults = new ArrayList<GoogleBookModel>();

        try {
            Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();
            URL requestURL = new URL(builtURI.toString());

            connection = (HttpURLConnection) requestURL.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            // Get the InputStream and Create a buffered reader from that input stream
            InputStream inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }

            Log.d("Resp", builder.toString());

            if (builder.length() == 0) {
                return null;
            }

            jsonResponse = builder.toString();
            try {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONArray itemsArray = jsonObject.getJSONArray("items");

                // Initialize variables
                int i = 0;
                String title = null;
                String authors = null;
                String description = null;
                String publisher = null;
                String publishedDate = null;
                String categories = null;

                // Iterate through all the items and add them to the
                while (i < itemsArray.length()) {
                    JSONObject book = itemsArray.getJSONObject(i);
                    JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                    try {
                        title = volumeInfo.getString("title");
                        try {authors = volumeInfo.get("authors").toString();}
                        catch (Exception e) { authors = " "; }
                        try { description = volumeInfo.getString("description"); }
                        catch (Exception e) { description = " "; }
                        try { publisher = volumeInfo.getString("publisher"); }
                        catch (Exception e) { publisher = " "; }
                        try { publishedDate = volumeInfo.getString("publishedDate"); }
                        catch (Exception e) { publishedDate = " "; }
                        try { categories = volumeInfo.get("categories").toString(); }
                        catch (Exception e) { categories = " "; }


                        GoogleBookModel parsedBook = new GoogleBookModel(title, authors, description, publisher, publishedDate, categories);
                        bookResults.add(parsedBook);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    i++;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bookResults;
    }
}
