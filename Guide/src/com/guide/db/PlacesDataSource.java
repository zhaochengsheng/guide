package com.guide.db;

import java.util.ArrayList;
import java.util.List;

import com.guide.db.Place.LocationNode;

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
			PlacesSQLiteHelper.COLUMN_SCORE,
			PlacesSQLiteHelper.COLUMN_REMOTE_ID,
			PlacesSQLiteHelper.COLUMN_LATITUDE,
			PlacesSQLiteHelper.COLUMN_LONGITUDE,
			PlacesSQLiteHelper.COLUMN_DESCRIPTION,
			PlacesSQLiteHelper.COLUMN_CATEGORY
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
	
	public Place createPlace(String name, float rating, String remote_id, float latitude, float longitude, String description, String category){
		ContentValues values = new ContentValues();
		values.put(PlacesSQLiteHelper.COLUMN_NAME, name);
		values.put(PlacesSQLiteHelper.COLUMN_SCORE, rating);
		values.put(PlacesSQLiteHelper.COLUMN_REMOTE_ID, remote_id);
		values.put(PlacesSQLiteHelper.COLUMN_LATITUDE, latitude);
		values.put(PlacesSQLiteHelper.COLUMN_LONGITUDE, longitude);
		values.put(PlacesSQLiteHelper.COLUMN_LONGITUDE, description);
		values.put(PlacesSQLiteHelper.COLUMN_LONGITUDE, category);
		
		long insertId = database.insert(
				PlacesSQLiteHelper.TABLE_PLACES, 
				null, values);
		
		Cursor cursor = database.query(
				PlacesSQLiteHelper.TABLE_PLACES, allColumns,
				PlacesSQLiteHelper.COLUMN_ID + " = " + insertId, 
				null, null, null, null, null);
		cursor.moveToFirst();
		Place scoreObject = cursorToPlace(cursor);
		
		return scoreObject;
	}
	
	public ArrayList<Place> getAllPlaces(){
		ArrayList<Place> places = new ArrayList<Place>();
		
		Cursor cursor = database.query(
				PlacesSQLiteHelper.TABLE_PLACES, allColumns, 
				null, null, null, null, PlacesSQLiteHelper.COLUMN_SCORE + " DESC");
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			Place place = cursorToPlace(cursor);
			places.add(place);
			cursor.moveToNext();
		}
		cursor.close();
		
		return places;
	}
	
	public void deleteAll(){
		database.delete(PlacesSQLiteHelper.TABLE_PLACES, null, null);
	}
	
	private Place cursorToPlace(Cursor cursor){
		Place place = new Place();
		//place.setLocal_id(cursor.getLong(0));
		place.setName(cursor.getString(1));
		place.setRating(cursor.getFloat(2));
		place.set_id(cursor.getString(3));
		List<Float> coordinates = new ArrayList<Float>();
		coordinates.add(cursor.getFloat(4)); 
		coordinates.add(cursor.getFloat(5)); 
		
		LocationNode location = new LocationNode();
		location.setCoordinates(coordinates);
		location.setType("Point");
		
		place.setLocation(location);
		
		place.setDescription(cursor.getString(6));
		place.setCategory(cursor.getString(7));
		return place;
	}
	
}
