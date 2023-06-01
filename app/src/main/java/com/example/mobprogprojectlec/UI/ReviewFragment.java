package com.example.mobprogprojectlec.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobprogprojectlec.Database.ReviewHelper;
import com.example.mobprogprojectlec.Database.SongHelper;
import com.example.mobprogprojectlec.Model.Review;
import com.example.mobprogprojectlec.R;
import com.example.mobprogprojectlec.UI.NavBar.SongAdapter;

import java.util.Vector;

public class ReviewFragment extends Fragment {

    private ReviewHelper reviewHelper;
    private Vector<Review> vReview;
    private RecyclerView reviewRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();

        reviewHelper = new ReviewHelper(getContext());
        reviewHelper.open();
        vReview = reviewHelper.viewReview();
        reviewHelper.close();

        reviewRecyclerView = view.findViewById(R.id.reviewRV);
        ReviewAdapter reviewAdapter = new ReviewAdapter(getContext(), vReview);
        reviewRecyclerView.setAdapter(reviewAdapter);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void initialize() {
        vReview = new Vector<>();
    }
}