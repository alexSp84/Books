package eu.fse.books;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private BooksAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private EditText searchEdTxt;

    private ArrayList<Book> myDataset;

    private RequestQueue queue;
    public static final String TAG = "StopRequest";

    public static final int EDIT_REQUEST = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.books_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        searchEdTxt = (EditText) findViewById(R.id.search_ed_txt);

        myDataset = new ArrayList<>();

        mAdapter = new BooksAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        searchEdTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String textSearched = searchEdTxt.getText().toString();
                findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
                if (queue != null)
                    queue.cancelAll(TAG);
                getBookFromURL(textSearched);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if(getIntent() != null) {
            Intent receivedSearch = getIntent();
            String authorSearch = receivedSearch.getStringExtra("author");
            searchEdTxt.setText(authorSearch);
        }

    }


    @Override
    protected void onStop() {
        super.onStop();
        if (queue != null)
            queue.cancelAll(TAG);
    }

    private void getBookFromURL(String textSearched) {
        // Make HTTP call

        if (!textSearched.equals("")) {

            // Instantiate the RequestQueue.
            queue = Volley.newRequestQueue(this);
            String url = null;

            try {
                url = "https://www.googleapis.com/books/v1/volumes?q=" + URLEncoder.encode(textSearched, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            // Request a string response from the provided URL.
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            findViewById(R.id.progress_bar).setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            Log.d("jsonRequest", response.toString());

                            try {
                                JSONArray result = response.getJSONArray("items");
                                mAdapter.clearBooksList();
                                ArrayList<Book> bookListFromResponse = Book.getBooksList(result);
                                mAdapter.addBooksList(bookListFromResponse);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(MainActivity.this, "Si è verificato un errore: " + error.networkResponse.statusCode, Toast.LENGTH_LONG).show();
                }
            });

            // Set the tag on the request.
            jsonRequest.setTag(TAG);

            // Add the request to the RequestQueue.
            queue.add(jsonRequest);

        } else {
            mAdapter.clearBooksList();
            findViewById(R.id.progress_bar).setVisibility(View.GONE);
        }
    }
}
