package com.example.android.finalproject.network;

import android.net.Uri;
import android.util.Log;

import com.example.android.finalproject.model.ShopItems;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class APIFetcher {
    public static final String CONSUMER_KEY = "ck_109ac9e7df200e7cc58194b85008f65cc67d08db";
    public static final String CONSUMER_SECRET = "cs_542f042e91f6f9c4c7cbcff7b459cc17180e86e6";
    private static final String TAG = "ShopItemsFetcher";

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream inputStream = httpURLConnection.getInputStream();

            if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(httpURLConnection.getResponseMessage() +
                        " with: " +urlSpec);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int readSize = 0;

            while ((readSize = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, readSize);
            }

            outputStream.close();
            return outputStream.toByteArray();

        } catch (MalformedURLException e) {
            Log.e(TAG, "malformed url: ", e);
        } finally {
            httpURLConnection.disconnect();
        }
        return null;
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<ShopItems> fetchItems() throws IOException {
        String url = Uri.parse("https://woocommerce.maktabsharif.ir/wp-json/wc/v3/products?")
                .buildUpon()
                .appendQueryParameter("consumer_key", CONSUMER_KEY)
                .appendQueryParameter("consumer_secret", CONSUMER_SECRET)
                .build().toString();

        String result = getUrlString(url);
        Log.d(TAG, "fetched: " + result);

        List<ShopItems> shopItems = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(result);
            ShopItems[] arrayShop = new Gson()
                    .fromJson(jsonArray.toString(), ShopItems[].class);
            shopItems = Arrays.asList(arrayShop);

        } catch (JSONException e) {
            Log.e(TAG, "fail to parse json ", e);
        }

        return shopItems;
    }
}
