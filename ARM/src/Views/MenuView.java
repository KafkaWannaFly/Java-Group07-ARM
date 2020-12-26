package Views;

import Models.Item;
import ViewModels.MenuViewModel;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class MenuView {
	MenuViewModel menuViewModel = new MenuViewModel();
	private JPanel rootPane;
	private JTextField textField1;
	private JButton searchButton;
	private JButton confirmButton;
	private JPanel billPane;
	private JPanel itemsPane;
	private JComboBox sortOptions;
	private JScrollPane itemsScrollPane;
	private JSplitPane splitPane;
	private JPanel billItemsPane;
	private JScrollPane billItemsScrollPane;
	private JLabel totalPrice;

	private Function<Void, Void> onDOMChanged;

	private ArrayList<ItemView> itemViews = new ArrayList<>();
	private int actualItemAmount = -1;

	private ArrayList<BillItemView> billItemViews = new ArrayList<>();

	public MenuView() {
//		GridLayout gridBagLayout = new GridLayout(0,1, 0, 0);
//		billItemsPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		billItemsPane.setLayout(new GridLayout(0, 1));

		int scrollSpeed = 16;
		itemsScrollPane.getVerticalScrollBar().setUnitIncrement(scrollSpeed);
		billItemsScrollPane.getVerticalScrollBar().setUnitIncrement(scrollSpeed);

		this.populateMenuWithItems(20)
				.thenAccept(new Consumer<Void>() {
					@Override
					public void accept(Void unused) {
						// Wait items get filled up
						while (actualItemAmount < 0) {
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

						setupDefaultSplitPane();
						setupOrderButtonHandler();
					}
				});

	}

	private MouseInputListener confirmButtonListener() {
		return new MouseInputAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
			}
		};
	}

	private Function<BillItemView, Void> billItemAmountChangeHandler() {
		return new Function<BillItemView, Void>() {
			@Override
			public Void apply(BillItemView billItemView) {
				billItemsPane.invalidate();

				// Update total price
				long total = 0;
				for (var biv : billItemViews) {
					total += biv.getTotal();
				}
				totalPrice.setText(String.valueOf(total));

				billItemsPane.validate();
				return null;
			}
		};
	}

	private Function<Item, Void> handleOrderButtonClick() {

		return new Function<Item, Void>() {
			@Override
			public Void apply(Item item) {
				// Update UI when add new object
				billItemsPane.invalidate();

				BillItemView billItemView = new BillItemView(item);
				billItemView.setOnAmountChange(billItemAmountChangeHandler());

				int idx = billItemViews.indexOf(billItemView);
				if (idx >= 0) {
					BillItemView current = billItemViews.get(idx);
					current.setAmount(current.getAmount() + 1);
					billItemViews.set(idx, current);
				}
				else {
					billItemViews.add(billItemView);
				}

				GridBagConstraints gbc = new GridBagConstraints();
				gbc.weightx = 1;
				gbc.weighty = 1;
				gbc.fill = 1;
				gbc.gridx = 0;


				if (idx < 0) {
					billItemsPane.add(billItemView.getRootPane());
				}


				// Update total price
				long total = 0;
				for (var biv : billItemViews) {
					total += biv.getTotal();
				}
				totalPrice.setText(String.valueOf(total));

				billItemsPane.validate();
				return null;
			}
		};
	}

	private void setupOrderButtonHandler() {
//		while (actualItemAmount < 0) {
//			System.out.println("setupOrderButtonHandler with itemsView size = " + actualItemAmount);
//
//		}

		System.out.println("setupOrderButtonHandler with itemsView size = " + actualItemAmount);

		for (var itemView : itemViews) {
			itemView.setOnOrderButtonClick(handleOrderButtonClick());
		}
	}

	public void setupDefaultSplitPane() {
		Dimension itemsPaneDimension = itemsPane.getPreferredSize();
		Dimension billItemsDimension = billItemsPane.getPreferredSize();
		Dimension splitPaneDimension = splitPane.getPreferredSize();
		splitPane.setResizeWeight(1 - (float) billItemsDimension.width / (float) splitPaneDimension.width);
	}

	public void setOnDOMChanged(Function<Void, Void> function) {
		this.onDOMChanged = function;
	}


	/**
	 * Get items from database and fill up the view
	 *
	 * @param amount amount of item to get. -1 if want to get all
	 */
	private CompletableFuture<Void> populateMenuWithItems(int amount) {
		return CompletableFuture.supplyAsync(new Supplier<Void>() {
			@Override
			public Void get() {
				int colNum = 2;
				GridBagLayout gridLayout = new GridBagLayout();

				itemsPane.setLayout(gridLayout);

				GridBagConstraints gbc = new GridBagConstraints();
				gbc.weightx = 1;
				gbc.weighty = 1;
				gbc.fill = 1;
				gbc.gridx = 0;

				CompletableFuture<ArrayList<Item>> itemsFuture = menuViewModel.getItemsListAsync(amount);

				itemsFuture.thenAccept(new Consumer<ArrayList<Item>>() {
					@Override
					public void accept(ArrayList<Item> items) {
						Collections.sort(items, (o1, o2) -> o1.getName().compareTo(o2.getName()));
						Collections.sort(items, (o1, o2) -> o1.getType().compareTo(o2.getType()));

						for (var item : items) {
							try {
//								System.out.println(item);

								ItemView itemView = new ItemView(item);

								itemViews.add(itemView);

								itemsPane.add(itemView.getRootPane(), gbc);
								gbc.gridx = (gbc.gridx + 1) % colNum;
							} catch (Exception exception) {
								exception.printStackTrace();
							}
						}

						actualItemAmount = items.size();
						itemsPane.setVisible(true);
					}
				});
				return null;
			}
		});

	}

	public JPanel getRootPane() {
		return rootPane;
	}

}
