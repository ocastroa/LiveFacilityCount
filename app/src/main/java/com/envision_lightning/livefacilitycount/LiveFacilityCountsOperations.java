package com.envision_lightning.livefacilitycount;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by ocastroa on 7/23/16.
 */
public class LiveFacilityCountsOperations {

    private LiveFacilityCountDatabase db;
//    private String[] TABLE_COLUMNS = {db.FACILITY_ID, db.FACILITY_LOCATION,
//            db.FACILITY_PARTICIPANTS, db.FACILITY_CHECKIN};
    private SQLiteDatabase database;

    public LiveFacilityCountsOperations(Context context) {
        db = new LiveFacilityCountDatabase(context);
    }

    public void open() throws SQLException {
        database = db.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long insertData(String location, String numPart, String checkIn) {
        ContentValues contentVal = new ContentValues();
        contentVal.put(db.FACILITY_LOCATION, location);
        contentVal.put(db.FACILITY_PARTICIPANTS, numPart);
        contentVal.put(db.FACILITY_CHECKIN, checkIn);

        long id = database.insert(db.TABLE_NAME, null, contentVal);
        return id;
    }


    public void deleteAll() { // delete all data
        database.delete(db.TABLE_NAME, null, null);
        database.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + db.TABLE_NAME + "'"); // reset autoincrement id back to 1
    }

//    public void deleteData(long id) { // delete data based on id that person chose
//        database.delete(db.TABLE_NAME, db.FACILITY_ID + "=" + id, null);
//        database.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + db.TABLE_NAME + "'"); // reset autoincrement id back to 1
//       }

    public Cursor retrieveData(long id) {
        database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from " + db.TABLE_NAME +  " where Id = ?", new String[] {String.valueOf(id)});
       if(cursor != null && cursor.moveToFirst()){
           Log.d("Taglines", "cursor not null");
           return cursor;
       }
        return null;
    }
}