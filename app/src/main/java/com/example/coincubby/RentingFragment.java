package com.example.coincubby;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RentingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_renting, container, false);

        // 1. Set the Locker ID
        TextView tvLockerId = view.findViewById(R.id.tvLockerId);
        tvLockerId.setText("S1");

        // 2. Setup Detail Rows
        setupDetailRow(view.findViewById(R.id.rowAccessCode), R.drawable.ic_hash,     "Access Code", "123BM");
        setupDetailRow(view.findViewById(R.id.rowStarted),    R.drawable.ic_clock,    "Started",     "Apr 18, 2026, 6:31 PM");
        setupDetailRow(view.findViewById(R.id.rowExpires),    R.drawable.ic_calendar, "Expires",     "Apr 18, 2026, 7:31 PM");
        setupDetailRow(view.findViewById(R.id.rowSize),       R.drawable.ic_box,      "Size",        "Small");

        // 3. Return locker button
        view.findViewById(R.id.btnReturnLocker).setOnClickListener(v ->
                Toast.makeText(getContext(), "Processing Return...", Toast.LENGTH_SHORT).show());

        // 4. See History → navigate to RentalHistoryFragment
        view.findViewById(R.id.btnSeeHistory).setOnClickListener(v -> {
            if (!isAdded()) return;
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new RentalHistoryFragment(), "RentalHistory")
                    .addToBackStack(null)
                    .commit();
        });

        // 5. btnBack → go to Profile
        view.findViewById(R.id.btnBack).setOnClickListener(v -> {
            if (!isAdded()) return;
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).navigateToProfile();
            }
        });

        return view;
    }

    private void setupDetailRow(View rowView, int iconRes, String label, String value) {
        if (rowView == null) return;

        ImageView ivIcon  = rowView.findViewById(R.id.icon);
        TextView  tvLabel = rowView.findViewById(R.id.label);
        TextView  tvValue = rowView.findViewById(R.id.value);

        if (ivIcon  != null && iconRes != 0) ivIcon.setImageResource(iconRes);
        if (tvLabel != null) tvLabel.setText(label);
        if (tvValue != null) tvValue.setText(value);
    }
}