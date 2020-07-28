package com.exercise.pingidentity.feature.biometric;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exercise.pingidentity.R;
import com.exercise.pingidentity.databinding.BiometricFragmentBinding;
import com.exercise.pingidentity.util.di.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class BiometricFragment extends DaggerFragment {

    @Inject
    ViewModelProviderFactory viewModelFactory;

    @Nullable
    BiometricViewModel viewModel;

    private BiometricFragmentBinding binding;

    public static BiometricFragment newInstance() {
        return new BiometricFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.biometric_fragment, container, false);
        initDataBinding();
        observeFields();
        return binding.getRoot();
    }

    private void initDataBinding() {
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
    }

    private void observeFields() {
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(BiometricViewModel.class);
    }
}
