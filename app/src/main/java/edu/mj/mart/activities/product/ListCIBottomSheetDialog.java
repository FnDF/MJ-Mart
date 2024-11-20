package edu.mj.mart.activities.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import edu.mj.mart.R;
import edu.mj.mart.adapter.ListCIAdapter;
import edu.mj.mart.model.CI;

public class ListCIBottomSheetDialog extends BottomSheetDialogFragment {

    private final OnCIListener listener;

    private final ArrayList<CI> cis = new ArrayList<>();

    public ListCIBottomSheetDialog(OnCIListener listener) {
        this.listener = listener;
    }

    interface OnCIListener {
        void onSelectedCI(CI ci);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_ci_bottom_sheet_dialog, container, false);

        if (getArguments() != null) {
            cis.addAll((ArrayList<CI>) getArguments().getSerializable("cis"));
        }

        ListCIAdapter ciAdapter = new ListCIAdapter(cis, item -> {
            if (listener != null) {
                listener.onSelectedCI(item);
                dismiss();
            }
        });
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(ciAdapter);
        return v;
    }
}
