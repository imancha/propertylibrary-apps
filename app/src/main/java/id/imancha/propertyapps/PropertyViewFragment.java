package id.imancha.propertyapps;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.propertylibrary.property.Favorite;
import com.propertylibrary.property.Property;
import com.propertylibrary.user.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PropertyViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PropertyViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PropertyViewFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ID = "_id";
	private Map<String, Object> data = new HashMap<>();
	private Map<String, Object> agent = new HashMap<>();

	// TODO: Rename and change types of parameters
	private String mID;
	private OnFragmentInteractionListener mListener;

	private Session session;

	public PropertyViewFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @return A new instance of fragment PropertyViewFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static PropertyViewFragment newInstance(String _id) {
		PropertyViewFragment fragment = new PropertyViewFragment();
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
		session = new Session(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_property_view, container,
				false);
		setHasOptionsMenu(true);
		return view;
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.property_view_favorite).setVisible(session.isValid());
		super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu; this adds items to the action bar if it is present.
		inflater.inflate(R.menu.property_view, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.property_view_info) {
			View view = getActivity().findViewById(R.id.property_view_info);
			PopupMenu popup = new PopupMenu(getActivity(), view);
			popup.getMenuInflater().inflate(R.menu.property_view_info, popup.getMenu
					                                                                 ());
			popup.show();
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener
					                                     () {
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					User user = new User();
					if (item.getItemId() == R.id.action_call) {
						user.call(getActivity(), agent.get("phone").toString());
					} else if (item.getItemId() == R.id.action_email) {
						user.email(getActivity(), agent.get("email").toString(), data.get
								                                                              ("title")
								                                                         .toString(),
								data.get("description").toString());
					}
					return false;
				}
			});
			return true;
		} else if (id == R.id.property_view_favorite) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					Favorite favorite = new Favorite();
					List<Map<String, Object>> favorites = favorite.find(session.get
							                                                            ("_id"));
					boolean exist = false;
					for (int i = 0; i < favorites.size(); i++) {
						if (favorites.get(i).get("_id").equals(data.get("_id"))) {
							exist = true;
							break;
						}
					}
					try {
						final String message;
						if (exist) {
							favorite.remove(data.get("_id").toString(), session.get("_id"));
							message = "Hapus dari favorit berhasil.";
						} else {
							favorite.create(data.get("_id").toString(), session.get("_id"));
							message = "Tambah ke favorit berhasil.";
						}
						getView().post(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT)
										.show();
							}
						});
					} catch (final IllegalArgumentException e) {
						getView().post(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(getActivity(), e.getMessage(),
										Toast.LENGTH_SHORT).show();
							}
						});
					}
				}
			}).start();
		} else if (id == R.id.property_view_share) {
			Property property = new Property();
			property.share(getActivity(), data.get("description").toString(), new
					                                                                  String[]{"com.Slack"});
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onViewCreated(final View view, @Nullable Bundle
			                                           savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		final TextView title;
		final TextView description;
		final TextView price;
		final TextView contact;

		title = (TextView) view.findViewById(R.id.property_view_title);
		description = (TextView) view.findViewById(R.id.property_view_description);
		price = (TextView) view.findViewById(R.id.property_view_harga);
		contact = (TextView) view.findViewById(R.id.property_view_agent);

		new Thread(new Runnable() {
			@Override
			public void run() {
				Property property = new Property();
				User user = new User();

				data = property.findOne(mID);

				if (!data.isEmpty()) {
					List<Map<String, Object>> agents;
					agents = user.find(new HashMap<String, Object>(){{
						put("email", data.get("email").toString());
					}});
					if (agents.size() > 0) {
						agent = agents.get(0);
						view.post(new Runnable() {
							@Override
							public void run() {
								contact.setText(
										agent.get("email").toString() + " - " + agent.get("phone").toString());
							}
						});
					}
					view.post(new Runnable() {
						@Override
						public void run() {
							title.setText(data.get("title").toString());
							description.setText(data.get("description").toString() + "\n\n" +
									                    data.get("address").toString());
							price.setText(data.get("price").toString());
						}
					});
				}
			}
		}).start();
	}

	@Override
	public void onResume() {
		super.onResume();
		super.getActivity().setTitle("Detail");
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
