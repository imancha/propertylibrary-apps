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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.propertylibrary.property.Property;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PropertySellFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PropertySellFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalculatorFragment extends Fragment {

	private OnFragmentInteractionListener mListener;

	public CalculatorFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @return A new instance of fragment RegisterFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static CalculatorFragment newInstance() {
		CalculatorFragment fragment = new CalculatorFragment();

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
		return inflater.inflate(R.layout.fragment_calculator, container, false);
	}

	@Override
	public void onViewCreated(final View view, @Nullable Bundle
			                                           savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		final EditText harga, bunga, lama;
		final TextView hasil;
		final Button submit;

		final Session session = new Session(getActivity());

		harga = (EditText) view.findViewById(R.id.calc_harga);
		bunga = (EditText) view.findViewById(R.id.calc_bunga);
		lama = (EditText) view.findViewById(R.id.calc_lama);
		hasil = (TextView) view.findViewById(R.id.calc_hasil);
		submit = (Button) view.findViewById(R.id.calc_hitung);

		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!Validation.isValid(harga.getText()))
					Toast.makeText(getActivity(), "Harga harus diisi.", Toast
							                                                    .LENGTH_SHORT).show();
				else if (!Validation.isValid(bunga.getText()))
					Toast.makeText(getActivity(), "Bunga harus diisi.", Toast
							                                                     .LENGTH_SHORT).show();
				else if (!Validation.isValid(lama.getText()))
					Toast.makeText(getActivity(), "Lama harus diisi.", Toast
							                                                    .LENGTH_SHORT).show();
				else {
					Property property = new Property();
					Integer result = (int) property.calcKPR(
							Double.parseDouble(harga.getText().toString()),
							Double.parseDouble(bunga.getText().toString()),
							Integer.parseInt(lama.getText().toString())
					);

					hasil.setText(result.toString());
				}
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		super.getActivity().setTitle("Kalkulator");
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
