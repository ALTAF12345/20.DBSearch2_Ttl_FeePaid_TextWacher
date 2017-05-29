package org.sawhers.altafhussain.dbsearch2_ttl_feepaid_textwacher.DATABASE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.camera2.params.StreamConfigurationMap;

import org.sawhers.altafhussain.dbsearch2_ttl_feepaid_textwacher.MODEL.Student;

import java.util.ArrayList;

/**
 * Created by ALTAFHUSSAIN on 1/8/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="mystudent.db";
    private static final int DB_VERSION=1;
    private static final String KEY_TABLE_STUDENT="tbl_new_coustamer";
    private static final String KEY_ID="id";
    private static final String KEY_NAME="name";
    private static final String KEY_Course="course";
    private static final String KEY_Total_Fee="totalfee";
    private static final String KEY_FeePaid="feepaid";

//****************************************************************
    private static final String CREATE_TABLE= "CREATE TABLE "+ KEY_TABLE_STUDENT + " ( "

                                     +KEY_ID + " Integer Primary Key Autoincrement, "
                                     +KEY_NAME + " Text NOT NULL,"
                                     +KEY_Course + " Text,"
                                     +KEY_Total_Fee + " Integer,"
                                     +KEY_FeePaid + " Integer"
                                     +" ); " ;


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+CREATE_TABLE);
        onCreate(db);
    }


    public long saveStudent(Student student) {
        long result=-1;
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_NAME,student.getName());
        values.put(KEY_Course,student.getCourse());
        values.put(KEY_Total_Fee,student.getTotalFee());
        values.put(KEY_FeePaid,student.getFeePaid());

        result=db.insert(KEY_TABLE_STUDENT ,null,values);


        return result;
    }

    public ArrayList<Student> getAllStudent() {
        ArrayList<Student> studentArrayList=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from " + KEY_TABLE_STUDENT ,null);
        if (cursor.moveToFirst()){
            do {
                int id=cursor.getInt(cursor.getColumnIndex(KEY_ID));
                String name=cursor.getString(cursor.getColumnIndex(KEY_NAME));
                String course=cursor.getString(cursor.getColumnIndex(KEY_Course));
                int totalFee=cursor.getInt(cursor.getColumnIndex(KEY_Total_Fee));
                int feePaid=cursor.getInt(cursor.getColumnIndex(KEY_FeePaid));
                Student student=new Student(id,name,course,totalFee,feePaid);
                studentArrayList.add(student);
            }while (cursor.moveToNext());

        }

        return studentArrayList;
    }

    public int deleteStudent(String s) {
        int result=0;

        try {
            SQLiteDatabase db=this.getWritableDatabase();
            result=db.delete(KEY_TABLE_STUDENT , KEY_ID + "=?",new String[]{s});
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int upDateStudent(Student student) {
        int result=0;
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_NAME,student.getName());
        values.put(KEY_Course,student.getCourse());
        values.put(KEY_Total_Fee,student.getTotalFee());
        values.put(KEY_FeePaid,student.getFeePaid());
        result=sqLiteDatabase.update(KEY_TABLE_STUDENT,values ,KEY_ID + "=?" ,new String[]{String.valueOf(student.getId())});
        return result;
    }
    public ArrayList<Student> selectStudentPerCriteria(String field, String value) {
        ArrayList<Student> studentArrayList = new ArrayList<>();

        Cursor c;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        //********************************************************************
        if (field.equals("Fee_Paid")) {
             c = sqLiteDatabase.rawQuery("select * from " + KEY_TABLE_STUDENT + " where " + KEY_Total_Fee + "= " + KEY_FeePaid, null);
        } else if (field.equals("Fee_Not_Paid")) {
             c = sqLiteDatabase.rawQuery("select * from " + KEY_TABLE_STUDENT + " where " + KEY_Total_Fee + "> " + KEY_FeePaid, null);
        } else {
            //*******************************************************************
             c = sqLiteDatabase.rawQuery("select * from " + KEY_TABLE_STUDENT + " where " + field + " LIKE ?", new String[]{value});
        }
            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(c.getColumnIndex(KEY_ID));
                    String name = c.getString(c.getColumnIndex(KEY_NAME));
                    String course = c.getString(c.getColumnIndex(KEY_Course));
                    int totalFee = c.getInt(c.getColumnIndex(KEY_Total_Fee));
                    int feePaid = c.getInt(c.getColumnIndex(KEY_FeePaid));
                    Student student = new Student(id, name, course, totalFee, feePaid);
                    //Student(int id, String name, String course, int totalFee, int feePaid)
                    studentArrayList.add(student);
                } while (c.moveToNext());

            }
            return studentArrayList;

        }
 //*********************************Start***TextWacher***********************
 public ArrayList<Student> TextWacherftn(String field, String value) {
     ArrayList<Student> studentArrayList = new ArrayList<>();
     Cursor c;
     SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
     c = sqLiteDatabase.rawQuery("select * from " + KEY_TABLE_STUDENT + " where " + field + " LIKE ? "  , new String[]{value+"%"});

     if (c.moveToFirst()) {
         do {
             int id = c.getInt(c.getColumnIndex(KEY_ID));
             String name = c.getString(c.getColumnIndex(KEY_NAME));
             String course = c.getString(c.getColumnIndex(KEY_Course));
             int totalFee = c.getInt(c.getColumnIndex(KEY_Total_Fee));
             int feePaid = c.getInt(c.getColumnIndex(KEY_FeePaid));
             Student student = new Student(id, name, course, totalFee, feePaid);
             //Student(int id, String name, String course, int totalFee, int feePaid)
             studentArrayList.add(student);
         } while (c.moveToNext());

     }
     return studentArrayList;
 }
 //*********************************end textwacher***************************

}

