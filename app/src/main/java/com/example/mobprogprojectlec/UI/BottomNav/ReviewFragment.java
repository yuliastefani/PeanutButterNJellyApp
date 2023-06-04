package com.example.mobprogprojectlec.UI.BottomNav;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobprogprojectlec.Database.ReviewHelper;
import com.example.mobprogprojectlec.Database.UserHelper;
import com.example.mobprogprojectlec.Model.Review;
import com.example.mobprogprojectlec.Model.User;
import com.example.mobprogprojectlec.R;

import java.util.Vector;

public class ReviewFragment extends Fragment {

    private ReviewHelper reviewHelper;
    private Vector<Review> vReview;
    private RecyclerView reviewRecyclerView;
    UserHelper userHelper;
    User user;


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

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("username",Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        userHelper = new UserHelper(getContext());
        userHelper.open();
        user = userHelper.getUser(username);
        userHelper.close();

        reviewHelper = new ReviewHelper(getContext());
        reviewHelper.open();
        vReview = reviewHelper.viewReviewbyUserID(user.getId());
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