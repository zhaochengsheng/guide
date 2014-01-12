package com.guide.db;

import java.util.ArrayList;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PlacesDataSource {
	
	// Database fields
	private SQLiteDatabase database;
	private PlacesSQLiteHelper dbHelper;
	private String[] allColumns = { 
			
			PlacesSQLiteHelper.COLUMN_ID,
			PlacesSQLiteHelper.COLUMN_NAME,
			PlacesSQLiteHelper.COLUMN_SCORE 
	};
	
	public PlacesDataSource(Context context){
		dbHelper = new PlacesSQLiteHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		dbHelper.close();
	}
}
