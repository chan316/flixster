package com.codepath.flixster.adapters;

import android.content.Context;
import android.content.res.Configuration;
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
import static com.codepath.flixster.models.Movie.MovieTypes.POPULAR;

/**
 * Created by chanis on 1/23/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    // View lookup cache
    private static class ViewHolderRegular {
        TextView title;
        TextView overview;
        ImageView image;
    }

    // View lookup cache
    private static class ViewHolderPopular {
        ImageView image;
    }


    private String imgPlaceholder = "http://allipadwallpapers.com/wp-content/uploads/wallpapers/Vector/previews/preview_Zoidberg.jpg";

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).movieType.ordinal();
    }

    @Override
    public int getViewTypeCount() {
        return Movie.MovieTypes.values().length;
    }

    /*
    public int getItemViewType(int position) {
        return getItem(position).movieType.ordinal();
    }
    */

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        int type = getItemViewType(position);


        switch (movie.movieType) {
            case POPULAR:
                ViewHolderPopular viewHolderPopular;
                View v = convertView;

                if (v == null) {
                    viewHolderPopular = new ViewHolderPopular();

                    v = getInflatedLayoutForType(type);

                    viewHolderPopular.image = (ImageView) v.findViewById(R.id.lvMovieImage);

                    // Cache the viewHolder object inside the fresh view
                    v.setTag(viewHolderPopular);
                } else {
                    viewHolderPopular = (ViewHolderPopular) v.getTag();
                }

                viewHolderPopular.image.setImageResource(0);

                Picasso.with(getContext()).load(getImage(movie))
                        .placeholder(R.mipmap.ic_launcher)
                        .into(viewHolderPopular.image);

                return v;

            case REGULAR:
                ViewHolderRegular viewHolderRegular;
                View v1 = convertView;

                if (v1 == null) {
                    viewHolderRegular = new ViewHolderRegular();

                    v1 = getInflatedLayoutForType(type);

                    viewHolderRegular.title    = (TextView)  v1.findViewById(tvTitle);
                    viewHolderRegular.overview = (TextView)  v1.findViewById(tvOverview);
                    viewHolderRegular.image    = (ImageView) v1.findViewById(R.id.lvMovieImage);

                    // Cache the viewHolder object inside the fresh view
                    v1.setTag(viewHolderRegular);
                } else {
                    viewHolderRegular = (ViewHolderRegular) v1.getTag();
                }

                viewHolderRegular.title.setText(movie.getOriginalTitle());
                viewHolderRegular.overview.setText(movie.getOverview());
                viewHolderRegular.image.setImageResource(0);

                Picasso.with(getContext()).load(getImage(movie))
                        .placeholder(R.mipmap.ic_launcher)
                        .into(viewHolderRegular.image);

                return v1;

            default:
                throw new Error();

        }
    }

    public String getImage(Movie movie) {
        int orientation = getContext().getResources().getConfiguration().orientation;

        if (movie.getMovieType() == POPULAR) {
            return movie.getBackdropPath();
        }
        else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            return movie.getPosterPath();
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return movie.getBackdropPath();
        } else {
            return "";
        }
    }

    private View getInflatedLayoutForType(int type) {
        if (type == POPULAR.ordinal()) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie_popular, null);
        } else if (type == Movie.MovieTypes.REGULAR.ordinal()) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie, null);
        } else {
            return null;
        }
    }


}
