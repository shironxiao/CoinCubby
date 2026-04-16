package com.example.coincubby;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RentalFragment extends Fragment {

    private static final String ARG_LOCKER_ID = "locker_id";
    private static final String ARG_TIME = "time";
    private static final String ARG_ACCESS_CODE = "access_code";

    private String lockerId;
    private String time;
    private String accessCode;

    public static RentalFragment newInstance(String lockerId, String time, String accessCode) {
        RentalFragment fragment = new RentalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LOCKER_ID, lockerId);
        args.putString(ARG_TIME, time);
        args.putString(ARG_ACCESS_CODE, accessCode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            lockerId = getArguments().getString(ARG_LOCKER_ID);
            time = getArguments().getString(ARG_TIME);
            accessCode = getArguments().getString(ARG_ACCESS_CODE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rental_card, container, false);
        
        TextView tvLockerId = view.findViewById(R.id.tvLockerId);
        TextView tvAccessCode = view.findViewById(R.id.tvAccessCode);
        TextView tvTimeCost = view.findViewById(R.id.tvTimeCost);
        
        if (lockerId != null) tvLockerId.setText(lockerId);
        if (accessCode != null) tvAccessCode.setText(accessCode);
        if (time != null) tvTimeCost.setText(time);
        
        return view;
    }
}
