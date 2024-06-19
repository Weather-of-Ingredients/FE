package com.example.woi_fe;

import android.os.Bundle;
import android.util.Log;

<<<<<<< HEAD
import com.example.woi_fe.ui.CropPrediction.CropPredFragment;
import com.example.woi_fe.ui.Diet.DietUpdateFragment;

=======
import com.example.woi_fe.CropPrediction.CropPredFragment;
import com.example.woi_fe.Diet.DietUpdateFragment;
>>>>>>> graph
import com.example.woi_fe.ui.dashboard.DashboardFragment;
import com.example.woi_fe.ui.dietcal.DietCalFragment;
import com.example.woi_fe.ui.home.HomeFragment;

import com.example.woi_fe.ui.notifications.NotificationsFragment;
<<<<<<< HEAD

=======
>>>>>>> graph
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.woi_fe.databinding.ActivityMainBinding;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;

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

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_layout, new CropPredFragment())
                .commit();
        /*bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                //                    updateIcons(item, R.drawable.calendar_1);
                loadFragment(new DietUpdateFragment());
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
            } else if (itemId == R.id.navigation_dietCal){
                loadFragment(new DietCalFragment());
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("식단 일정");
                }
            } else if (itemId == R.id.navigation_makeDiet){
                loadFragment(new );
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("식단 등록");
                }
            }
            return true;
        });*/
        //loadFragment(new CropPredFragment());
        //bottomNavigationView.setSelectedItemId(R.id.navigation_home);

//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.main_layout, new CropPredFragment())
//                .commit();
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_layout, fragment);
        transaction.addToBackStack(null); // Optional: Add the fragment to the back stack
        transaction.commit();

<<<<<<< HEAD
=======
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_layout, new CropPredFragment())
                .commit();
>>>>>>> graph
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