package org.example.android.numero;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by harshgupta on 02/10/16.
 */
public class GalleryPager2Fragment extends Fragment {

    private File[] listFile=null;
    private String FilePathStrings;
    private String mcategory_name;
    private int mtotal,mposition;
    private ImageView singlephoto;

    public static GalleryPager2Fragment newInstance(int total,int position,String category_name){
        GalleryPager2Fragment galleryPager2Fragment=new GalleryPager2Fragment();
        Bundle args = new Bundle();
        Log.d("Gallerypa",category_name);
        args.putInt("total",total);
        args.putInt("position",position);
        args.putString("category_name",category_name);
        galleryPager2Fragment.setArguments(args);
        return galleryPager2Fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mtotal = getArguments().getInt("total");
        mposition=getArguments().getInt("position");
        mcategory_name = getArguments().getString("category_name");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.galleryvertical, container, false);

        File folder = new File(Environment.getExternalStorageDirectory() + "/numeros/" + mcategory_name.toLowerCase());
        listFile = folder.listFiles();
        // Create a String array for FilePathStrings
        FilePathStrings = listFile[mposition].getAbsolutePath();

        singlephoto=(ImageView) rootView.findViewById(R.id.singlephoto);

        Picasso.with(getContext()).load("file:///"+FilePathStrings)
                .error(R.drawable.placeholder)
                .placeholder((R.drawable.placeholder))
                .into(singlephoto);


        return  rootView;
    }
}
