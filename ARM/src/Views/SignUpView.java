package Views;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.Function;

public class SignUpView {
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

		scrollPane.setPreferredSize(scrollPane.getPreferredSize());

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

	private void setInvisibleBorderTextFields(JTextField... textFields) {
		for (var textField : textFields) {
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
