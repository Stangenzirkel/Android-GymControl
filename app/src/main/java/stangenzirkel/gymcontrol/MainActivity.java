package stangenzirkel.gymcontrol;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import stangenzirkel.gymcontrol.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExerciseDBHelper.init(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_statistic, R.id.navigation_exrecises)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        NavOptions options = new NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setEnterAnim(R.anim.empty_animation)
                .setExitAnim(R.anim.empty_animation)
                .setPopEnterAnim(R.anim.empty_animation)
                .setPopExitAnim(R.anim.empty_animation)
                .build();
        navView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    navController.navigate(R.id.navigation_home, null, options);
                    break;

                case R.id.navigation_statistic:
                    navController.navigate(R.id.navigation_statistic, null, options);
                    break;

                case R.id.navigation_exrecises:
                    navController.navigate(R.id.navigation_exrecises, null, options);
                    break;
            }
            return true;
        });
    }
}