package mju.com.iojo.Setting;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import mju.com.iojo.R;

/**
 * Created by TaeHoon on 2017-06-04.
 */

public class BookmarkListAdapter extends ArrayAdapter<Bookmark> {
    ArrayList<Bookmark> Bookmarks;
    Context context;
    int resource;

    public BookmarkListAdapter(Context context, int resource, ArrayList<Bookmark> Bookmarks) {
        super(context, resource, Bookmarks);
            this.context = context;
            this.resource = resource;
            this.Bookmarks = Bookmarks;
        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                LayoutInflater layoutInflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.bookmark_list, null, true);
            }
            Bookmark bookmark = getItem(position);
//            System.out.println("bookmark" + position + " : " + bookmark.getTop());
//            System.out.println("bookmark" + position + " : " + bookmark.getBottom());

            ImageView TopImageView = (ImageView) convertView.findViewById(R.id.BookmarkTopImageView);
            ImageView BottomImageView = (ImageView) convertView.findViewById(R.id.BookmarkBottomImageView);

            Picasso.with(context)
                    .load(bookmark.getTop())
                    .resize(400, 400)
                    .into(TopImageView);
            Picasso.with(context)
                    .load(bookmark.getBottom())
                    .resize(400, 400)
                    .into(BottomImageView);

            return convertView;
    }
}
