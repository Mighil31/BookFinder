package com.example.bookfinder;

public class Book {

    // Link of the image
    private String mLink;

    // Name of the book
    private String mTitle;

    // Name of the author
    private String mAuthor;

    // Price of the book
    private String mPrice;

    public Book(String title, String author, String price, String link) {
        mLink = link;
        mAuthor = author;
        mPrice = price;
        mTitle = title;
    }

    /**
     * Get link of image
     */
    public String getLink() {
        return mLink;
    }

    /**
     * Get name of the book
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Get name of the author
     */
    public String getAuthor() {
        return mAuthor;
    }

    /**
     * Get price of the book
     */
    public String getPrice() {
        return mPrice;
    }
}
