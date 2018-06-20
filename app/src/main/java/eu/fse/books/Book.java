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

    public Book(JSONObject jsonBook) {
        try {

            id = jsonBook.getString("id");

            title = jsonBook.getJSONObject("volumeInfo").getString("title");

            JSONArray jsonAuthors = jsonBook.getJSONObject("volumeInfo").getJSONArray("authors");
            for (int i = 0; i < jsonAuthors.length(); i++)
                authors.add(jsonAuthors.getString(i));


            thumbnail = jsonBook.getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("thumbnail");

            publisher = jsonBook.getJSONObject("volumeInfo").getString("publisher");

            description = jsonBook.getJSONObject("volumeInfo").getString("description");

            language = jsonBook.getJSONObject("volumeInfo").getString("language");

            subtitle = jsonBook.getJSONObject("volumeInfo").getString("subtitle");

            pages = jsonBook.getJSONObject("volumeInfo").getString("pageCount");


        } catch (JSONException e) {
            Log.e("Book", e.getMessage());
        }
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
