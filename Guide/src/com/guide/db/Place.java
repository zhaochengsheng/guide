package com.guide.db;

public class Place {
	private String _id;
	private String name;
	private float rating;
	public String getId() {
		return _id;
	}
	public void setId(String _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(Float rating) {
		this.rating = rating;
	}
	
	public static class location{
		private String type;
		private float coordinates[];
		
		public String getType(){
			return type;
		}
		
		public void setType(String type){
			this.type = type;
		}
		
		public float[] getCoordinates(){
			return coordinates;
		}
		
		public void setCoordinates(float coordinates[]){
			this.coordinates = coordinates;
		}
		
	}
}
