package Views;

import Models.User;
import ViewModels.SignUpViewModel;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.MouseInputAdapter;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.time.DayOfWeek;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

public class SignUpView {
	private SignUpViewModel signUpViewModel = new SignUpViewModel();

	private JPanel root;
	private JPanel loginLabelGroup;
	private JPanel form;
	private JPanel textGroup;
	private JTextField usernameInput;
	private JPasswordField passwordInput;
	private JTextField fullNameInput;
	private JTextField citizenIdInput;
	private JTextField phoneInput;
	private JTextField emailInput;
	private JScrollPane scrollPane;
	private JPanel nonTextGroup;
	private JComboBox genderConboBox;
	private JPanel dobPanel;
	private JLabel cancelButton;
	private JLabel signUpButton;
	DatePicker datePicker = new DatePicker();

	public Function<Void, Void> goToSignIn = null;


	public SignUpView() {
		scrollPane.setPreferredSize(scrollPane.getPreferredSize());

		DatePickerSettings datePickerSettings = new DatePickerSettings(new Locale("en-us"));
		datePickerSettings.setAllowEmptyDates(false);
		datePickerSettings.setAllowKeyboardEditing(false);
		datePickerSettings.setFirstDayOfWeek(DayOfWeek.SUNDAY);
		datePickerSettings.setFormatForDatesCommonEra("dd-MM-yyyy");
		datePicker.setSettings(datePickerSettings);

		dobPanel.add(datePicker);

		setUpLabelButtonEffect(signUpButton, cancelButton);

		setInvisibleBorderTextFields(usernameInput, passwordInput,
				fullNameInput, citizenIdInput, phoneInput, emailInput);

		signUpButton.addMouseListener(new MouseInputAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);

				try {
					User user = registerUser();
					CompletableFuture<Boolean> signUpFuture = signUpViewModel.signUpAsync(user);
					signUpFuture.thenAccept(new Consumer<Boolean>() {
						@Override
						public void accept(Boolean signUpSuccessful) {
							if (signUpSuccessful) {
								DialogView.showInfoDialog("Sign Up", "Sign up successfully");
							}
							else {
								DialogView.showInfoDialog("Sign Up", "Sign up fail");
							}
						}
					});
				} catch (Exception exception) {
					DialogView.showInfoDialog("Exception", exception.getMessage());
				}
			}
		});

		cancelButton.addMouseListener(new MouseInputAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				goToSignIn.apply(null);
			}
		});

	}

	public JComponent getContainer() {
		return scrollPane;
	}

	private User registerUser() {
		User user = new User();

		String username = usernameInput.getText();
		if (username == null || username.length() < 1) {
			throw new NullPointerException("Username mustnot be empty");
		}
		if (!username.matches("[a-zA-Z0-9]*")) {
			throw new InputMismatchException("Username must only contain letter or number");
		}

		String password = String.valueOf(passwordInput.getPassword());
		if (password.length() < 1) {
			throw new NullPointerException("Password must not be empty");
		}
		if (!password.matches("[a-zA-Z0-9]*")) {
			throw new InputMismatchException("Password must only contain letter or number");
		}

		String citizenID = citizenIdInput.getText();
		if (citizenID == null || citizenID.length() < 1) {
			throw new NullPointerException("Citizen ID mustn't be empty!");
		}
		if (!citizenID.matches("[0-9]+")) {
			throw new InputMismatchException("Citizen ID must be number");
		}

		String phone = phoneInput.getText();
		if (phone == null || phone.length() < 1) {
			throw new NullPointerException("Phone number mustn't be empty!");
		}
		if (!phone.matches("[0-9]+")) {
			throw new InputMismatchException("Phone number must only be number");
		}

		user.setUsername(username);
		user.setPassword(password);
		user.setName(fullNameInput.getText());
		user.setCitizenID(citizenID);
		user.setGender(Objects.requireNonNull(genderConboBox.getSelectedItem()).toString());
		user.setDoB(datePicker.getDateStringOrEmptyString());
		user.setEmail(emailInput.getText());
		user.setPhoneNumber(phone);

		return user;
	}


	private void setInvisibleBorderTextFields(JTextField... textFields) {
		for (JTextField textField : textFields) {
			textField.setBorder(BorderFactory.createEmptyBorder());
		}
	}

	private void setUpLabelButtonEffect(JLabel... jLabels) {
		for (JLabel label : jLabels) {
			if (label == null) {
				continue;
			}
			label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			int clickDelta = 5;
			int hoverDelta = 2;

			label.addMouseListener(new MouseInputAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
				}

				@Override
				public void mousePressed(MouseEvent e) {
					super.mousePressed(e);

					label.setBounds(label.getX(), label.getY() + clickDelta, label.getWidth(), label.getHeight());
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					super.mouseReleased(e);

					label.setBounds(label.getX(), label.getY() - clickDelta, label.getWidth(), label.getHeight());
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					super.mouseEntered(e);

					label.setBounds(label.getX(), label.getY() - hoverDelta, label.getWidth(), label.getHeight());
				}

				@Override
				public void mouseExited(MouseEvent e) {
					super.mouseExited(e);

					label.setBounds(label.getX(), label.getY() + hoverDelta, label.getWidth(), label.getHeight());
				}
			});
		}
	}

}
