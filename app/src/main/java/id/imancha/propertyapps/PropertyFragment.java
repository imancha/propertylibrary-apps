package id.imancha.propertyapps;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.propertylibrary.property.Property;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PropertyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PropertyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PropertyFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private Boolean mParam1;
	private Boolean mParam2;

	private OnFragmentInteractionListener mListener;

	public PropertyFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment PropertyFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static PropertyFragment newInstance(Boolean param1, Boolean param2) {
		PropertyFragment fragment = new PropertyFragment();
		Bundle args = new Bundle();
		args.putBoolean(ARG_PARAM1, param1);
		args.putBoolean(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			mParam1 = getArguments().getBoolean(ARG_PARAM1);
			mParam2 = getArguments().getBoolean(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_property, container, false);
	}

	@Override
	public void onViewCreated(final View view, @Nullable Bundle
			                                           savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		final RecyclerView VProperty = (RecyclerView) view.findViewById(R.id
				                                                                .property);

		VProperty.setLayoutManager(new LinearLayoutManager(view.getContext()));
		VProperty.setHasFixedSize(true);

		new Thread(new Runnable() {
			@Override
			public void run() {
				Property property = new Property();

				if (mParam1 && mParam2) {
					final PropertyAdapter docs = new PropertyAdapter(property.find());
					view.post(new Runnable() {
						@Override
						public void run() {
							VProperty.setAdapter(docs);
						}
					});
				} else {
					final PropertyAdapter docs = new PropertyAdapter(property.find(new
							                                                               HashMap<String, Object>() {{
								                                                               put("type", mParam1 ? "sell" : "rent");

							                                                               }}));
					view.post(new Runnable() {
						@Override
						public void run() {
							VProperty.setAdapter(docs);
						}
					});
				}
			}
		}).start();
	}

	@Override
	public void onResume() {
		super.onResume();
		super.getActivity().setTitle("Hasil");
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
