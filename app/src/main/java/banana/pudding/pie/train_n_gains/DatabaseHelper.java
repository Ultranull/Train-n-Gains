package banana.pudding.pie.train_n_gains;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseHelper extends SQLiteOpenHelper {

    // --Commented out by Inspection (4/30/2019 4:40 PM):public static final String DATABASE_PATH = "/data/data/banana.pudding.pie.train_n_gains/databases/";
    private static final String DATABASE_NAME = "WORKOUTS";
    private static final String TABLE_NAME = "B";
    private static final String ID = "ID";
    private static final String COL_NAME = "NAME";
    private static final String COL_DESC = "DESCRIPTION";
    private static final String COL_INST = "INSTRUCTION";
    private static final String COL_TYPE = "TYPE";
    private static final String COL_DAY = "DAY";
    private static final String COL_MONTH = "MONTH";
    private static final String COL_COMPLETED = "COMPLETED";



     DatabaseHelper(Context context){super(context, DATABASE_NAME, null, 1);}

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



     boolean addData(WorkoutAction woa, String day, String month, String completed) {
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
        return result!=-1;
    }

// --Commented out by Inspection START (4/30/2019 4:40 PM):
//    public void moveRowUp(int row){
//        int n=row,m=row-1;
//        SQLiteDatabase db = this.getReadableDatabase();
//        db.execSQL("UPDATE "+TABLE_NAME+" SET "+ID+" = "+ID+"-1 WHERE "+ID+" > "+m+" AND "+ID+" <= "+n);
//        db.execSQL("UPDATE "+TABLE_NAME+" SET "+ID+" = m WHERE "+ID+" = n");
//    }
// --Commented out by Inspection STOP (4/30/2019 4:40 PM)

     void clearTables(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from B");
    }

     Cursor getListContents() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }



     void updateCompletion(String newCompletion, String oldCompletion) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME + " SET " + COL_COMPLETED +
                " = '" +newCompletion + "' WHERE " + COL_COMPLETED + " = '" + oldCompletion + "'";

        db.execSQL(query);
    }


     boolean deleteData(String data)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "COMPLETED = ?", new String[] {data});

        return result!=-1;

    }

}