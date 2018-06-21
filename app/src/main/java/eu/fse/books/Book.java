package eu.fse.books;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Book {

    private String id, title, thumbnail, publisher, description, language, subtitle, pages;

    private ArrayList<String> authors = new ArrayList<>();

    public Book(String id) {
        this.id = id;
    }

    public static ArrayList<Book> getBooksList(JSONArray books) {

        ArrayList<Book> list = new ArrayList<>();

        for (int i = 0; i < books.length(); i++) {

            try {
                JSONObject jsonBook = books.getJSONObject(i);
                list.add(new Book(jsonBook));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public Book(JSONObject jsonBook) throws JSONException {

        id = jsonBook.getString("id");
        JSONObject volumeInfo = jsonBook.getJSONObject("volumeInfo");

        title = volumeInfo.optString("title", "No Title");

        JSONArray jsonAuthors = volumeInfo.optJSONArray("authors");
        if (jsonAuthors != null) {
            for (int i = 0; i < jsonAuthors.length(); i++)
                authors.add(jsonAuthors.getString(i));
        } else authors.add("");

        thumbnail = volumeInfo.getJSONObject("imageLinks").optString("thumbnail", "No Image");

        publisher = volumeInfo.optString("publisher", "");

        description = volumeInfo.optString("description", "No Description");

        language = volumeInfo.optString("language", "");

        subtitle = volumeInfo.optString("subtitle", "");

        pages = volumeInfo.optString("pageCount", "");

    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public String getId() {
        return id;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getPages() {
        return pages;
    }
}
