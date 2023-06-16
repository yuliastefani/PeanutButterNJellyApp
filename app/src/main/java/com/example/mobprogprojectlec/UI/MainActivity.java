package com.example.mobprogprojectlec.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.mobprogprojectlec.UI.BottomNav.AccountFragment;
import com.example.mobprogprojectlec.UI.BottomNav.FeedFragment;
import com.example.mobprogprojectlec.UI.BottomNav.HomeFragment;
import com.example.mobprogprojectlec.UI.BottomNav.ReviewFragment;
import com.example.mobprogprojectlec.UI.Landing.LoginActivity;
import com.example.mobprogprojectlec.UI.NavBar.AboutFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import com.example.mobprogprojectlec.R;
import com.example.mobprogprojectlec.UI.NavBar.AlbumFragment;
import com.example.mobprogprojectlec.UI.NavBar.ArtistFragment;
import com.example.mobprogprojectlec.UI.NavBar.SongFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private BottomNavigationView bottom_nav;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
            getSupportActionBar().setTitle("Home");
        }

        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.nav_dark);
        Switch darkSwitch = menuItem.getActionView().findViewById(R.id.switch_dark_mode);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            darkSwitch.setChecked(true);
        }

        darkSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                Toast.makeText(MainActivity.this, "Dark Mode Activated", Toast.LENGTH_SHORT).show();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                Toast.makeText(MainActivity.this, "Dark Mode Deactivated", Toast.LENGTH_SHORT).show();
            }
        });

        bottom_nav = findViewById(R.id.bottomNav);
        bottom_nav.setOnItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                        getSupportActionBar().setTitle("Home");
                        break;

                    case R.id.bottom_review:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ReviewFragment()).commit();
                        getSupportActionBar().setTitle("My Review");
                        break;

                    case R.id.bottom_feed:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FeedFragment()).commit();
                        getSupportActionBar().setTitle("Feed");
                        break;

                    case R.id.bottom_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AccountFragment()).commit();
                        getSupportActionBar().setTitle("My Profile");
                        break;
                }
            return true;
        });



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.nav_dark:
//                //activate dark mode with switch
//                Toast.makeText(this, "Dark Mode", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                getSupportActionBar().setTitle("Home");
                break;

            case R.id.nav_artist:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ArtistFragment()).commit();
                getSupportActionBar().setTitle("Artist");
                break;

            case R.id.nav_album:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AlbumFragment()).commit();
                getSupportActionBar().setTitle("Album");
                break;

            case R.id.nav_song:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SongFragment()).commit();
                getSupportActionBar().setTitle("Song");
                break;

            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutFragment()).commit();
                getSupportActionBar().setTitle("About");
                break;

            case R.id.nav_logout:
                builder = new AlertDialog.Builder(this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to logout?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    SharedPreferences local = getSharedPreferences("username", Context.MODE_PRIVATE);
                    SharedPreferences.Editor spEdit = local.edit();
                    spEdit.putString("username", "null");
                    spEdit.commit();
                    Toast.makeText(MainActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                });
                builder.setNegativeButton("No", (dialog, which) -> {
                    Toast.makeText(MainActivity.this, "Logout Cancelled", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}