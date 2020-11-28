package ViewModels;

import Models.Bill;
import Models.Items;
import Models.Menu;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class MenuViewModel {
	private Menu menu;

	/**
	 * Get some items from database
	 * @param amount amount needed. If want to get all, {@code amount = -1}
	 * @return Items
	 */
	public CompletableFuture<ArrayList<Items>> getItemsListAsync(int amount) {
		return null;
	}

	public CompletableFuture<Items> addItemAsync(Items item) {
		return null;
	}

	/**
	 * Cập nhật giá trị mới cho item
	 * @param name Tên item cần sửa
	 * @param newValue Giá trị mới
	 * @return Item vừa sửa xong. Nếu k sửa được thì trả về null
	 * @throws Exception Thông tin lỗi dọc đường (Tên đã tồn tại,...)
	 */
	public CompletableFuture<Items> updateItem(String name, Items newValue) throws Exception {
		return null;
	}

	public CompletableFuture<Items> deleteItem(String name) {
		return null;
	}

	public CompletableFuture<Items> getItem(String name) {
		return null;
	}

	/**
	 * Save the bill to database
	 * @param bill Bill to be saved
	 * @return Successful or not
	 */
	public CompletableFuture<Boolean> purchaseBillAsync(Bill bill) {
		return null;
	}
}
