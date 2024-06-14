package com.example.woi_fe;

import android.os.Bundle;
import android.view.MenuItem;
import android.util.Log;

import com.example.woi_fe.CropPrediction.CropPredFragment;
import com.example.woi_fe.ui.dashboard.DashboardFragment;
import com.example.woi_fe.ui.home.HomeFragment;
import com.example.woi_fe.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.woi_fe.databinding.ActivityMainBinding;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
      
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Home page");
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                //                    updateIcons(item, R.drawable.calendar_1);
                loadFragment(new HomeFragment());
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Home");
                }
            } else if (itemId == R.id.navigation_dashboard) {
                //                    updateIcons(item, R.drawable.checklist_1);
                loadFragment(new DashboardFragment());
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Dashboard");
                }
            } else if (itemId == R.id.navigation_notifications) {
                //                    updateIcons(item, R.drawable.user_1);
                loadFragment(new CropPredFragment());
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Notifications");
                }
            }
            return true;
        });
        loadFragment(new HomeFragment());
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.main_layout, new CropPredFragment())
//                .commit();
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_layout, fragment);
        transaction.addToBackStack(null); // Optional: Add the fragment to the back stack
        transaction.commit();
//        printKeyHash();
//
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.main_layout, new CropPredFragment())
//                .commit();
    }

    private static final String TAG = "KeyHash";


    private void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String keyHash = Base64.encodeToString(md.digest(), Base64.NO_WRAP);
                Log.d(TAG, "KeyHash: " + keyHash);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Package name not found", e);
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "No such algorithm", e);
        }
    }

}