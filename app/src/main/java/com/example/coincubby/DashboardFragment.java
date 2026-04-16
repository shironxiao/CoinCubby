package com.example.coincubby;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DashboardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        LinearLayout rentalCardsContainer = view.findViewById(R.id.rentalCardsContainer);
        
/*
        // Add sample rental cards
        if (getChildFragmentManager().findFragmentById(R.id.rentalCardsContainer) == null) {
            getChildFragmentManager().beginTransaction()
                    .add(R.id.rentalCardsContainer, RentalFragment.newInstance("Locker 12", "02 : 45 : 12", "S-012"))
                    .add(R.id.rentalCardsContainer, RentalFragment.newInstance("Locker 05", "01 : 12 : 30", "M-005"))
                    .commit();
        }
*/

        return view;
    }
}
