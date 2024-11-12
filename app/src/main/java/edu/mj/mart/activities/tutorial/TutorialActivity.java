package edu.mj.mart.activities.tutorial;

import android.content.Intent;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;

import edu.mj.mart.R;
import edu.mj.mart.activities.AuthActivity;
import edu.mj.mart.adapter.TutorialViewPagerAdapter;
import edu.mj.mart.core.BaseActivity;
import edu.mj.mart.databinding.ActivityTutorialBinding;
import edu.mj.mart.utils.SharedPrefUtils;

public class TutorialActivity extends BaseActivity<ActivityTutorialBinding> {

    @NonNull
    @Override
    protected ActivityTutorialBinding createViewBinding(LayoutInflater layoutInflater) {
        return ActivityTutorialBinding.inflate(layoutInflater);
    }

    @Override
    public void setupView() {
        TutorialViewPagerAdapter viewPagerAdapter = new TutorialViewPagerAdapter(this);
        binding.viewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                (tab, position) -> {
                }
        ).attach();

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 2) {
                    binding.btnSkip.setEnabled(true);
                    binding.btnSkip.setBackgroundResource(R.drawable.bg_button);
                } else {
                    binding.btnSkip.setEnabled(false);
                    binding.btnSkip.setBackgroundResource(R.drawable.bg_button_disable);
                }
            }
        });

        binding.btnSkip.setOnClickListener(view -> {
            SharedPrefUtils.saveData(this, SharedPrefUtils.KEY_IS_FIRST_TIME, false);
            startActivity(new Intent(TutorialActivity.this, AuthActivity.class));
            finish();
        });
    }
}