package com.guide.db;

import java.util.ArrayList;

import com.guide.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PlaceAdapter extends BaseAdapter {

	
	
	private static class ViewHolder
	{
		TextView title;
		TextView description;
		TextView category;
		TextView rating;
	}
 
	private ArrayList<Place> data;
	private LayoutInflater inflater = null;
	
	public PlaceAdapter(Context c, ArrayList<Place> data) {
		this.data = data;
		inflater = LayoutInflater.from(c);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder; 
 
		if (convertView == null)
		{
			convertView = inflater.inflate(R.layout.activity_main_menu_places_row, null);
 
			holder = new ViewHolder();
 
			holder.title = (TextView) convertView.findViewById(R.id.title_categories_row);
			holder.description = (TextView) convertView.findViewById(R.id.description_categories_row);
			holder.category = (TextView) convertView.findViewById(R.id.category_categories_row);
			holder.rating = (TextView) convertView.findViewById(R.id.rating_categories_row);
			
			convertView.setTag(holder);
 
		} else{
			holder = (ViewHolder) convertView.getTag();
		}
 
		// Setting all values in listview
		holder.title.setText(data.get(position).getName());
		holder.description.setText("Description: " + data.get(position).getDescription());
		holder.rating.setText("Rating: " + String.valueOf(data.get(position).getRating()));
		holder.category.setText("Category: " + data.get(position).getCategory());
		
		return convertView;
	}

}
