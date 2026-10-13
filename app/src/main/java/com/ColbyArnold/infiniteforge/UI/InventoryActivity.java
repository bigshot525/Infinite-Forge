package com.colbyarnold.infiniteforge.UI;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.colbyarnold.infiniteforge.R;
import com.colbyarnold.infiniteforge.data.Inventory;

public class InventoryActivity extends AppCompatActivity {

    private Inventory inventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        TextView goldText = findViewById(R.id.GoldText);
        TextView tvCapacity = findViewById(R.id.tvCapacity);
        RecyclerView rvInventory = findViewById(R.id.rvInventory);
        rvInventory.setLayoutManager(new LinearLayoutManager(this));

        String username = getIntent().getStringExtra("username");

        inventory = new Inventory(username,
                items -> {
                    tvCapacity.setText(items.size() + "/" + inventory.getCapacity() + " slots");
                    //TODO show equiped gear and non equiped gear
                },
                (gold, level) -> {
                    goldText.setText(gold + " Gold");
                    tvCapacity.setText(inventory.getItems().size() + "/" + inventory.getCapacity() + " slots");
                });
    }
}
