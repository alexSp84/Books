package eu.fse.books;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Book {

    private String title;

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

        } catch (JSONException e) {
            Log.e("Book", e.getMessage());
        }
    }

    public String getTitle() {
        return title;
    }
}
