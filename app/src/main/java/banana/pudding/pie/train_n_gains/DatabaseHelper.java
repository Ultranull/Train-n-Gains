package banana.pudding.pie.train_n_gains;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

   // public static final String DATABASE_PATH = "/data/data/banana.pudding.pie.train_n_gains/databases/";
    public static final String DATABASE_NAME = "WORKOUT_ACTIONS";
    public static final String TABLE_NAME = "ACTIONS";
    public static final String ID = "ID";
    public static final String COL_NAME = "NAME";
    public static final String COL_DESC = "DESCRIPTION";
    public static final String COL_TYPE = "TYPE";

    public DatabaseHelper(Context context){super(context, DATABASE_NAME, null, 1);}

    @Override
    public void onCreate (SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " "+COL_NAME+" TEXT, "+COL_DESC+" TEXT,"+COL_TYPE+" TEXT)";
        db.execSQL(createTable);
    }



    @Override public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }



    public boolean addData(WorkoutAction woa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, woa.getId());
        contentValues.put(COL_NAME, woa.getName());
        contentValues.put(COL_DESC, woa.getDescription());
        contentValues.put(COL_TYPE, woa.getType().ordinal());

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result!=-1;
    }



    public Cursor getListContents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }


}