package id.imancha.propertyapps;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
	private Context context;
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;

	public Session(Context context) {
		this.context = context;
		this.preferences = context.getSharedPreferences("Session", 0);
		this.editor = this.preferences.edit();
	}

	public void create(String _id, String name, String email) {
		this.editor.clear();
		this.editor.putString("_id", _id);
		this.editor.putString("name", name);
		this.editor.putString("email", email);
		this.editor.commit();
	}

	public String get(String key) {
		return this.preferences.getString(key, null);
	}

	public boolean isValid() {
		if (this.preferences.contains("_id") &&
				this.preferences.contains("name") && this.preferences.contains("email"))
			return true;
		return false;
	}

	public void remove() {
		this.editor.clear();
		this.editor.commit();
	}
}
