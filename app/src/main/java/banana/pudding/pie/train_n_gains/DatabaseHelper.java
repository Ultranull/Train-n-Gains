package banana.pudding.pie.train_n_gains;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_PATH = "/data/data/banana.pudding.pie.train_n_gains/databases/";
    //public static final String DATABASE_NAME = "CONTACT";
    public static final String DATABASE_NAME = "NEW_CONTACTS_DATABASE";
    public static final String TABLE_NAME = "TABLENAME";
    public static final String COL1 = "ID";
    public static final String COL2 = "WORKOUT_NAME";
    public static final String COL3 = "DESCRIPTION";

    public DatabaseHelper(Context context){super(context, DATABASE_NAME, null, 1);}

    @Override
    public void onCreate (SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " NAME TEXT, NUMBER TEXT)";
        db.execSQL(createTable);
    }



    @Override public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }



    public boolean addData(String workout_name, String des) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, workout_name);
        contentValues.put(COL3, des);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }



    public Cursor getListContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    public boolean deleteName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result =  db.delete(TABLE_NAME, "WORKOUT_NAME = ?", new String[] {name});

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteDes(String reps) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result =  db.delete(TABLE_NAME, "DESCRIPTION = ?", new String[] {reps});

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public Cursor viewData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

}