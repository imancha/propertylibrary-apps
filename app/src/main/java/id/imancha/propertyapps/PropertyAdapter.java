package id.imancha.propertyapps;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter
		                                                          .PropertyViewHolder> {
	List<Map<String, Object>> properties;

	public static class PropertyViewHolder extends RecyclerView.ViewHolder {
		CardView propertyCard;
		TextView propertyAddress;
		TextView propertyDescription;
		TextView propertyPrice;

		public PropertyViewHolder(View itemView) {
			super(itemView);

			propertyCard = (CardView) itemView.findViewById(R.id.property_card);
			propertyAddress = (TextView) itemView.findViewById(R.id
					                                                   .property_address);
			propertyDescription = (TextView) itemView.findViewById(R.id
					                                                       .property_description);
			propertyPrice = (TextView) itemView.findViewById(R.id.property_price);
		}
	}

	PropertyAdapter(List<Map<String, Object>> properties) {
		this.properties = properties;
	}

	@Override
	public PropertyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
				                                                             .fragment_property_list, parent, false);
		PropertyViewHolder holder = new PropertyViewHolder(view);

		return holder;
	}

	@Override
	public void onBindViewHolder(final PropertyViewHolder holder, final int
			                                                                position) {
		holder.propertyAddress
				.setText(properties.get(position).get("address").toString());
		holder.propertyDescription
				.setText(properties.get(position).get("description").toString());
		holder.propertyPrice
				.setText(properties.get(position).get("price").toString());
		holder.propertyCard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AppCompatActivity activity = (AppCompatActivity) v.getContext();

				activity.getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.frame, PropertyViewFragment.newInstance
								                                          (properties.get
										                                                      (position).get("_id").toString()))
						.addToBackStack(null)
						.commit();
			}
		});
	}

	@Override
	public int getItemCount() {
		return properties.size();
	}
}
