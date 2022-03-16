package com.distributing.data.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.RecyclerView;

import com.distributing.data.R;
import com.distributing.data.models.GoogleBookModel;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private ArrayList<GoogleBookModel> mGoogleBookModelList;
    private LoaderManager mLoaderManager;
    public final static String EXTRA_NAME = "BOOKS_LIST";

    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(GoogleBookModel model, View itemView);
    }

    public ItemAdapter(ArrayList<GoogleBookModel> googleBookModels, LoaderManager loaderManager, ListItemClickListener listener) {
        mGoogleBookModelList = googleBookModels;
        mLoaderManager = loaderManager;
        mOnClickListener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.model_list_item, parent, false);
        return new ItemViewHolder(itemView, this, mLoaderManager);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(mGoogleBookModelList.get(position));
    }

    public void setBookList(ArrayList<GoogleBookModel> bookList) {
        this.mGoogleBookModelList = bookList;
    }

    @Override
    public int getItemCount() {
        return mGoogleBookModelList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        final TextView titleTextView;
        final TextView authorsTextView;
        final TextView mCategoryTextView;
        final ItemAdapter mAdapter;
        final LoaderManager mLoaderManager;

        public ItemViewHolder(View itemView, ItemAdapter adapter, LoaderManager loaderManager) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleText);
            authorsTextView = itemView.findViewById(R.id.authorText);
            mCategoryTextView = itemView.findViewById(R.id.category);
            mAdapter = adapter;
            mLoaderManager = loaderManager;

            itemView.setOnClickListener(this);
        }

        void bind(GoogleBookModel model) {
            titleTextView.setText(model.getmTitle());
            authorsTextView.setText(model.getmAuthors());
            mCategoryTextView.setText(model.getmCategories());
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            GoogleBookModel element = mAdapter.mGoogleBookModelList.get(clickedPosition);
            mOnClickListener.onListItemClick(element, view);
        }
    }
}