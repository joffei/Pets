/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.pets.data.PetContract;
import com.example.android.pets.data.PetContract.*;
import com.example.android.pets.data.PetDbHelper;


/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    private PetDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new PetDbHelper(this);
        displayDatabaseInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        //PetDbHelper mDbHelper = new PetDbHelper(this);


        String[] projection = new String[]{PetEntry._ID, PetEntry.COLUMN_PET_NAME,
                                            PetEntry.COLUMN_PET_BREED, PetEntry.COLUMN_PET_GENDER,
                                            PetEntry.COLUMN_PET_WEIGHT};

        //Cursor cursor = db.query(PetEntry.TABLE_NAME, projection, null, null, null, null, null);

        Cursor cursor = getContentResolver().query(PetEntry.CONTENT_URI, projection, null, null, null);
        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            TextView displayView = (TextView) findViewById(R.id.text_view_pet);
            displayView.setText("The pets table contains " + cursor.getCount() + " pets");

            displayView.append("\n\n" + cursor.getColumnName(0));
            for (int i = 1; i < cursor.getColumnCount(); ++i){
                displayView.append(" - " + cursor.getColumnName(i));
            }

            final int _ID_COL_INDEX = cursor.getColumnIndex(PetEntry._ID);
            final int NAME_COL_INDEX = cursor.getColumnIndex(PetEntry.COLUMN_PET_NAME);
            final int BREED_COL_INDEX = cursor.getColumnIndex(PetEntry.COLUMN_PET_BREED);
            final int GENDER_COL_INDEX = cursor.getColumnIndex(PetEntry.COLUMN_PET_GENDER);
            final int WEIGHT_COL_INDEX = cursor.getColumnIndex(PetEntry.COLUMN_PET_WEIGHT);

            displayView.append("\n");

            while(cursor.moveToNext()){

                displayView.append("\n" + cursor.getInt(_ID_COL_INDEX) + " - " + cursor.getString(NAME_COL_INDEX) +
                                    " - " + cursor.getString(BREED_COL_INDEX) + " - " + cursor.getInt(GENDER_COL_INDEX) +
                                    " - " + cursor.getInt(WEIGHT_COL_INDEX));
            }

        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    private void insertPet(){
        //SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PetEntry.COLUMN_PET_NAME, "Toto");
        values.put(PetEntry.COLUMN_PET_BREED, "Terrier");
        values.put(PetEntry.COLUMN_PET_GENDER, PetEntry.GENDER_MALE);
        values.put(PetEntry.COLUMN_PET_WEIGHT, 7);

        /*long newRowId =*/ getContentResolver().insert(PetEntry.CONTENT_URI,values);

        //Log.v("CatalogActivity", "New Row ID: " + newRowId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
