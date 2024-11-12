package edu.mj.mart.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import edu.mj.mart.activities.tutorial.TutorialFragment;
import edu.mj.mart.utils.Constants;
import kotlin.Triple;

public class TutorialViewPagerAdapter extends FragmentStateAdapter {

    private final List<Triple<Integer, String, String>> tutorials = Constants.getTutorials();

    public TutorialViewPagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int).
        Fragment fragment = new TutorialFragment();
        Bundle args = new Bundle();
        // The object is just an integer.
        args.putInt("icon", tutorials.get(position).getFirst());
        args.putString("title", tutorials.get(position).getSecond());
        args.putString("description", tutorials.get(position).getThird());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return tutorials.size();
    }
}
