package id.imancha.propertyapps;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.propertylibrary.property.Property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PropertySellFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PropertySellFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PropertySellFragment extends Fragment {

	private OnFragmentInteractionListener mListener;

	public PropertySellFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @return A new instance of fragment RegisterFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static PropertySellFragment newInstance() {
		PropertySellFragment fragment = new PropertySellFragment();

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_property_sell, container, false);
	}

	@Override
	public void onViewCreated(final View view, @Nullable Bundle
			                                             savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		final EditText title;
		final EditText description;
		final EditText address;
		final EditText price;
		final Spinner options;
		final Button submit;
		final ArrayAdapter<String> adapter;

		final Session session = new Session(getActivity());

		title = (EditText) view.findViewById(R.id.sell_title);
		description = (EditText) view.findViewById(R.id.sell_description);
		address = (EditText) view.findViewById(R.id.sell_address);
		price = (EditText) view.findViewById(R.id.sell_price);
		options = (Spinner) view.findViewById(R.id.sell_options);
		submit = (Button) view.findViewById(R.id.sell_submit);

		List<String> data = new ArrayList<>();
		data.add("Jual");
		data.add("Sewa");
		adapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, data);
		adapter.setDropDownViewResource(
				android.R.layout.simple_spinner_dropdown_item);
		options.setAdapter(adapter);
		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!Validation.isValid(title.getText()))
					Toast.makeText(getActivity(), "Judul harus diisi.", Toast.LENGTH_SHORT).show();
				else if (!Validation.isValid(address.getText()))
					Toast.makeText(getActivity(), "Alamat harus diisi.", Toast.LENGTH_SHORT).show();
				else if (!Validation.isValid(price.getText()))
					Toast.makeText(getActivity(), "Harga harus diisi.", Toast.LENGTH_SHORT).show();
				else if (!Validation.isValidPrice(price.getText()))
					Toast.makeText(getActivity(), "Harga tidak valid.", Toast.LENGTH_SHORT).show();
				else {
					new Thread(new Runnable() {
						@Override
						public void run() {
							Property property = new Property();
							property.create(new HashMap<String, Object>() {{
								put("title", title.getText().toString());
								put("description", description.getText().toString());
								put("address", address.getText().toString());
								put("price", price.getText().toString());
								put("email", session.get("email"));
								put("type", options.getSelectedItem().toString() == "Jual" ?
										            "sell" : "rent");
							}});
							view.post(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(getActivity(), "Tambah properti berhasil.",
											Toast.LENGTH_SHORT).show();
								}
							});
						}
					}).start();
				}
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		super.getActivity().setTitle("Tambah");
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated
	 * to the activity and potentially other fragments contained in that
	 * activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating
	 * .html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		void onFragmentInteraction(Uri uri);
	}
}
