package com.exercise.pingidentity.feature.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;

import com.exercise.pingidentity.R;
import com.exercise.pingidentity.feature.encryption.EncryptionFragmentDirections;
import com.exercise.pingidentity.util.di.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    @Inject
    ViewModelProviderFactory viewModelFactory;

    @Nullable
    MainViewModel viewModel;

    private boolean isRunningByIntent = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(MainViewModel.class);
        isRunningByIntent = processIntent(getIntent());
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        isRunningByIntent = processIntent(intent);
    }

    private boolean processIntent(Intent intent) {
        if (intent.hasExtra("decrypted_text") && intent.hasExtra("decrypted_text2")) {
            navigateToBiometricScreen(intent.getExtras().getString("decrypted_text"));
            return true;
        }
        return false;
    }

        private void navigateToBiometricScreen(String decryptedText) {
            NavDirections openBiometricScreenAction = EncryptionFragmentDirections
                    .actionEncryptionFragmentToBiometricFragment()
                    .setDecryptedText(decryptedText);
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(openBiometricScreenAction);   //getWindow().getDecorView().getRootView()
        }
}
