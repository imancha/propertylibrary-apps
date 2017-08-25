package id.imancha.propertyapps;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.propertylibrary.user.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

	private OnFragmentInteractionListener mListener;

	public LoginFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @return A new instance of fragment LoginFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static LoginFragment newInstance() {
		LoginFragment fragment = new LoginFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);

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
		return inflater.inflate(R.layout.fragment_login, container, false);
	}

	@Override
	public void onViewCreated(final View view, @Nullable Bundle
			                                           savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		final TextView email;
		final TextView password;
		final Button login;
		final TextView register;

		email = (TextView) view.findViewById(R.id.login_email);
		password = (TextView) view.findViewById(R.id.login_password);
		register = (TextView) view.findViewById(R.id.login_register);
		login = (Button) view.findViewById(R.id.login_submit);

		register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getFragmentManager()
						.beginTransaction()
						.replace(R.id.frame, RegisterFragment.newInstance())
						.addToBackStack(null)
						.commit();
			}
		});

		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!Validation.isValid(email.getText()))
					Toast.makeText(getActivity(), "Email harus diisi.", Toast.LENGTH_SHORT).show();
				else if (!Validation.isValidEmail(email.getText()))
					Toast.makeText(getActivity(), "Email tidak valid.", Toast.LENGTH_SHORT).show();
				else if (!Validation.isValid(password.getText()))
					Toast.makeText(getActivity(), "Password harus diisi.", Toast.LENGTH_SHORT).show();
				else {
					new Thread(new Runnable() {
						@Override
						public void run() {
							User user = new User();
							List<Map<String, Object>> result = user.find(new HashMap<String,
									                                                        Object>() {{
								put("email", email.getText().toString());
								put("password", password.getText().toString());
							}});

							if (result.size() > 0) {
								Session session = new Session(getActivity());
								session.create(
										result.get(0).get("_id").toString(),
										result.get(0).get("name").toString(),
										result.get(0).get("email").toString()
								);
								getFragmentManager()
										.beginTransaction()
										.replace(R.id.frame, HomeFragment.newInstance())
										.commit();
							} else {
								view.post(new Runnable() {
									@Override
									public void run() {
										Toast.makeText(getActivity(), "Email / Password salah.",
												Toast.LENGTH_SHORT).show();
									}
								});
							}
						}
					}).start();
				}
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		super.getActivity().setTitle("Login");
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
