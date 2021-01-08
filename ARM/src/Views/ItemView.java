package Views;

import Models.Item;
import ViewModels.ItemViewModel;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
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

	private ItemViewModel itemViewModel;


	ItemView(Item item) {
		itemViewModel = new ItemViewModel(item);

//		System.out.println(item);
		try {
			nameTextField.setText(item.getName());
			priceTextField.setText(item.getPrice().toString() + " đ");

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

//		rootPane.setPreferredSize(rootPane.getPreferredSize());
//		textPane.setPreferredSize(textPane.getPreferredSize());
//		outlinePane.setPreferredSize(outlinePane.getPreferredSize());

		orderButton.addMouseListener(this.setupOrderButtonListener());
		outlinePane.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(1f)));
	}

	public void setOnOrderButtonClick(Function<Item, Void> onOrderButtonClick) {
		this.onOrderButtonClick = onOrderButtonClick;
	}

	private void setTextsEditable(boolean isEditable) {
		Component[] components = textPane.getComponents();
		for (Component component : components) {
			try {
				JTextField text = (JTextField) component;
				if (text != null) {
					text.setEditable(isEditable);
				}
			} catch (Exception exception) {
//				System.out.println(exception.getMessage());
//				System.out.println("This exception is made on purpose. We shouldn't worry about :)))");
			}

		}
	}

	private MouseInputListener setupOrderButtonListener() {
		return new MouseInputAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				onOrderButtonClick.apply(itemViewModel.getItem());
			}
		};
	}

	public JPanel getRootPane() {
		return rootPane;
	}

}
