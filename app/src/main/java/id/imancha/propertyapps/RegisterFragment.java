package id.imancha.propertyapps;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.propertylibrary.user.User;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

	private OnFragmentInteractionListener mListener;

	public RegisterFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @return A new instance of fragment RegisterFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static RegisterFragment newInstance() {
		RegisterFragment fragment = new RegisterFragment();

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
		return inflater.inflate(R.layout.fragment_register, container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		super.getActivity().setTitle("Register");
	}

	@Override
	public void onViewCreated(final View view, @Nullable Bundle
			                                             savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		final EditText name = (EditText) view.findViewById(R.id.register_name);
		final EditText email = (EditText) view.findViewById(R.id.register_email);
		final EditText phone = (EditText) view.findViewById(R.id.register_phone);
		final EditText password = (EditText) view.findViewById(R.id
				                                                       .register_password);
		Button submit = (Button) view.findViewById(R.id.register_submit);

		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!Validation.isValid(name.getText()))
					Toast.makeText(getActivity(), "Nama harus diisi.", Toast
							                                                   .LENGTH_SHORT).show();
				else if (!Validation.isValidName(name.getText()))
					Toast.makeText(getActivity(), "Nama tidak valid.", Toast
							                                                   .LENGTH_SHORT).show();
				else if (!Validation.isValid(email.getText()))
					Toast.makeText(getActivity(), "Email harus diisi.", Toast
							                                                    .LENGTH_SHORT).show();
				else if (!Validation.isValidEmail(email.getText()))
					Toast.makeText(getActivity(), "Email tidak valid.", Toast
							                                                    .LENGTH_SHORT).show();
				else if (!Validation.isValid(phone.getText()))
					Toast.makeText(getActivity(), "Telepon harus diisi.", Toast
							                                                    .LENGTH_SHORT).show();
				else if (!Validation.isValidPhone(phone.getText()))
					Toast.makeText(getActivity(), "Telepon tidak valid.", Toast
							                                                    .LENGTH_SHORT).show();
				else if (!Validation.isValid(password.getText()))
					Toast.makeText(getActivity(), "Password harus diisi.", Toast
							                                                    .LENGTH_SHORT).show();
				else if (!Validation.isValidPassword(password.getText()))
					Toast.makeText(getActivity(), "Password minimum 6 karakter.", Toast
							                                                    .LENGTH_SHORT).show();
				else {
					new Thread(new Runnable() {
						@Override
						public void run() {
							User user = new User();

							user.create(new HashMap<String, Object>() {{
								put("name", name.getText().toString());
								put("email", email.getText().toString());
								put("phone", phone.getText().toString());
								put("password", password.getText().toString());
							}});

							view.post(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(getActivity(), "Register berhasil. Silahkan " +
									                              "Login.", Toast.LENGTH_LONG).show();
									getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
									getFragmentManager()
											.beginTransaction()
											.replace(R.id.frame, LoginFragment.newInstance())
											.commit();
								}
							});
						}
					}).start();
				}
			}
		});
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
