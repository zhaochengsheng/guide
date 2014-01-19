package com.guide.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlacesSQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_PLACES = "places";
	public static final String COLUMN_ID = "local_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_SCORE = "rating";
	public static final String COLUMN_REMOTE_ID = "_id";
	public static final String COLUMN_LATITUDE = "latitude";
	public static final String COLUMN_LONGITUDE = "longitude";
	
	private static final String DATABASE_NAME = "places.db";
	private static final int DATABASE_VERSION = 1;
	
	String sqlCreate = "CREATE TABLE " + TABLE_PLACES  + "(" +
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_NAME + " TEXT NOT NULL, " +
			COLUMN_SCORE + " FLOAT, " +
			COLUMN_REMOTE_ID + " TEXT NOT NULL," +
			COLUMN_LATITUDE + " FLOAT NOT NULL," +
			COLUMN_LONGITUDE + " FLOAT NOT NULL" +
			");";
	
	public PlacesSQLiteHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(sqlCreate);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	    //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
		
        // Se crea la nueva versión de la tabla
        onCreate(db);
        
	}

}
