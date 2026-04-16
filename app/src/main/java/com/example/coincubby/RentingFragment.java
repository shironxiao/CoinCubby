package com.example.coincubby;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RentingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_renting, container, false);

        // Setup each locker item programmatically
        setupLocker(view, R.id.locker_s1, "S1", "Small", "Locker S1 (Small)");
        setupLocker(view, R.id.locker_s2, "S2", "Small", "Locker S2 (Small)");
        setupLocker(view, R.id.locker_s3, "S3", "Small", "Locker S3 (Small)");
        setupLocker(view, R.id.locker_s4, "S4", "Small", "Locker S4 (Small)");
        setupLocker(view, R.id.locker_m1, "M1", "Medium", "Locker M1 (Medium)");
        setupLocker(view, R.id.locker_m2, "M2", "Medium", "Locker M2 (Medium)");
        setupLocker(view, R.id.locker_l1, "L1", "Large", "Locker L1 (Large)");
        setupLocker(view, R.id.locker_l2, "L2", "Large", "Locker L2 (Large)");

        return view;
    }

    private void setupLocker(View root, int viewId, String lockerLabel, String sizeLabel, String details) {
        View lockerItem = root.findViewById(viewId);
        TextView tvId = lockerItem.findViewById(R.id.tvLockerId);
        TextView tvSize = lockerItem.findViewById(R.id.tvSize);
        
        tvId.setText(lockerLabel);
        tvSize.setText(sizeLabel);
        
        lockerItem.setOnClickListener(v -> {
            // Toggle gray background for selection
            boolean isSelected = v.getTag() != null && (boolean) v.getTag();
            if (!isSelected) {
                v.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.LTGRAY));
                v.setTag(true);
                addConfigPanel(lockerLabel, details);
            } else {
                // Optional: deselect logic (revert to original color)
                v.setBackgroundTintList(android.content.res.ColorStateList.valueOf(
                    androidx.core.content.ContextCompat.getColor(getContext(), R.color.cc_card_bg)));
                v.setTag(false);
                // Note: Removing the config panel would require more logic (tracking fragments)
            }
        });
    }

    private void addConfigPanel(String id, String details) {
        getChildFragmentManager().beginTransaction()
                .add(R.id.configContainer, ConfigureRentalFragment.newInstance(id, details))
                .commit();
    }
}
