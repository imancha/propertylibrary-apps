package id.imancha.propertyapps;

import android.text.TextUtils;
import android.util.Patterns;

public class Validation {
	public final static boolean isValid(CharSequence text) {
		return !TextUtils.isEmpty(text);
	}

	public final static boolean isValidEmail(CharSequence email) {
		return Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}

	public final static boolean isValidName(CharSequence name) {
		return name.toString().matches("[a-zA-Z\\s]+$");
	}

	public final static boolean isValidPassword(CharSequence password) {
		return password.length() > 5;
	}

	public final static boolean isValidPhone(CharSequence phone) {
		return Patterns.PHONE.matcher(phone).matches() && phone.length() > 9 &&
				       phone.length() < 13;
	}

	public final static boolean isValidPrice(CharSequence price) {
		return price.toString().matches("[0-9]+$");
	}
}
