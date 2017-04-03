
package com.envision_lightning.livefacilitycount;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LiveFacilityCountDatabase extends SQLiteOpenHelper{
//    private static LiveFacilityCountDatabase mInstance = null;

    public static final String DATABASE_NAME = "LiveFacilityCountDatabase.db";
    public static final String TABLE_NAME = "LiveFacilityCount_table";

    public static final String FACILITY_ID = "Id ";
    public static final String FACILITY_LOCATION= "Location ";
    public static final String FACILITY_PARTICIPANTS = "Number_of_Participants ";
    public static final String FACILITY_CHECKIN = "Last_Check_in ";


    public LiveFacilityCountDatabase(Context context){ // Constructor
        super(context, DATABASE_NAME,null,2);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + TABLE_NAME + " (Id INTEGER PRIMARY KEY AUTOINCREMENT, Location TEXT, Number_of_Participants INTEGER, Last_Check_in TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME); // delete table
        onCreate(db); // create new table
    }
}
