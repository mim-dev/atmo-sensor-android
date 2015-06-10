package com.mim_development.android.atmosensor.controllers.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mim_development.android.atmosensor.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class AtmoHomeFragment extends Fragment {

    public AtmoHomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_atmo_home, container, false);
    }
}
