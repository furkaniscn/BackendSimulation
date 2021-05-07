package app.business.concretes;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.business.abstracts.UserManagerService;
import app.dataAccess.concretes.Database;
import app.entities.concretes.User;

public class UserManager implements UserManagerService {

	List<User> users;
	String regex = "^(.+)@(.+)$";
	Pattern pattern = Pattern.compile(regex);

	public UserManager(Database database) {
		this.users = database.getUsers();
	}

	@Override
	public boolean EmailControl(String email) {

		for (User user : users) {
			if (user.getEmail().equals(email)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean EmailRegexControl(String email) {
		Matcher matcher = pattern.matcher(email);
		if (matcher.matches() == false) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean PasswordControl(String password) {
		if (password.length() < 6) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean nameSurnameControl(String firstName, String lastName) {
		if (firstName.length() < 2 || lastName.length() < 2) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void SendConfirmEmail(String email) {

		System.out.println(email + " adl� e-posta adresine do�rulama mesaj� g�nderildi.");

	}

	@Override
	public void ConfirmAccount(User user) {

		user.setConfirmed(true);
		users.add(user);
		System.out.println("Hesap do�ruland� ve kay�t tamamland�.");

	}

	@Override
	public User Is_thereA_User(String email, String password) {
		for (User user : users) {
			if (email == user.getEmail()) {
				return user;
			}
		}
		return null;

	}

	@Override
	public void LoginUser(String email, String password) {

		User user = Is_thereA_User(email, password);

		if (user == null) {
			System.err.println("...Kullan�c� bulunamad�...");
		} else {

			if (password == user.getPassword()) {
				System.out.println("G�R�� BA�ARILI");
			} else {
				System.err.println("...Bilgilerinizi kontrol edin...");
			}

		}

	}

	@Override
	public void RegisterUser(User user) {

		if (EmailControl(user.getEmail()) == false) {
			System.err.println("Email ba�kas� taraf�ndan kullan�l�yor.");
		} else if (EmailRegexControl(user.getEmail()) == false) {
			System.err.println("Email adresi gereksinimleri kar��lam�yor,ba�ka bir email adresi deneyiniz.");
		} else if (PasswordControl(user.getPassword()) == false) {
			System.err.println("Parola uzunlu�u en az 6 karakter olmal�d�r.");
		} else if (nameSurnameControl(user.getFirstName(), user.getLastName()) == false) {
			System.err.println("Ad ve Soyad verileri en az 2 karakterden olu�mal�d�r.");
		} else {
			SendConfirmEmail(user.getEmail());
		}

	}

}
