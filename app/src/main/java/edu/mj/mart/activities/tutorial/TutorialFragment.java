package edu.mj.mart.activities.tutorial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import edu.mj.mart.R;

public class TutorialFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tutorial, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args == null) return;
        ((AppCompatImageView) view.findViewById(R.id.ivIcon)).setImageResource(args.getInt("icon"));
        ((TextView) view.findViewById(R.id.tvTitle)).setText(args.getString("title"));
        ((TextView) view.findViewById(R.id.tvDescription)).setText(args.getString("description"));
    }
}
