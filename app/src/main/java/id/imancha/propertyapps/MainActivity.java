package id.imancha.propertyapps;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.propertylibrary.base.Database;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	private TextView name, email;
	private Session session;
	private NavigationView navigationView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				                                                              this,
				                                                              drawer,
				                                                              toolbar,
				                                                              R.string
						                                                              .navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		Database.setConnection("mongodb://192.168.43.152");

		navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		View navigationHeader = navigationView.getHeaderView(0);
		name = (TextView) navigationHeader.findViewById(R.id.nav_name);
		email = (TextView) navigationHeader.findViewById(R.id.nav_email);
		session = new Session(getApplicationContext());

		getSupportFragmentManager().addOnBackStackChangedListener(
				new FragmentManager
						    .OnBackStackChangedListener() {
					@Override
					public void
					onBackStackChanged() {
						if
								(getSupportFragmentManager
										 ()
										 .getBackStackEntryCount() > 0) {

							toggle
									.setDrawerIndicatorEnabled(false);

							toggle
									.setToolbarNavigationClickListener(new View.OnClickListener
											                                       () {
										@Override
										public void onClick
												(View v) {
											getSupportFragmentManager().popBackStack();
										}
									});
						} else {
							toggle
									.setDrawerIndicatorEnabled(true);
							toggle
									.setToolbarNavigationClickListener(new View.OnClickListener
											                                       () {
										@Override
										public void onClick
												(View v) {
											toggle
													.getToolbarNavigationClickListener();
										}
									});
						}
					}
				});

		if (savedInstanceState == null) {
			selectDrawerItem(navigationView.getMenu().getItem(1));
		}
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		this.setMenu();
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	public void setMenu() {
		Menu menu = navigationView.getMenu();

		menu.findItem(R.id.nav_properti).setVisible(session.isValid());
		menu.findItem(R.id.nav_favorit).setVisible(session.isValid());
		menu.findItem(R.id.nav_login).setVisible(!session.isValid());
		menu.findItem(R.id.nav_logout).setVisible(session.isValid());

		name.setText(session.get("name"));
		email.setText(session.get("email"));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem menuItem) {
		selectDrawerItem(menuItem);

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);

		return true;
	}

	public void selectDrawerItem(MenuItem menuItem) {
		Fragment fragment = null;
		FragmentManager fragmentManager = getSupportFragmentManager();

		// Handle navigation view item clicks here.
		switch (menuItem.getItemId()) {
			case R.id.nav_properti:
				fragment = PropertySellFragment.newInstance();
				break;
			case R.id.nav_favorit:
				fragment = FavoriteFragment.newInstance();
				break;
			case R.id.nav_kalkulator:
				fragment = CalculatorFragment.newInstance();
				break;
			case R.id.nav_login:
				fragment = LoginFragment.newInstance();
				break;
			case R.id.nav_logout:
				session.remove();
				this.setMenu();
			default:
				fragment = HomeFragment.newInstance();
				break;
		}

		fragmentManager.popBackStack(null, FragmentManager
				                                   .POP_BACK_STACK_INCLUSIVE);
		fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();

		menuItem.setChecked(true);
	}
}
