package com.example.coincubby;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.coincubby.shared.SessionManager;

public class DashboardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // ── Welcome name from session ─────────────────────────────────────────
        TextView tvWelcomeName = view.findViewById(R.id.tvWelcomeName);
        String fullName = SessionManager.getFullName(requireContext());
        if (fullName != null && !fullName.isEmpty()) {
            tvWelcomeName.setText(fullName + "!");
        } else {
            String email = SessionManager.getEmail(requireContext());
            tvWelcomeName.setText(email != null ? email + "!" : "there!");
        }

        // ── Profile icon → navigate to ProfileFragment ────────────────────────
        ImageView btnProfile = view.findViewById(R.id.btnBack2);
        btnProfile.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToProfile();
            }
        });

        // ── Rental cards container (kept for future use) ──────────────────────
        LinearLayout rentalCardsContainer = view.findViewById(R.id.rentalCardsContainer);

        return view;
    }
}