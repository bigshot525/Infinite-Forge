package com.colbyarnold.infiniteforge.UI;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.colbyarnold.infiniteforge.R;

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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
