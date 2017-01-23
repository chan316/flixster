package com.codepath.flixster.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flixster.R;
import com.codepath.flixster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.codepath.flixster.R.id.tvOverview;
import static com.codepath.flixster.R.id.tvTitle;

/**
 * Created by chanis on 1/23/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    // View lookup cache
    private static class ViewHolder {

        TextView title;
        TextView overview;
        ImageView image;
    }
    private String imgPlaceholder = "http://allipadwallpapers.com/wp-content/uploads/wallpapers/Vector/previews/preview_Zoidberg.jpg";

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);


        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);

            viewHolder.title    = (TextView)  convertView.findViewById(tvTitle);
            viewHolder.overview = (TextView)  convertView.findViewById(tvOverview);
            viewHolder.image    = (ImageView) convertView.findViewById(R.id.lvMovieImage);

            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(movie.getOriginalTitle());
        viewHolder.overview.setText(movie.getOverview());
        viewHolder.image.setImageResource(0);

        Picasso.with(getContext()).load(movie.getPosterPath())
                .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.image);

        return convertView;
    }
}
