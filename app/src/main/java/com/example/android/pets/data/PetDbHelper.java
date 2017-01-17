package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.pets.data.PetContract.PetEntry;

/**
 * Created by Jesse on 1/10/2017.
 */

public class PetDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "shelter.db";

    /*SQL DATA TYPES*/
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String BLOB_TYPE = " BLOB";
    private static final String COMMA_SEP = ", ";

    /*SQL COLUMN MODIFIERS*/
    private static final String PRIMARY_KEY_MODIFIER = " PRIMARY KEY";
    private static final String AUTOINC_MODIFIER = " AUTOINCREMENT";
    private static final String NOT_NULL_MODIFIER = " NOT NULL";
    private  static final String DEFAULT_MODIFIER = " DEFAULT ";

    /*SQL CREATE TABLE STATEMENT*/
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + PetEntry.TABLE_NAME +
            "(" + PetEntry._ID + INT_TYPE + PRIMARY_KEY_MODIFIER + AUTOINC_MODIFIER + COMMA_SEP
            + PetEntry.COLUMN_PET_NAME + TEXT_TYPE + NOT_NULL_MODIFIER + COMMA_SEP
            + PetEntry.COLUMN_PET_BREED + TEXT_TYPE + COMMA_SEP
            + PetEntry.COLUMN_PET_GENDER + INT_TYPE + NOT_NULL_MODIFIER + COMMA_SEP
            + PetEntry.COLUMN_PET_WEIGHT + INT_TYPE + NOT_NULL_MODIFIER + DEFAULT_MODIFIER + "0);";

    /*SQL DROP TABLE STATEMENT*/
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + PetEntry.TABLE_NAME + ");";

    public PetDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
