package Views;

import Models.User;
import ViewModels.UserViewModel;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.function.Consumer;

public class UserAccountView {
	private JPanel rootPane;
	private JLabel idLabel;
	private JTextField passwordTF;
	private JTextField fullnameTF;
	private JComboBox genderComboBox;
	private JPanel birthPane;
	DatePicker datePicker = new DatePicker();
	private JTextField citizenIdTF;
	private JTextField phoneTF;
	private JTextField emailTF;
	private JLabel usernameLabel;
	private JPanel infoPane;
	private JPanel buttonsPane;
	private JButton editButton;
	private JButton updateButton;

	private User currentUser;
	private UserViewModel userViewModel = new UserViewModel();

	public UserAccountView(User user) {
		currentUser = user;

		idLabel.setText(user.getID());
		usernameLabel.setText(user.getUsername());
		passwordTF.setText("");
		fullnameTF.setText(user.getName());

		if(user.getGender() != null) {
			if(user.getGender().compareTo("Male") == 0) {
				genderComboBox.setSelectedIndex(0);
			}
			else if (user.getGender().compareTo("Female") == 0) {
				genderComboBox.setSelectedItem(1);
			}
			else {
				genderComboBox.setSelectedItem(2);
			}
		}


		DatePickerSettings datePickerSettings = new DatePickerSettings(new Locale("en-us"));
		datePickerSettings.setAllowEmptyDates(false);
		datePickerSettings.setAllowKeyboardEditing(false);
		datePickerSettings.setFirstDayOfWeek(DayOfWeek.SUNDAY);
		datePickerSettings.setFormatForDatesCommonEra("dd-MM-yyyy");

		datePicker.setSettings(datePickerSettings);
		datePicker.setDate(LocalDate.parse(user.getDoB()));

		birthPane.add(datePicker);

		citizenIdTF.setText(user.getCitizenID());
		phoneTF.setText(user.getPhoneNumber());
		emailTF.setText(user.getEmail());

		updateButton.addMouseListener(this.updateUserHandler());
	}

	public JPanel getRootPane() {
		return rootPane;
	}

	private MouseInputListener updateUserHandler() {
		return new MouseInputAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					currentUser.setName(fullnameTF.getText());
					if(!passwordTF.getText().isEmpty()) {
						currentUser.setPassword(passwordTF.getText());
					}
					currentUser.setGender((String)genderComboBox.getSelectedItem());
					currentUser.setDoB(datePicker.getDateStringOrEmptyString());
					currentUser.setCitizenID(citizenIdTF.getText());
					currentUser.setPhoneNumber(phoneTF.getText());
					currentUser.setEmail(emailTF.getText());

					userViewModel.updateOneUser(currentUser)
							.thenAccept(new Consumer<Boolean>() {
								@Override
								public void accept(Boolean isOK) {
									if(isOK) {
										JOptionPane.showMessageDialog(rootPane,
												"Update successfully!",
												"Update",
												JOptionPane.INFORMATION_MESSAGE);
									}
									else {
										JOptionPane.showMessageDialog(rootPane,
												"Update fail!",
												"Update",
												JOptionPane.ERROR_MESSAGE);
									}
								}
							});
				} catch (Exception exception) {
					exception.printStackTrace();
				}

			}
		};
	}
}
