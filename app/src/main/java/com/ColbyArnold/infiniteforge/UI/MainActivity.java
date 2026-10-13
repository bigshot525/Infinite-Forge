package com.colbyarnold.infiniteforge.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.colbyarnold.infiniteforge.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    /*
    Infinite Forge is a mobile app, single player rpg where the user fights monsters in a dungeon to get better loot
    The user can then pick and choose what loot to fight with and what weapons to use.
    The user can also upgrade certain weapons or armor to improve their stats.


    in the future I want to explore possibility of adding other skills like mining or fishing.

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Apply top and side insets to the main layout, leaving bottom padding for the nav bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        // Let bottom_navigation handle the system bottom inset cleanly
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            ViewCompat.setOnApplyWindowInsetsListener(bottomNav, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(v.getPaddingLeft(), v.getPaddingTop(), v.getPaddingRight(), systemBars.bottom);
                return insets;
            });

            bottomNav.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    // Handle home navigation
                    return true;
                } else if (itemId == R.id.navigation_inventory) {
                Intent intent = new Intent(this, InventoryActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(intent);
                return true;
                } else if (itemId == R.id.navigation_merge) {
                    //take to merge screen
                    return true;
                } else if (itemId == R.id.navigation_settings) {
                    //take to settings

                    return true;
                }
                return false;
            });
        }
    }
}