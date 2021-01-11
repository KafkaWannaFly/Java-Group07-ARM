package ViewModels;

import Models.Bill;
import Models.Item;
import Models.Menu;
import Models.ModelManager;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
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

	//Thêm item mới vào database, nếu trùng item hoặc kết nối lỗi, return false
	public CompletableFuture<Boolean> addItemAsync(Item item) {
		return CompletableFuture.supplyAsync(new Supplier<Boolean>() {
			@Override
			public Boolean get() {
				try {
					MongoDatabase db = ModelManager.getInstance().getDatabase();
					MongoCollection<Document> itemCollection = db.getCollection("Item");

					for (Document t : itemCollection.find()) {
						String a = t.getString("name");
						if (item.getName().compareTo(a) == 0)
							return false;
					}

					Document temp = new Document();
					temp.append("type", item.getType());
					temp.append("name", item.getName());
					temp.append("price", item.getPrice().toString());
					temp.append("description", item.getDescription());
					temp.append("imgPath", item.getImgPath());

					itemCollection.insertOne(temp);

					return true;
				} catch (Exception exc){
					exc.printStackTrace();
					return false;
				}
			}
		});
	}

	/**
	 * Cập nhật giá trị mới cho item
	 * @param //name Tên item cần sửa
	 * @param //newValue Giá trị mới
	 * @return Item vừa sửa xong. Nếu k sửa được thì trả về null
	 * @throws Exception Thông tin lỗi dọc đường (Tên đã tồn tại,...)
	 */

	public CompletableFuture<Boolean> updateItemAsync(String name, Item newValue) throws Exception {
		return CompletableFuture.supplyAsync(new Supplier<Boolean>() {
			@Override
			public Boolean get() {
				try {
					MongoDatabase db = ModelManager.getInstance().getDatabase();
					MongoCollection<Document> itemCollection = db.getCollection("Item");
					Document specificItem = itemCollection.find(Filters.eq("name", name)).first();

					if (specificItem == null) {
						System.out.println("Can't find item");
					} else {
						updateType(newValue.getType(), newValue.getName(), itemCollection, specificItem);
//						updateName(newValue.getName(), itemCollection, specificItem);
						updatePrice(newValue.getPrice(), itemCollection, specificItem);
						updateDescription(newValue.getDescription(), itemCollection, specificItem);
						updateImgPath(newValue.getImgPath(), itemCollection, specificItem);

					}
					return true;
				} catch (MongoException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}

	public void updateType(String newType, String name, MongoCollection<Document> itemCollection, Document document) throws MongoException {
		if(newType.isEmpty() || newType.isBlank()) {
			return;
		}
		else {
			BasicDBObject query = new BasicDBObject();
			query.put("name", document.getString("name"));
			BasicDBObject newDocument = new BasicDBObject();
			newDocument.put("type", newType.toString());
			BasicDBObject updateObject = new BasicDBObject();
			updateObject.put("$set", newDocument);
			itemCollection.updateOne(query, updateObject);
		}
	}

//	public void updateName(String newName, MongoCollection<Document> itemCollection, Document document) throws MongoException {
//		if(newName.isEmpty() || newName.isBlank()) {
//			return;
//		}
//		else {
//			BasicDBObject query = new BasicDBObject();
//			query.put("name", document.getString("name"));
//			BasicDBObject newDocument = new BasicDBObject();
//			newDocument.put("name", newName);
//			BasicDBObject updateObject = new BasicDBObject();
//			updateObject.put("$set", newDocument);
//
//			itemCollection.updateOne(query, updateObject);
//		}
//	}

	public void updatePrice(Long newPrice, MongoCollection<Document> itemCollection, Document document) throws MongoException {
		if(newPrice <= 0) {
			return;
		}
		else {
			BasicDBObject query = new BasicDBObject();
			query.put("name", document.getString("name"));
			BasicDBObject newDocument = new BasicDBObject();
			newDocument.put("price", newPrice.toString());
			BasicDBObject updateObject = new BasicDBObject();
			updateObject.put("$set", newDocument);
			itemCollection.updateOne(query, updateObject);
		}
	}

	public void updateDescription(String newDescription, MongoCollection<Document> itemCollection, Document document) throws MongoException {
		if(newDescription.isEmpty() || newDescription.isBlank()) {
			return;
		}
		else {
			BasicDBObject query = new BasicDBObject();
			query.put("name", document.getString("name"));
			BasicDBObject newDocument = new BasicDBObject();
			newDocument.put("description", newDescription.toString());
			BasicDBObject updateObject = new BasicDBObject();
			updateObject.put("$set", newDocument);
			itemCollection.updateOne(query, updateObject);
		}
	}

	public void updateImgPath(String newPath, MongoCollection<Document> itemCollection, Document document) throws MongoException {
		if(newPath.isEmpty() || newPath.isBlank()) {
			return;
		}
		else {
			BasicDBObject query = new BasicDBObject();
			query.put("name", document.getString("name"));
			BasicDBObject newDocument = new BasicDBObject();
			newDocument.put("imgPath", newPath.toString());
			BasicDBObject updateObject = new BasicDBObject();
			updateObject.put("$set", newDocument);
			itemCollection.updateOne(query, updateObject);
		}
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
