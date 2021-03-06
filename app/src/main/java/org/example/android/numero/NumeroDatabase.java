package org.example.android.numero;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by harshgupta on 10/09/16.
 */
public class NumeroDatabase extends SQLiteOpenHelper {
    private static final String TAG = NumeroDatabase.class.getSimpleName();
    private static final String DATABASE_NAME="numeros.db";
    private static final int DATABASE_VERSION = 3;
    private final Context mContext;

    interface Tables{
        String NUMEROS = "numeros";
    }

    public NumeroDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE "+ Tables.NUMEROS + " ("
                +BaseColumns._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
        + NumeroContract.NumeroColumns.NUMERO_CATEGORY+" TEXT NOT NULL,"
        + NumeroContract.NumeroColumns.NUMERO_DESCRIPTION+ " TEXT NOT NULL,"
        + NumeroContract.NumeroColumns.NUMERO_COUNT+ " INTEGER NOT NULL,"
        + NumeroContract.NumeroColumns.NUMERO_SPECIAL+ " INTEGER DEFAULT 0,"
        + NumeroContract.NumeroColumns.NUMERO_CREATEDDATE+ " INTEGER DEFAULT 0,"
        + NumeroContract.NumeroColumns.NUMERO_UPDATEDDATE+ " INTEGER DEFAULT 0,"
        + NumeroContract.NumeroColumns.NUMERO_DATE+ " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        int version=oldVersion;
//        if(version != DATABASE_VERSION){
//            Log.d("Hello","whatcontentprovider_xupgrade");
//            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ Tables.NUMEROS);
//            onCreate(sqLiteDatabase);
//        }
    }

    public static void deleteDatabase(Context context){
        context.deleteDatabase(DATABASE_NAME);
    }
}
