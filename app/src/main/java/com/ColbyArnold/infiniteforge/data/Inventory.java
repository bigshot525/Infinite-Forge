package com.colbyarnold.infiniteforge.data;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    public static class Item {
        private String id;
        private String name;
        private String type;
        private int statBonus;
        private boolean equipped;
        private int quantity;

        public Item() {}

        public Item(String id, String name, String type, int statBonus, boolean equipped, int quantity) {
            this.id = id;
            this.name = name;
            this.type = type;
            this.statBonus = statBonus;
            this.equipped = equipped;
            this.quantity = quantity;
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public String getType() { return type; }
        public int getStatBonus() { return statBonus; }
        public boolean isEquipped() { return equipped; }
        public int getQuantity() { return quantity; }
    }

    public interface OnInventoryLoadedListener {
        void onInventoryLoaded(List<Item> items);
    }

    private FirebaseFirestore db;
    private int level = 1;
    private int gold;
    private final List<Item> items = new ArrayList<>();

    public Inventory(String username) {
        db = FirebaseFirestore.getInstance();
        fetchUserData(username, null);
        getUserInventory(username, null);
    }

    public void getUserInventory(String username) {
        getUserInventory(username, null);
    }

    public void getUserInventory(String username, OnInventoryLoadedListener listener) {
        if (db != null && username != null) {
            db.collection("users").document(username).collection("inventory").get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        items.clear();
                        for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                            String id = doc.getId();
                            String name = doc.getString("name");
                            String type = doc.getString("type");
                            Long statVal = doc.getLong("statBonus");
                            Boolean equippedVal = doc.getBoolean("equipped");
                            Long qtyVal = doc.getLong("quantity");
                            String path = doc.getString("path");



                            int statBonus = statVal != null ? statVal.intValue() : 0;
                            boolean equipped = equippedVal != null && equippedVal;
                            int quantity = qtyVal != null ? qtyVal.intValue() : 1;

                            items.add(new Item(id, name, type, statBonus, equipped, quantity));
                        }
                        if (listener != null) {
                            listener.onInventoryLoaded(items);
                        }
                    });
        }
    }

    public interface OnUserDataLoadedListener {
        void onUserDataLoaded(int gold, int level);
    }

    public Inventory(String username, OnInventoryLoadedListener listener,
                     OnUserDataLoadedListener userListener) {
        db = FirebaseFirestore.getInstance();
        fetchUserData(username, userListener);
        getUserInventory(username, listener);
    }

    private void fetchUserData(String username, OnUserDataLoadedListener userListener) {
        Log.d("Inventory", "fetchUserData username=" + username);
        if (db != null && username != null) {
            db.collection("users").document(username).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        Log.d("Inventory", "exists=" + documentSnapshot.exists()
                                + " gold=" + documentSnapshot.get("gold"));
                        if (documentSnapshot.exists()) {
                            Long goldVal = documentSnapshot.getLong("gold");
                            Long levelVal = documentSnapshot.getLong("level");
                            if (goldVal != null) this.gold = goldVal.intValue();
                            if (levelVal != null) this.level = levelVal.intValue();
                        }
                        if (userListener != null) {
                            userListener.onUserDataLoaded(gold, level);
                        }
                    })
                    .addOnFailureListener(e -> Log.e("Inventory", "user fetch failed", e));
        }
    }

    public List<Item> getItems() {
        return items;
    }

    public int getCapacity() {
        // Users inventory capacity depends on their level (capacity = level * 10)
        return level * 10;
    }

    public int getGold() {
        return gold;
    }

    public int getLevel() {
        return level;
    }
}