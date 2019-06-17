package com.example.bookfinder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Activity context, ArrayList<Book> books) {

         super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if existing view is being reused, otherwise inflate the view\
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);

        }

        // Get Book object located at this position in the list
        Book currentBook = getItem(position);

        //Find TextView in the list_item.xml layout with id title
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title);
        // set this text on the TextView
        titleTextView.setText(currentBook.getTitle());

        //Find TextView in the list_item.xml layout with id title
        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author);
        // set this text on the TextView
        authorTextView.setText(currentBook.getAuthor());

        //Find TextView in the list_item.xml layout with id title
        TextView priceTextView = (TextView) listItemView.findViewById(R.id.price);
        // set this text on the TextView
        priceTextView.setText(currentBook.getPrice());

        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        ImageView bookImageView = (ImageView) listItemView.findViewById(R.id.book_image);
        // Get the image resource ID from the current AndroidFlavor object and
        // set the image to iconView
        ImageLoader.getInstance().displayImage(currentBook.getLink(), bookImageView); // Default options will be used

        // Return the whole list item layout
        // so that it can be shown in the ListView
        return listItemView;

        }

}
