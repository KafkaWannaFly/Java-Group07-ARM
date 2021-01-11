package Views;

import Models.Item;
import Models.User;
import ViewModels.ItemViewModel;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class ItemView {
	private JLabel picture;
	private JTextField nameTextField;
	private JTextField priceTextField;
	private JPanel textPane;
	private JPanel rootPane;
	private JPanel outlinePane;
	private JButton orderButton;
	private JButton editButton;
	private JButton deleteButton;
	private JPanel buttonPane;

	private Function<Item, Void> onOrderButtonClick;

	/**
	 * MenuViewModel hold updateItem function so I have to do this workaround
	 * Params: (itemName, newValue, updateResult)
	 */
	private BiFunction<String, Item, Boolean> onItemUpdated;

	private ItemViewModel itemViewModel;
	private boolean isEditting = false; // Controlling state when in Manager mode


	ItemView(Item item) {
		itemViewModel = new ItemViewModel(item);

//		System.out.println(item);
		try {
			nameTextField.setText(item.getName());
			priceTextField.setText(item.getPrice().toString());

			itemViewModel.getImageAsync(item).thenAccept(new Consumer<File>() {
				@Override
				public void accept(File file) {
					picture.setIcon(new ImageIcon(file.getAbsolutePath()));
				}
			});

			this.setTextsEditable(false);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		orderButton.addMouseListener(this.orderButtonListener());
		editButton.addMouseListener(this.editButtonListener());

		outlinePane.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(1f)));
	}

	public void setOnOrderButtonClick(Function<Item, Void> onOrderButtonClick) {
		this.onOrderButtonClick = onOrderButtonClick;
	}

	/**
	 * Mở khóa các nút bấm dành cho Manager
	 *
	 * @param user
	 */
	public void leverageUserLevel(User user) {
		if (!user.isManager()) {
			return;
		}

		this.editButton.setEnabled(true);
		this.editButton.setVisible(true);

		this.editButton.setEnabled(true);
		this.deleteButton.setVisible(true);
	}

	private void setTextsEditable(boolean isEditable) {
//		Component[] components = textPane.getComponents();
//		for (Component component : components) {
//			try {
//				JTextField text = (JTextField) component;
//				if (text != null) {
//					text.setEditable(isEditable);
//				}
//			} catch (Exception exception) {
////				System.out.println(exception.getMessage());
////				System.out.println("This exception is made on purpose. We shouldn't worry about :)))");
//			}
//
//		}

//		nameTextField.setEditable(isEditable);
		priceTextField.setEditable(isEditable);
	}

	private MouseInputListener editButtonListener() {
		return new MouseInputAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					String oldName = nameTextField.getText();

					isEditting = !isEditting; // Switch state
					if (isEditting) {
						editButton.setText("Update");
					}
					else {
						// Do some validation
						String newPrice = priceTextField.getText();
						if (!newPrice.matches("[0-9]+")) {
							JOptionPane.showMessageDialog(rootPane,
									"Price must be number");
							return;
						}

						if (newPrice.isEmpty() || newPrice.isBlank()) {
							JOptionPane.showMessageDialog(rootPane,
									"Price must not be empty");
							return;
						}

						String newName = nameTextField.getText();
						if (newName.isEmpty() || newName.isBlank()) {
							JOptionPane.showMessageDialog(rootPane,
									"Name must not be empty");
							return;
						}

						itemViewModel.setItemName(newName);
						itemViewModel.setItemPrice(newPrice);
						if (onItemUpdated.apply(oldName, itemViewModel.getItem())) {
							JOptionPane.showMessageDialog(rootPane,
									"Update successfully");
						}
						else {
							JOptionPane.showMessageDialog(rootPane,
									"Update fail");
						}

						editButton.setText("Edit");
					}

					setTextsEditable(isEditting);
				} catch (Exception exception) {
					exception.printStackTrace();
				}

			}
		};
	}

	private MouseInputListener orderButtonListener() {
		return new MouseInputAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				onOrderButtonClick.apply(itemViewModel.getItem());
			}
		};
	}

	/// SETTER
	public void setOnItemUpdated(BiFunction<String, Item, Boolean> onItemUpdated) {
		this.onItemUpdated = onItemUpdated;
	}


	/// GETTER
	public JPanel getRootPane() {
		return rootPane;
	}
	public String getItemName() {
		return nameTextField.getText();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ItemView)) return false;
		ItemView itemView = (ItemView) o;

		return (itemView.nameTextField.getText().equals(this.nameTextField.getText()))
				       && (itemView.priceTextField.getText().equals(this.priceTextField.getText()));
	}

	@Override
	public int hashCode() {
		return Objects.hash(nameTextField, priceTextField);
	}
}
