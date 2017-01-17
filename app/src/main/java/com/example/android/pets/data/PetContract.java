package com.example.android.pets.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Jesse on 1/10/2017.
 */

public final class PetContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.pets";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_PETS = "pets";

    public static abstract class PetEntry implements BaseColumns{

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);

        /*table name*/
        public static final String TABLE_NAME = "pets";

        /*Table columns*/
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PET_NAME = "name";
        public static final String COLUMN_PET_BREED = "breed";
        public static final String COLUMN_PET_GENDER = "gender";
        public static final String COLUMN_PET_WEIGHT = "weight";

        /*Gender values*/
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;
        public static final int GENDER_UNKOWN = 0;
    }
}
