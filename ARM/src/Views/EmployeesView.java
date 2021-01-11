package Views;

import Models.User;
import ViewModels.UserViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Function;

public class EmployeesView {
	private User currentUser;
	private JPanel rootPane;
	private JPanel employeesListPane;
	private ArrayList<EmployeeView> employees = new ArrayList<>();
	private UserViewModel userViewModel = new UserViewModel();
	private ArrayList<User> users = new ArrayList<>();

	public EmployeesView(User user) {
		this.currentUser = user;

		userViewModel.getUsersAsync(currentUser.getID())
				// Handle query results
				.thenAccept(new Consumer<ArrayList<User>>() {
					@Override
					public void accept(ArrayList<User> arrayList) {
						users = arrayList;
					}
				})
				// Set up for variable
				.thenAccept(new Consumer<Void>() {
					@Override
					public void accept(Void unused) {
						for (var user : users) {
							EmployeeView employeeView = new EmployeeView(user);
							employees.add(employeeView);

							employeeView.setOnDeleteButtonClick(new Function<EmployeeView, Boolean>() {
								@Override
								public Boolean apply(EmployeeView emp) {
									try {
										boolean isDeleted = userViewModel.deleteUserAsync(emp.getCurrentUser().getID()).get();

										if(isDeleted) {
											employeesListPane.invalidate();
											employeesListPane.remove(emp.getRootPane());
											employeesListPane.validate();
										}

										return isDeleted;
									} catch (InterruptedException | ExecutionException e) {
										e.printStackTrace();
									}
									return false;
								}
							});

							employeeView.setOnUpdateButtonClick(new Function<User, Boolean>() {
								@Override
								public Boolean apply(User user) {
									try {
										return userViewModel.updateOneUser(user).get();
									} catch (InterruptedException | ExecutionException e) {
										e.printStackTrace();
									}
									return false;
								}
							});
						}
					}
				})
				// Load data into view
				.thenAccept(new Consumer<Void>() {
					@Override
					public void accept(Void unused) {
						loadUsersDataToPane();
					}
				});
	}

	private void loadUsersDataToPane() {
		GridBagLayout gridLayout = new GridBagLayout();
		employeesListPane.setLayout(gridLayout);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = 1;
		gbc.gridx = 0;

		for (var employee : employees) {
			employeesListPane.add(employee.getRootPane(), gbc);
		}
	}

	public JPanel getRootPane() {
		return rootPane;
	}

}
