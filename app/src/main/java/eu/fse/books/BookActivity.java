package eu.fse.books;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    private TextView titleTxtView, authorsTxtView, publisherTxtView, descriptionTxtView;
    private TextView authorsBio, titleBio, languageBio, subtitleBio, publisherBio, pageBio;
    private ImageView cover;

    Intent receivedBook;
    String idBook;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        titleTxtView = (TextView) findViewById(R.id.title_txt_view);
        cover = (ImageView) findViewById(R.id.cover_img_view);
        authorsTxtView = (TextView) findViewById(R.id.authors_txt_view);
        publisherTxtView = (TextView) findViewById(R.id.publisher_txt_view);
        descriptionTxtView = (TextView) findViewById(R.id.description_txt_view);
        authorsBio = (TextView) findViewById(R.id.authors_bio);
        titleBio = (TextView) findViewById(R.id.title_bio);
        languageBio = (TextView) findViewById(R.id.language_bio);
        subtitleBio = (TextView) findViewById(R.id.subtitle_bio);
        publisherBio = (TextView) findViewById(R.id.edition_bio);
        pageBio = (TextView) findViewById(R.id.pages_bio);


        receivedBook = getIntent();
        idBook = receivedBook.getStringExtra("idbook");

        getBookInfoFromURL(idBook);

        authorsBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent authorSearch = new Intent(v.getContext(),MainActivity.class);
                authorSearch.putExtra("author", authorsBio.getText().toString());
                startActivity(authorSearch);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(queue != null)
            queue.cancelAll(MainActivity.TAG);
    }

    private void bindBook(Book book) {
        titleTxtView.setText(book.getTitle());

        Glide.with(cover.getContext())
                .load(book.getThumbnail())
                .into(cover);

        String authorsList = "";
        int sizeAuthorsList = book.getAuthors().size();

        for (int i = 0; i < sizeAuthorsList; i++) {
            if(i > 0) authorsList+=", ";
            authorsList += book.getAuthors().get(i);
        }

        authorsTxtView.setText(authorsList);

        publisherTxtView.setText(book.getPublisher());

        descriptionTxtView.setText(book.getDescription());

        authorsBio.setText(authorsList);

        titleBio.setText(book.getTitle());

        subtitleBio.setText(book.getSubtitle());

        publisherBio.setText(book.getPublisher());

        pageBio.setText(book.getPages());

        languageBio.setText(book.getLanguage());
    }

    private void getBookInfoFromURL(String id) {
        // Make HTTP call

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(this);
        String url = null;

        try {
            findViewById(R.id.loading).setVisibility(View.VISIBLE);
            url = "https://www.googleapis.com/books/v1/volumes/" + URLEncoder.encode(id, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        findViewById(R.id.loading).setVisibility(View.GONE);
                        findViewById(R.id.all_view).setVisibility(View.VISIBLE);
                        Log.d("jsonRequest", response.toString());

                        try {
                            bindBook(new Book(response));
                        } catch (JSONException e) {
                            Toast.makeText(BookActivity.this, "Si è verificato un errore", Toast.LENGTH_LONG).show();
                            finish();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                findViewById(R.id.loading).setVisibility(View.GONE);
                Toast.makeText(BookActivity.this, "Si è verificato un errore: " + error.networkResponse.statusCode, Toast.LENGTH_LONG).show();
            }
        });

        // Set the tag on the request.
        jsonRequest.setTag(MainActivity.TAG);
        // Add the request to the RequestQueue.
        queue.add(jsonRequest);

    }


}
