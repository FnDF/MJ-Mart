package edu.mj.mjmart.core;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import edu.mj.mjmart.base.BasePresenter;

public abstract class BaseFragment<V extends ViewBinding, P extends BasePresenter> extends Fragment {

    protected V binding;
    protected P presenter;

    @NonNull
    protected abstract V createBinding(LayoutInflater inflater, ViewGroup container);

    @NonNull
    protected abstract P createPresenter();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = createBinding(inflater, container);
        presenter = createPresenter();

        return binding.getRoot();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

}
