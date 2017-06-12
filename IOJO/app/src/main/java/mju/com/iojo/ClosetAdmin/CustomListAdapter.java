package mju.com.iojo.ClosetAdmin;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import java.util.ArrayList;
import com.squareup.picasso.Picasso;

import mju.com.iojo.R;

/**
 * Created by TaeHoon on 2017-06-02.
 */

public class CustomListAdapter extends ArrayAdapter<StyleImage> {

    ArrayList<StyleImage> Images;
    Context context;
    int resource;

    public CustomListAdapter(Context context, int resource, ArrayList<StyleImage> Images) {
        super(context, resource, Images);
        this.Images = Images;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_list, null, true);

        }
        StyleImage styleImage = getItem(position);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.StyleImageView);

        Picasso.with(context)
                .load(styleImage.getImage())
                .resize(900,900)
                .into(imageView);

        return convertView;
    }
}