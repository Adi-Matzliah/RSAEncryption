package com.exercise.pingidentity.feature.encryption;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.exercise.pingidentity.R;
import com.exercise.pingidentity.databinding.BiometricFragmentBinding;
import com.exercise.pingidentity.databinding.EncryptionFragmentBinding;
import com.exercise.pingidentity.feature.biometric.BiometricFragment;
import com.exercise.pingidentity.feature.biometric.BiometricViewModel;
import com.exercise.pingidentity.util.di.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class EncryptionFragment extends DaggerFragment {

    @Inject
    ViewModelProviderFactory viewModelFactory;

    @Nullable
    EncryptionViewModel viewModel;

    @NonNull
    private EncryptionFragmentBinding binding;

    public static EncryptionFragment newInstance() {
        return new EncryptionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.encryption_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(EncryptionViewModel.class);
        viewModel.createPushNotificationRegToken();
        initDataBinding();
        observeFields();
    }

    private void initDataBinding() {
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
    }

    private void observeFields() {

        viewModel.isBiometricAlertDialogVisible().observe(getViewLifecycleOwner(), isActive -> {
                new AlertDialog.Builder(getContext())
                    .setMessage(R.string.biometric_alert_dialog_message)
                    .setPositiveButton(R.string.biometric_alert_dialog_settings_btn, (dialog, which) -> {
                        openLockScreenSettings();
                    })
                    .setNegativeButton(getString(R.string.biometric_alert_dialog_cancel_btn), (dialog, which) -> {
                        dialog.cancel();
                    })
                    .setCancelable(false)
                        .show();
            });
    }

    @Override
    public void onStop() {
        viewModel.createBackgroundTask();
        super.onStop();
    }

    private void openLockScreenSettings() {
        Intent intent = new Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD);
        startActivity(intent);
    }
}
