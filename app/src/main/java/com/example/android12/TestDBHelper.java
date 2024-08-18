package com.example.android12;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class TestDBHelper extends DBHelper {

    private static final String TEST_DB_NAME = "CoVanVaDieuPhoiHP.db"; // Use a separate database for testing
    private static final int TEST_DB_VERSION = 1;

    public TestDBHelper(Context context) {
        super(context, TEST_DB_NAME, null, TEST_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db); // Calls the original onCreate method to create all tables
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion); // Calls the original onUpgrade method
    }
}
