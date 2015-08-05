package com.suh.itboy.auctionsystem.Fragments.Dashboard;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.suh.itboy.auctionsystem.Adapters.ImageSliderAdapter;
import com.suh.itboy.auctionsystem.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageSliderFragment extends Fragment {
    int imageResourceId = 0;

    public ImageSliderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        imageResourceId = bundle.getInt(ImageSliderAdapter.KEY_SLIDE);
        View view =  inflater.inflate(R.layout.fragment_image_slider, container, false);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
        Glide.with(getActivity()).load(imageResourceId).centerCrop().into(imageView);
        /*if (imageResourceId != 0)
            imageSliderLayout.setBackgroundResource(imageResourceId);
            */
        return view;
    }


}
