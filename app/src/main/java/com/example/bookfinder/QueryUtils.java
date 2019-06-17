package com.example.bookfinder;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public final class QueryUtils {

    public static final String LOG_TAG = MainActivity.class.getName();

    private QueryUtils() {
    }

    /**
     * Return a list of {@link Book} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Book> extractBooks(String bookJSON) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }

        // Empty ArrayList to start adding Books to
        ArrayList<Book> books = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONObject baseJsonResponse = new JSONObject(bookJSON);
            JSONArray itemsArray = baseJsonResponse.getJSONArray("items");
            String price;

            // Loop through each book
            for(int i = 0;i < itemsArray.length(); i++) {
                JSONObject currentBook = itemsArray.getJSONObject(i);
                JSONObject volumeinfo = currentBook.getJSONObject("volumeInfo");
                JSONObject saleinfo = currentBook.getJSONObject("saleInfo");

                if(saleinfo.getString("saleability").equals("FOR_SALE")) {
                    JSONObject listPrice  = saleinfo.getJSONObject("listPrice");
                    price = listPrice.getString("amount");
                    price = "₹" + price;
                }
                else{
                    price = "NO INFO";
                }

                String title = volumeinfo.getString("title");
                String authors = "";
                JSONArray authorArray = volumeinfo.getJSONArray("authors");
                for(int j = 0   ;j < authorArray.length();j++) {
                    authors += authorArray.getString(j);
                    if(j != authorArray.length()-1) {
                        authors += ", ";
                    }
                }
                JSONObject imageLinks = volumeinfo.getJSONObject("imageLinks");
                String link = imageLinks.getString("smallThumbnail");
                Log.d("EEEEE", link);
                Book book = new Book(title, authors, price, link);
                books.add(book);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing JSON results", e);
        }

        // Return the list of earthquakes
        return books;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch(MalformedURLException e) {
            Log.e("P", "Problem building the code", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest (URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if(url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("P", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("P", "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Query the USGS dataset and return a list of Book objects.
     */
    public static List<Book> fetchBookData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("P", "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<Book> books = extractBooks(jsonResponse);

        // Return the list of {@link Earthquake}s
        return books;
    }




    public class JSONTask extends AsyncTask<URL, String, String>{

        @Override
        protected String doInBackground(URL... urls) {
            return null;
        }
    }























}
