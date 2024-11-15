package edu.mj.mart.activities.main;

import static edu.mj.mart.utils.Constants.KEY_SERIALIZABLE_ACCOUNT;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.mj.mart.R;
import edu.mj.mart.model.Account;
import edu.mj.mart.utils.Constants;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        BottomNavigationView navigationView = findViewById(R.id.bottomNavigationView);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
}
