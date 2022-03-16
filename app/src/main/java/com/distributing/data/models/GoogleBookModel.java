package com.distributing.data.models;

public class GoogleBookModel {
    private String mTitle;
    private String mAuthors;
    private String mDescription;
    private String mPublisher;
    private String mPublishedDate;
    private String mCategories;


    public GoogleBookModel(String mTitle, String mAuthors, String mDescription, String mPublisher, String mPublishedDate, String mCategorie) {
        this.mTitle = mTitle;
        this.mAuthors = mAuthors;
        this.mDescription = mDescription;
        this.mPublisher = mPublisher;
        this.mPublishedDate = mPublishedDate;
        this.mCategories = mCategories;

    }

    public GoogleBookModel() {

    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmAuthors() {
        return mAuthors;
    }

    public void setmAuthors(String mAuthors) {
        this.mAuthors = mAuthors;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmPublisher() {
        return mPublisher;
    }

    public void setmPublisher(String mPublisher) {
        this.mPublisher = mPublisher;
    }

    public String getmPublishedDate() {
        return mPublishedDate;
    }

    public void setmPublishedDate(String mPublishedDate) {
        this.mPublishedDate = mPublishedDate;
    }

    public String getmCategories() {
        return mCategories;
    }

    public void setmCategories(String mCategories) {
        this.mCategories = mCategories;
    }

}
