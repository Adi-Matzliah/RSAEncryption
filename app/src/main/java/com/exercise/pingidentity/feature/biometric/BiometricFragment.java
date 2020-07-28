package com.exercise.pingidentity.feature.biometric;

import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.exercise.pingidentity.R;
import com.exercise.pingidentity.databinding.BiometricFragmentBinding;
import com.exercise.pingidentity.feature.home.MainActivity;
import com.exercise.pingidentity.util.di.ViewModelProviderFactory;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class BiometricFragment extends DaggerFragment {

    @Inject
    ViewModelProviderFactory viewModelFactory;

    @Nullable
    BiometricViewModel viewModel;

    private BiometricFragmentBinding binding;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    public static BiometricFragment newInstance() {
        return new BiometricFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.biometric_fragment, container, false);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(BiometricViewModel.class);
        initDataBinding();
        observeFields();
        viewModel.initialize();
        viewModel.setDecryptedText(getArguments().getString("decrypted_text"));
        showBiometricDialog();
        return binding.getRoot();
    }

    private void initDataBinding() {
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
    }

    private void observeFields() {
    }

    private void showBiometricDialog() {
        executor = ContextCompat.getMainExecutor(getContext());
        biometricPrompt = new BiometricPrompt(BiometricFragment.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getActivity(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                viewModel.setTextVisibility(true);
                Toast.makeText(getActivity(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getActivity(),
                        "Authentication failed", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login is required!")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }

/*    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }*/
}
