package com.example.coincubby;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    private LinearLayout navHomeContainer, navRentContainer, navWalletContainer, navProfileContainer;
    private ImageView navHomeIcon, navRentIcon, navWalletIcon, navProfileIcon;
    private TextView navHomeText, navRentText, navWalletText, navProfileText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        navHomeContainer    = findViewById(R.id.nav_home_container);
        navRentContainer    = findViewById(R.id.nav_rent_container);
        navWalletContainer  = findViewById(R.id.nav_wallet_container);
        navProfileContainer = findViewById(R.id.nav_profile_container);
        navHomeIcon         = findViewById(R.id.nav_home_icon);
        navRentIcon         = findViewById(R.id.nav_rent_icon);
        navWalletIcon       = findViewById(R.id.nav_wallet_icon);
        navProfileIcon      = findViewById(R.id.nav_profile_icon);
        navHomeText         = findViewById(R.id.nav_home_text);
        navRentText         = findViewById(R.id.nav_rent_text);
        navWalletText       = findViewById(R.id.nav_wallet_text);
        navProfileText      = findViewById(R.id.nav_profile_text);

        if (savedInstanceState == null) {
            loadFragment(new DashboardFragment(), "Dashboard");
            updateNavUI("Home");
        }

        navHomeContainer.setOnClickListener(v -> {
            loadFragment(new DashboardFragment(), "Dashboard");
            updateNavUI("Home");
        });

        navRentContainer.setOnClickListener(v -> {
            loadFragment(new RentingFragment(), "Renting");
            updateNavUI("Rent");
        });

        navWalletContainer.setOnClickListener(v -> navigateToWallet());

        navProfileContainer.setOnClickListener(v -> navigateToProfile());
    }

    // ── Public navigation methods (called from fragments) ─────────────────────

    public void navigateToProfile() {
        loadFragment(new ProfileFragment(), "Profile");
        updateNavUI("Profile");
    }

    public void navigateToWallet() {
        loadFragment(new WalletFragment(), "Wallet");
        updateNavUI("Wallet");
    }

    // ─────────────────────────────────────────────────────────────────────────

    private void loadFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, tag)
                .commit();
    }

    private void updateNavUI(String selected) {
        resetNavItem(navHomeContainer,    navHomeIcon,    navHomeText);
        resetNavItem(navRentContainer,    navRentIcon,    navRentText);
        resetNavItem(navWalletContainer,  navWalletIcon,  navWalletText);
        resetNavItem(navProfileContainer, navProfileIcon, navProfileText);

        if (selected.equals("Home")) {
            selectNavItem(navHomeContainer,    navHomeIcon,    navHomeText);
        } else if (selected.equals("Rent")) {
            selectNavItem(navRentContainer,    navRentIcon,    navRentText);
        } else if (selected.equals("Wallet")) {
            selectNavItem(navWalletContainer,  navWalletIcon,  navWalletText);
        } else if (selected.equals("Profile")) {
            selectNavItem(navProfileContainer, navProfileIcon, navProfileText);
        }
    }

    private void resetNavItem(LinearLayout container, ImageView icon, TextView text) {
        container.setBackground(null);
        text.setVisibility(View.GONE);
        icon.setColorFilter(ContextCompat.getColor(this, R.color.black));
    }

    private void selectNavItem(LinearLayout container, ImageView icon, TextView text) {
        container.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_item_highlight));
        text.setVisibility(View.VISIBLE);
        icon.setColorFilter(ContextCompat.getColor(this, R.color.cc_text_primary));
    }
}