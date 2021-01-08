package ViewModels;

import Models.Bill;
import Models.Item;
import Models.Menu;
import Models.ModelManager;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.model.Filters;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class MenuViewModel {
	private Menu menu;

	/**
	 * Get some items from database
	 * @param amount amount needed. If want to get all, {@code amount = -1}
	 * @return Item
	 */
	public CompletableFuture<ArrayList<Item>> getItemsListAsync(int amount) {
		return CompletableFuture.supplyAsync(new Supplier<ArrayList<Item>>() {
			long _amount = amount;
			@Override
			public ArrayList<Item> get() {
				MongoDatabase db = ModelManager.getInstance().getDatabase();
				MongoCollection<Document> itemCollection = db.getCollection("Item");
				int count = 0;
				if(this._amount < 0) {
					this._amount = itemCollection.countDocuments();
				}
				try {
					ArrayList<Item> items = new ArrayList<>();
					for (Document t : itemCollection.find()) {
						if(count == this._amount - 1)
							break;
						Item i = new Item(
								t.getString("type"),
								t.getString("name"),
								t.getString("price"),
								t.getString("description"),
								t.getString("imgPath")
						);
						items.add(i);
						count++;
					}
					menu = new Menu(items);
					return items;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}

	public CompletableFuture<Item> addItemAsync(Item item) {
		return null;
	}

	/**
	 * Cập nhật giá trị mới cho item
	 * @param name Tên item cần sửa
	 * @param newValue Giá trị mới
	 * @return Item vừa sửa xong. Nếu k sửa được thì trả về null
	 * @throws Exception Thông tin lỗi dọc đường (Tên đã tồn tại,...)
	 */
	public CompletableFuture<Item> updateItemAsync(String name, Item newValue) throws Exception {
		return CompletableFuture.supplyAsync(new Supplier<Item>() {
			@Override
			public Item get() {
				try {
					for (Item i : menu.getDishList()) {
						if (i.getName().compareTo(name) == 0)
							i.replace(newValue);
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
				return null;
			}

		});
	}
	/** Xóa món ăn thì trả về boolean, xóa được hay không thôi hen?*/
	public CompletableFuture<Item> deleteItem(String name) {
		return CompletableFuture.supplyAsync(new Supplier<Item>() {
			@Override
			public Item get() {
				try {
					MongoDatabase db =  ModelManager.getInstance().getDatabase();
					MongoCollection<Document> doc = db.getCollection("Item");
					Document temp = doc.findOneAndDelete(Filters.eq("name", name));
					Item deleteItem = new Item(temp.getString("type"),
							temp.getString("name"),
							temp.getString("price"),
							temp.getString("description"));
					return deleteItem;
				} catch(Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}

	public CompletableFuture<Item> getItem(String name) {
		return CompletableFuture.supplyAsync(new Supplier<Item>() {
			@Override
			public Item get() {
				try {
					ArrayList<Item> list = menu.getDishList();
					for(Item i : list) {
						if(i.getName().compareTo(name) == 0)
							return i;
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});
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
