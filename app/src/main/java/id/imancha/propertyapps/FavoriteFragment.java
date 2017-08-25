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

import com.propertylibrary.property.Favorite;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavoriteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {
	private OnFragmentInteractionListener mListener;

	public FavoriteFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @return A new instance of fragment FavoriteFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static FavoriteFragment newInstance() {
		FavoriteFragment fragment = new FavoriteFragment();

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
		return inflater.inflate(R.layout.fragment_favorite, container, false);
	}

	@Override
	public void onViewCreated(final View view, @Nullable Bundle
			                                           savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		final RecyclerView VProperty = (RecyclerView) view.findViewById(R.id
				                                                                .favorit);

		VProperty.setLayoutManager(new LinearLayoutManager(view.getContext()));
		VProperty.setHasFixedSize(true);

		final Session session = new Session(getActivity());

		new Thread(new Runnable() {
			@Override
			public void run() {
				Favorite favorite = new Favorite();
				final PropertyAdapter docs = new PropertyAdapter(favorite.find
						                                                          (session.get("_id")));
				view.post(new Runnable() {
					@Override
					public void run() {
						VProperty.setAdapter(docs);
					}
				});
			}
		}).start();
	}

	@Override
	public void onResume() {
		super.onResume();
		super.getActivity().setTitle("Favorit");
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
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
