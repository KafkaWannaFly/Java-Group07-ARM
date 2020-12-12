package Views;

import Models.User;
import ViewModels.LoginViewModel;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

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
	private JLabel infoLabel;
	private JPanel formContainer;

	private LoginViewModel loginViewModel = new LoginViewModel();
	private SignUpView signUpView = new SignUpView();

	private Function<Void, Void> onNavigate;
	private Function<User, Void> onLoginSuccess;

	public LoginView() {
		setUpLabelButtonEffect(signInLabel, signUpLabel);

		signInLabel.addMouseListener(new MouseInputAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				onSignInButtonClick();
			}
		});

		signUpLabel.addMouseListener(new MouseInputAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);

				formContainer.remove(form);

				JComponent panel = signUpView.getContainer();

				formContainer.add(panel);
				formContainer.invalidate();
				formContainer.validate();

				if (onNavigate != null) {
					onNavigate.apply(null);
				}
			}
		});


		signUpView.goToSignIn = new Function<Void, Void>() {
			@Override
			public Void apply(Void unused) {
				JComponent panel = signUpView.getContainer();
				formContainer.remove(panel);

				formContainer.add(form);
				formContainer.invalidate();
				formContainer.validate();

				if (onNavigate != null) {
					onNavigate.apply(null);
				}
				return null;
			}
		};
	}

	/**
	 * Add few on-click effects which give button responsive feeling
	 *
	 * @param jLabels list of JLabel
	 */
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

	private void onSignInButtonClick() {
		try {
			CompletableFuture<User> completableFuture = loginViewModel.loginAsync(userNameInput.getText(),
					String.copyValueOf(passwordInput.getPassword())); //Deserteagle1801

			completableFuture.thenAccept(new Consumer<User>() {
				@Override
				public void accept(User user) {
					if (user == null) {
						infoLabel.setText("Incorrect Username or Password!");
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						infoLabel.setText("");
					}
					else { //Login Success
						DialogView.showInfoDialog("Login", "Success!");
						onLoginSuccess.apply(user);
					}
				}
			});

		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
	}

	public void setOnLoginSuccess(Function<User, Void> onLoginSuccess) {
		this.onLoginSuccess = onLoginSuccess;
	}

	public void setOnNavigate(Function<Void, Void> onNavigate) {
		this.onNavigate = onNavigate;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

}
