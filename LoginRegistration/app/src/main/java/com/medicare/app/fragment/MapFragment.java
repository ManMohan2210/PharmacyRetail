package com.medicare.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medicare.launch.app.R;


//import static com.medicare.app.loginregistration.R.id.postPhotoButton;
//import static com.medicare.app.loginregistration.R.id.postStatusUpdateButton;


public class MapFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)

    {
View v = inflater.inflate(R.layout.fragment_map,container,false);
        return v;
    }
}