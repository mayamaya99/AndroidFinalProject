package com.distributing.data;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.distributing.data.adapters.ItemAdapter;
import com.distributing.data.models.GoogleBookModel;
import com.distributing.data.utilities.NetworkUtils;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class MainActivity extends AppCompatActivity implements ItemAdapter.ListItemClickListener {

    private ArrayList<GoogleBookModel> bookData;
    private EditText mBookInput;
    private TextView mTitleText;
    private TextView mAuthorText;
    private ItemAdapter itemAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    ActionBar actionBar;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBookInput = (EditText) findViewById(R.id.bookInput);
        mTitleText = (TextView) findViewById(R.id.titleText);
        mAuthorText = (TextView) findViewById(R.id.authorText);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.dd_loading_indicator);
        bookData = new ArrayList<>();

        Button searchBtn = findViewById(R.id.searchButton);

        searchBtn.setOnClickListener(this::searchBooks);
        actionBar= getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF5733")));
        actionBar.setTitle("Display API Data");




    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setStatusBarColor(Color.parseColor("#DB5C41"));
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void searchBooks(View view) {
        String queryString = mBookInput.getText().toString();
        Log.d("Search", queryString);

        progressBar.setVisibility(View.VISIBLE);

        //Use an Async Function to call the API request and store the results
        CompletableFuture<Void> bookResults = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                Log.d("Run", queryString);

                //Run query to for search data
                bookData = NetworkUtils.getBookInfo(queryString);
                Log.d("Books", bookData.toString());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Make progress bar invisible once finished
                        progressBar.setVisibility(View.INVISIBLE);

                        TextView errorMesage = findViewById(R.id.main_error_message);
                        errorMesage.setVisibility(View.INVISIBLE);

                        //Update recycler view with new data
                        updateUI();


                        //Display an error toast if bookData is empty
                        if (bookData.size() == 0) {
                            Toast error = Toast.makeText(getApplicationContext(), "Error: search found no books!", Toast.LENGTH_LONG);
                            error.show();
                        }
                    }
                });
            }
        });

        //Hide text window
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void updateUI(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        itemAdapter = new ItemAdapter(bookData, LoaderManager.getInstance(this), this);

        mRecyclerView.setAdapter(itemAdapter);
        Log.d("IT$", "Loaded");
    }


    @Override
    public void onListItemClick(GoogleBookModel model, View itemView) {
        Intent i = new Intent(this, ModelDetailPage.class);
        i.putExtra("title", model.getmTitle());
        i.putExtra("authors", model.getmAuthors());
        i.putExtra("description", model.getmDescription());
        i.putExtra("publisher", model.getmPublisher());
        i.putExtra("publishDate", model.getmPublishedDate());
        i.putExtra("categories", model.getmCategories());
        startActivity(i);

    }
}