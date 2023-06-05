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

public class FeedFragment extends Fragment {

    private Vector<Review> vReview;
    ReviewHelper reviewHelper;
    RecyclerView feedRecyclerView;
    UserHelper userHelper;
    User user;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
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
        vReview = reviewHelper.viewReview(user.getId());
        reviewHelper.close();

        feedRecyclerView = view.findViewById(R.id.feedRV);
        FeedAdapter feedAdapter = new FeedAdapter(getContext(), vReview);
        feedRecyclerView.setAdapter(feedAdapter);
        feedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void initialize() {
        vReview = new Vector<>();
    }
}