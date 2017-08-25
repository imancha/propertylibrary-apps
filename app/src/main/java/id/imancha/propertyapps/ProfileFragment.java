package id.imancha.propertyapps;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.propertylibrary.user.User;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
	private static final String ID = "_id";
	private String mID;
	private OnFragmentInteractionListener mListener;

	public ProfileFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @return A new instance of fragment RegisterFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ProfileFragment newInstance(String _id) {
		ProfileFragment fragment = new ProfileFragment();
		Bundle args = new Bundle();
		args.putString(ID, _id);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mID = getArguments().getString(ID);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_profile, container, false);
	}

	@Override
	public void onViewCreated(final View view, @Nullable Bundle
			                                             savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		final TextView name;
		final TextView email;
		final TextView phone;

		name = (TextView) view.findViewById(R.id.profile_name);
		email = (TextView) view.findViewById(R.id.profile_email);
		phone = (TextView) view.findViewById(R.id.profile_phone);

		new Thread(new Runnable() {
			@Override
			public void run() {
				User user = new User();
				final Map<String, Object> result = user.findOne(mID);

				if (!result.isEmpty()) {
					view.post(new Runnable() {
						@Override
						public void run() {
							name.setText(result.get("name").toString());
							email.setText(result.get("email").toString());
							phone.setText(result.get("phone").toString());
						}
					});
				}
			}
		}).start();
	}

	@Override
	public void onResume() {
		super.onResume();
		super.getActivity().setTitle("Profil");
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
