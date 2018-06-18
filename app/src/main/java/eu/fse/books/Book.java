package eu.fse.books;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Book {

    private String title;
    private String thumbnail;
    ArrayList<String> authors = new ArrayList<>();

    public Book(String title) {
        this.title = title;
    }

    public static ArrayList<Book> getBooksList(JSONArray books){

        ArrayList<Book> list = new ArrayList<>();

        for (int i = 0; i < books.length(); i++){

            try {
                JSONObject jsonBook = books.getJSONObject(i);
                list.add(new Book(jsonBook));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public Book(JSONObject jsonBook){
        try {
            title = jsonBook.getJSONObject("volumeInfo").getString("title");

            JSONArray jsonAuthors = jsonBook.getJSONObject("volumeInfo").getJSONArray("authors");
            for(int i = 0; i<jsonAuthors.length(); i++)
                authors.add(jsonAuthors.getString(i));

            thumbnail = jsonBook.getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("thumbnail");


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

}
