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

	private Context context;
	
	// Keep all Images in array
	public Integer[] images = {
	        R.drawable.ic_img_restaurante, R.drawable.ic_img_farmacia, R.drawable.ic_img_banco,
	        R.drawable.ic_img_supermercado, R.drawable.ic_img_ropa, R.drawable.ic_img_cine
	};
	
	private static class ViewHolder
	{
		TextView title;
		TextView description;
		TextView category;
		TextView rating;
		TextView number_of_products;
		ImageView image;
	}
 
	private ArrayList<Place> data;
	private LayoutInflater inflater = null;
	
	public PlaceAdapter(Context c, ArrayList<Place> data) {
		this.data = data;
		inflater = LayoutInflater.from(c);
		context = c;
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
 
			holder.image = (ImageView) convertView.findViewById(R.id.image_categories_row);
			holder.title = (TextView) convertView.findViewById(R.id.title_categories_row);
			holder.description = (TextView) convertView.findViewById(R.id.description_categories_row);
			holder.category = (TextView) convertView.findViewById(R.id.category_categories_row);
			holder.rating = (TextView) convertView.findViewById(R.id.rating_categories_row);
			holder.number_of_products = (TextView) convertView.findViewById(R.id.products_categories_row);
			
			convertView.setTag(holder);
 
		} else{
			holder = (ViewHolder) convertView.getTag();
		}
 
		// Setting all values in listview
		int imageResource = images[0];
		if (data.get(position).getCategory().equals("restaurant")){
			imageResource = images[0];
		}else if(data.get(position).getCategory().equals("bar")){
			imageResource = images[0];
		}else if(data.get(position).getCategory().equals("pharmacy")){
				imageResource = images[1];
		}else if(data.get(position).getCategory().equals("bank")){
				imageResource = images[2];
		}else if(data.get(position).getCategory().equals("grocery_or_supermarket")){
				imageResource = images[3];
		}else if(data.get(position).getCategory().equals("clothing_store")){
				imageResource = images[4];
		}else if(data.get(position).getCategory().equals("movie_theater")){
				imageResource = images[5];
		}
		
		if(data.get(position).getProducts() != null){
			holder.number_of_products.setText(String.valueOf(data.get(position).getProducts().size()));
		}
		else{
			holder.number_of_products.setText("0");
		}
				
		holder.image.setImageResource(imageResource);
		holder.title.setText(data.get(position).getName());
		holder.description.setText( data.get(position).getDescription());
		holder.rating.setText(String.valueOf(data.get(position).getRating()));
		holder.category.setText(data.get(position).getCategory());
		
		return convertView;
	}

}
