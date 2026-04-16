package com.example.coincubby;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ConfigureRentalFragment extends Fragment {

    private static final String ARG_LOCKER_ID = "locker_id";
    private static final String ARG_LOCKER_DETAILS = "locker_details";

    private String mLockerId;
    private String mLockerDetails;

    public static ConfigureRentalFragment newInstance(String lockerId, String lockerDetails) {
        ConfigureRentalFragment fragment = new ConfigureRentalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LOCKER_ID, lockerId);
        args.putString(ARG_LOCKER_DETAILS, lockerDetails);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLockerId = getArguments().getString(ARG_LOCKER_ID);
            mLockerDetails = getArguments().getString(ARG_LOCKER_DETAILS);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configure_rental, container, false);

        TextView tvId = view.findViewById(R.id.tvConfigLockerId);
        TextView tvDetails = view.findViewById(R.id.tvConfigLockerDetails);

        tvId.setText(mLockerId);
        tvDetails.setText(mLockerDetails);

        return view;
    }
}
