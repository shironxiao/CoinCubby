package com.example.coincubby;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RentALocker extends Fragment {

    public RentALocker() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rent_a_locker, container, false);

        // Both the X button and Cancel button go back to Dashboard
        View.OnClickListener goBack = v -> {
            if (!isAdded()) return;
            requireActivity().getSupportFragmentManager().popBackStack();
        };

        view.findViewById(R.id.btn_close).setOnClickListener(goBack);
        view.findViewById(R.id.btn_cancel).setOnClickListener(goBack);

        // TODO: wire up btn_confirm for rental logic
        view.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            // your confirm rental logic here
        });

        return view;
    }
}