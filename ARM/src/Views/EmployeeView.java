package Views;

import Models.User;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;
import java.util.function.Function;

public class EmployeeView {
	private JPanel rootPane;
	private JTextField passwordTF;
	private JLabel idLabel;
	private JLabel fullnameLabel;
	private JLabel usernameLabel;
	private JPanel inforPane;
	private JButton updateButton;
	private JButton editButton;
	private JButton deleteButton;

	private Function<EmployeeView, Boolean> onDeleteButtonClick;
	private Function<User, Boolean> onUpdateButtonClick;

	private boolean isEditting = false;
	private User currentUser;

	public EmployeeView(User user) {
		currentUser = user;

		idLabel.setText(user.getID());
		fullnameLabel.setText(user.getName());
		usernameLabel.setText(user.getUsername());

		passwordTF.setText(user.getPassword());
		passwordTF.setEditable(false);

		editButton.addMouseListener(this.editHandler());
		deleteButton.addMouseListener(this.deleteHandler());
		updateButton.addMouseListener(this.updateHandler());
	}

	public void setOnDeleteButtonClick(Function<EmployeeView, Boolean> onDeleteButtonClick) {
		this.onDeleteButtonClick = onDeleteButtonClick;
	}

	public void setOnUpdateButtonClick(Function<User, Boolean> onUpdateButtonClick) {
		this.onUpdateButtonClick = onUpdateButtonClick;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	private MouseInputListener updateHandler() {
		return new MouseInputAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					currentUser.setPassword(passwordTF.getText());

					if(onUpdateButtonClick != null) {
						boolean isUpdated = onUpdateButtonClick.apply(currentUser);

						if(isUpdated) {
							JOptionPane.showMessageDialog(rootPane,
									"Done updating " + currentUser.getUsername());
						}
						else  {
							JOptionPane.showMessageDialog(rootPane,
									"Fail tp update （＞人＜；)");
						}
					}
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		};
	}

	private MouseInputListener deleteHandler() {
		return new MouseInputAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					int opt = JOptionPane.showConfirmDialog(rootPane,
							"Are you sure deleting " + currentUser.getName() + "?",
							"Delete", JOptionPane.OK_CANCEL_OPTION);

					if(opt == JOptionPane.OK_OPTION) {
						if (onDeleteButtonClick != null) {
							boolean isDeleted = onDeleteButtonClick.apply(EmployeeView.this);
							if(isDeleted) {
								JOptionPane.showMessageDialog(rootPane,
										"Delete done!");
							} else {
								JOptionPane.showMessageDialog(rootPane, "Fail to delete ╯︿╰");
							}
						}
					}

				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		};
	}

	private MouseInputListener editHandler() {
		return new MouseInputAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				isEditting = !isEditting;

				if (isEditting) {
					editButton.setText("Cancel");
				}
				else {
					editButton.setText("Edit");
					if (passwordTF.getText().isEmpty() || passwordTF.getText().isBlank()) {
						passwordTF.setText(currentUser.getPassword());
					}
				}

				passwordTF.setEditable(isEditting);

			}
		};
	}


	public JPanel getRootPane() {
		return rootPane;
	}
}
