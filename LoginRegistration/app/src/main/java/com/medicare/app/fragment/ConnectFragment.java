package com.medicare.app.fragment;

/**
 * Created by satveer on 24-07-2017.
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medicare.launch.app.R;

public class ConnectFragment extends Fragment {

    public ConnectFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_connect, container, false);

        return rootView;
    }

}