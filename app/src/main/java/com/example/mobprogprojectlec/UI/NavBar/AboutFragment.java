package com.example.mobprogprojectlec.UI.NavBar;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobprogprojectlec.Database.AlbumHelper;
import com.example.mobprogprojectlec.R;

import org.w3c.dom.Text;

public class AboutFragment extends Fragment {

    ImageView iv_facebook, iv_instagram, iv_twitter, includedLayout;
    TextView tvAboutUs, tvAboutUsDetail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View aboutFragment = inflater.inflate(R.layout.fragment_about, container, false);
        return aboutFragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iv_facebook = view.findViewById(R.id.iv_facebook);
        iv_instagram = view.findViewById(R.id.iv_instagram);
        iv_twitter = view.findViewById(R.id.iv_twitter);
        tvAboutUs = view.findViewById(R.id.tvAboutUs);
        tvAboutUsDetail = view.findViewById(R.id.tvAboutUsDetail);

        tvAboutUs.setText("About Us");
        tvAboutUsDetail.setText("Things you need to know about us");

        iv_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/";
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        iv_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.instagram.com/";
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        iv_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://twitter.com/";
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        includedLayout = view.findViewById(R.id.includedLayout);
        int nightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (nightMode == Configuration.UI_MODE_NIGHT_YES) {
            includedLayout.setImageResource(R.drawable.rounded_background_dark);
            tvAboutUs.setTextColor(Color.BLACK);
            tvAboutUsDetail.setTextColor(Color.BLACK);
        } else {
            includedLayout.setImageResource(R.drawable.rounded_background);
        }

    }
}