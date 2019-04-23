package banana.pudding.pie.train_n_gains;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_PATH = "/data/data/banana.pudding.pie.train_n_gains/databases/";
    public static final String DATABASE_NAME = "WORKOUTS";
    public static final String TABLE_NAME = "B";
    public static final String ID = "ID";
    public static final String COL_NAME = "NAME";
    public static final String COL_DESC = "DESCRIPTION";
    public static final String COL_INST = "INSTRUCTION";
    public static final String COL_TYPE = "TYPE";
    public static final String COL_DAY = "DAY";
    public static final String COL_MONTH = "MONTH";
    public static final String COL_COMPLETED = "COMPLETED";



    public DatabaseHelper(Context context){super(context, DATABASE_NAME, null, 1);}

    @Override
    public void onCreate (SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " NAME TEXT, DESCRIPTION TEXT, INSTRUCTION TEXT, TYPE TEXT, DAY TEXT, MONTH TEXT, COMPLETED TEXT)";
        db.execSQL(createTable);
    }



    @Override public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }



    public boolean addData(WorkoutAction woa, String day, String month, String completed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(ID, woa.getId());
        contentValues.put(COL_NAME, woa.getName());
        contentValues.put(COL_DESC, woa.getDescription());
        contentValues.put(COL_INST, woa.getInstructions());
        contentValues.put(COL_TYPE, woa.getType().ordinal());
        contentValues.put(COL_DAY, day);
        contentValues.put(COL_MONTH, month);
        contentValues.put(COL_COMPLETED, completed);


        long result = db.insert(TABLE_NAME, null, contentValues);
        //return result!=-1;

        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void moveRowUp(int row){
        int n=row,m=row-1;
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE "+TABLE_NAME+" SET "+ID+" = "+ID+"-1 WHERE "+ID+" > "+m+" AND "+ID+" <= "+n);
        db.execSQL("UPDATE "+TABLE_NAME+" SET "+ID+" = m WHERE "+ID+" = n");
    }

    public void clearTables(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from B");
    }

    public Cursor getListContents() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }


    public boolean deleteName(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "NAME = ?", new String[] {name});

        if(result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public void updateCompletion(String newCompletion, String oldCompletion) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME + " SET " + COL_COMPLETED +
                " = '" +newCompletion + "' WHERE " + COL_COMPLETED + " = '" + oldCompletion + "'";

        db.execSQL(query);
    }

}