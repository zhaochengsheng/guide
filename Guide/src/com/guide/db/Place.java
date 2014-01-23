package com.guide.db;

import java.util.List;

public class Place implements Comparable<Place>{
	private long local_id;
	private String _id;
	private String name;
	private String description;
	private String category;
	private float rating;
	private LocationNode location;
	private List<Product> products;
	
	public long getLocal_id() {
		return local_id;
	}
	public void setLocal_id(long local_id) {
		this.local_id = local_id;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
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
	
	public LocationNode getLocation() {
		return location;
	}
	public void setLocation(LocationNode location) {
		this.location = location;
	}
	public void setRating(float f) {
		this.rating = f;
	}
	
	public static class LocationNode{
		private String type;
		private List<Float> coordinates;
		
		public String getType(){
			return type;
		}
		
		public void setType(String type){
			this.type = type;
		}

		public List<Float> getCoordinates() {
			return coordinates;
		}

		public void setCoordinates(List<Float> coordinates) {
			this.coordinates = coordinates;
		}
		
	}
	
	public static class Product{
		private String name;
		private String description;
		private float price;
		private String category;
		private String name_place;
		
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getName_place() {
			return name_place;
		}
		public void setName_place(String name_place) {
			this.name_place = name_place;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public float getPrice() {
			return price;
		}
		public void setPrice(float price) {
			this.price = price;
		}
	}
	
	@Override
	public int compareTo(Place o) {
		if (this.getRating() > o.getRating()) {
			return -1;
		}
		else if (this.getRating() < o.getRating()) {
			return 1;
		}
		else {
			return 0;
		}
	}
}
