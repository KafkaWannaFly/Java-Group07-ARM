package ViewModels;

import Models.Bill;
import Models.Items;
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
	 * @return Items
	 */
	public CompletableFuture<ArrayList<Items>> getItemsListAsync(int amount) {
		return CompletableFuture.supplyAsync(new Supplier<ArrayList<Items>>() {
			@Override
			public ArrayList<Items> get() {
				MongoDatabase db = ModelManager.getInstance().getDatabase();
				int count = 0;
				try {
					MongoCollection<Document> doc = db.getCollection("Item");
					ArrayList<Items> items = new ArrayList<>();
					for (Document t : doc.find()) {
						if(count == amount - 1)
							break;
						Items i = new Items(
								t.getString("type"),
								t.getString("name"),
								t.getString("price"),
								t.getString("description")
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
		return CompletableFuture.supplyAsync(new Supplier<Items>() {
			@Override
			public Items get() {
				try {
					for (Items i : menu.getDishList()) {
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
	public CompletableFuture<Items> deleteItem(String name) {
		return CompletableFuture.supplyAsync(new Supplier<Items>() {
			@Override
			public Items get() {
				try {
					MongoDatabase db =  ModelManager.getInstance().getDatabase();
					MongoCollection<Document> doc = db.getCollection("Item");
					Document temp = doc.findOneAndDelete(Filters.eq("name", name));
					Items deleteItem = new Items(temp.getString("type"),
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

	public CompletableFuture<Items> getItem(String name) {
		return CompletableFuture.supplyAsync(new Supplier<Items>() {
			@Override
			public Items get() {
				try {
					ArrayList<Items> list = menu.getDishList();
					for(Items i : list) {
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
