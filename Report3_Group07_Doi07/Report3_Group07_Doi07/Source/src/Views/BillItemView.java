package Views;

import Models.Item;
import ViewModels.BillItemViewModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.AbstractMap;
import java.util.Objects;
import java.util.function.Function;

public class BillItemView {
	private JPanel rootPane;
	private JLabel nameTextField;
	private JTextField priceTextField;
	private JSpinner amountSpinner;

	private BillItemViewModel billItemViewModel;

	private Function<BillItemView, Void> onAmountChange; // Apply when amountSpinner change

	BillItemView(Item item) {
		billItemViewModel = new BillItemViewModel(item);

		nameTextField.setText(item.getName());
		priceTextField.setText(item.getPrice().toString());
		amountSpinner.setValue(1);

		amountSpinner.addChangeListener(this.setSpinnerListener());
//		amountSpinner.addPropertyChangeListener(billItemViewModel);

		rootPane.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(1)));

//		rootPane.setPreferredSize(rootPane.getPreferredSize());
	}

	// Prevent spinner amount < 0
	private ChangeListener setSpinnerListener() {
		return new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner spinner = (JSpinner) e.getSource();
				int val = (int) spinner.getValue();

				if (val < 0) {
					val = 0;
				}

				spinner.setValue(val);
				billItemViewModel.setAmount(val); // Should use data binding but don't know how to implement :((

				if(onAmountChange != null) {
					onAmountChange.apply(BillItemView.this);
				}
			}
		};
	}

	public AbstractMap.SimpleEntry<Item, Integer> getItemAmountPair() {
		return billItemViewModel.getBillItemModel();
	}

	public void setOnAmountChange(Function<BillItemView, Void> function) {
		this.onAmountChange = function;
	}

	public int getAmount() {
		return (Integer) this.amountSpinner.getValue();
	}

	public void setAmount(int amount) {
		this.amountSpinner.setValue(amount);
		this.billItemViewModel.setAmount(amount);
	}

	public long getTotal() {
		return billItemViewModel.getTotal();
	}

	public JPanel getRootPane() {
		return rootPane;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BillItemView)) return false;
		BillItemView that = (BillItemView) o;
		return Objects.equals(this.nameTextField.getText(), that.nameTextField.getText());
	}

	@Override
	public int hashCode() {
		return Objects.hash(rootPane, nameTextField, priceTextField, amountSpinner);
	}
}
