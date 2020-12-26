package ViewModels;

import Models.Item;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.AbstractMap;

public class BillItemViewModel {
	private AbstractMap.SimpleEntry<Item, Integer> billItemModel; // Item - amount

	public BillItemViewModel(Item item) {
		billItemModel = new AbstractMap.SimpleEntry<>(item, 1);
	}

	public AbstractMap.SimpleEntry<Item, Integer> getBillItemModel() {
		return billItemModel;
	}

	public void setAmount(int amount) {
		billItemModel.setValue(amount);
	}

	public long getTotal() {
		long price = billItemModel.getKey().getPrice();

		return price * billItemModel.getValue();
	}

}
