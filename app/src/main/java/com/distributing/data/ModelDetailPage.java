package com.distributing.data;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.distributing.data.adapters.ItemAdapter;
import com.distributing.data.models.GoogleBookModel;
import android.widget.ImageView;

public class ModelDetailPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.model_detail_page);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        TextView bookTitle = findViewById(R.id.book_title_text);
        TextView bookAuthors = findViewById(R.id.book_author);
        TextView bookPublisher = findViewById(R.id.book_publisher);
        TextView bookDescription = findViewById(R.id.book_description);
        TextView bookCategory = findViewById(R.id.book_category);


        if (extras != null) {
            extras = getIntent().getExtras();
            GoogleBookModel item = new GoogleBookModel(
                    extras.getString("title"),
                    extras.getString("authors"),
                    extras.getString("description"),
                    extras.getString("publisher"),
                    extras.getString("datePublished"),
                    extras.getString("categories")

            );

            bookTitle.setText(item.getmTitle());
            bookAuthors.setText(item.getmAuthors());
            bookPublisher.setText(getString(R.string.publisher) + " " + item.getmPublisher());
            bookDescription.setText(item.getmDescription());
            bookCategory.setText(getString(R.string.category) + " " + item.getmCategories());


            Bundle bundle = new Bundle();
        }
    }

}