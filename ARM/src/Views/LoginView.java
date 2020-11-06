package Views;

import Models.User;
import ViewModels.LoginViewModel;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.concurrent.CompletableFuture;

public class LoginView {
	private JPanel mainPanel;
	private JLabel picture;
	private JPanel userNameGroup;
	private JTextField userNameInput;
	private JPanel passwordGroup;
	private JPasswordField passwordInput;
	private JPanel form;
	private JPanel loginLabelGroup;
	private JPanel footerGroup;
	private JPanel buttonGroup;
	private JLabel signInLabel;
	private JLabel signUpLabel;

	private LoginViewModel loginViewModel = new LoginViewModel();

	public LoginView() {
		setUpLabelButtonEffect(signInLabel, signUpLabel);

		signInLabel.addMouseListener(new MouseInputAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				onSignInButtonClick();
			}
		});
	}

	/**
	 * Add few on-click effects which give button responsive feeling
	 * @param jLabels list of JLabel
	 */
	private void setUpLabelButtonEffect(JLabel... jLabels) {
		for (JLabel label : jLabels) {
			label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			int delta = 5;

			label.addMouseListener(new MouseInputAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
				}

				@Override
				public void mousePressed(MouseEvent e) {
					super.mousePressed(e);

					label.setBounds(label.getX(), label.getY() + delta, label.getWidth(), label.getHeight());
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					super.mouseReleased(e);

					label.setBounds(label.getX(), label.getY() - delta, label.getWidth(), label.getHeight());
				}
			});
		}
	}

	//TODO: Notify if fail. Navigate to landing page if success
	private void onSignInButtonClick() {
		CompletableFuture<User> completableFuture = loginViewModel.loginAsync(userNameInput.getText(),
				String.copyValueOf(passwordInput.getPassword()));
	}

	//TODO: Navigate to Sign-up page
	private void onSignUpButtonClick() {

	}

	public JPanel getMainPanel() {
		return mainPanel;
	}
}
