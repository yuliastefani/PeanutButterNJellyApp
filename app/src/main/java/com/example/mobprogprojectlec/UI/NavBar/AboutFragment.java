package com.example.mobprogprojectlec.UI.NavBar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobprogprojectlec.R;

public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View aboutFragment = inflater.inflate(R.layout.fragment_about, container, false);

        //make me about us page


        return aboutFragment;
    }
}