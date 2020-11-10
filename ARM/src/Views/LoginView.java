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

	//TODO: Notify if fail. Navigate to landing page if success
	private void onSignInButtonClick() {
		try {
			CompletableFuture<User> completableFuture = loginViewModel.loginAsync(userNameInput.getText(),
					String.copyValueOf(passwordInput.getPassword()));


//			JFrame frame = new JFrame();
//			final JOptionPane optionPane = new JOptionPane(
//					"The only way to close this dialog is by\n"
//							+ "pressing one of the following buttons.\n"
//							+ "Do you understand?",
//					JOptionPane.QUESTION_MESSAGE,
//					JOptionPane.YES_NO_OPTION);
//
//			final JDialog dialog = new JDialog(frame,
//					"Click a button",
//					true);
//			dialog.setContentPane(optionPane);
//			dialog.setDefaultCloseOperation(
//					JDialog.HIDE_ON_CLOSE);
//
//			final Toolkit toolkit = Toolkit.getDefaultToolkit();
//			final Dimension screenSize = toolkit.getScreenSize();
//			final int x = (screenSize.width - dialog.getWidth()) / 2;
//			final int y = (screenSize.height - dialog.getHeight()) / 2;
//			dialog.setLocation(x,y);
//			dialog.pack();
//			dialog.setVisible(true);

		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
	}

	//TODO: Navigate to Sign-up page
	private void onSignUpButtonClick() {

	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

}
