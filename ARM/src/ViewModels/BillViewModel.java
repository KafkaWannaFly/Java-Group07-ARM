package ViewModels;

import Models.Bill;
import Models.ModelManager;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class BillViewModel {
    //TODO: Gửi yêu cầu đăng ký
    //Nếu thành công thì trả về True, không thì False
    public static CompletableFuture<Boolean> addItemAsync(Bill bill) {
        return CompletableFuture.supplyAsync(new Supplier<Boolean>() {
            @Override
            public Boolean get() {
                MongoDatabase database = ModelManager.getInstance().getDatabase();
                
                try{
                    MongoCollection<Document> d = database.getCollection("Bill");

                    Document temp = new Document();
                    temp.append("ID", bill.getBillID());
                    temp.append("Customer ID", bill.getCustomerID());
                    temp.append("Total", bill.getTotalPrice());

                    List<String> key = new ArrayList<>(bill.getDishesWithNumber().keySet());

                    for (String item: key){
                        temp.append("Name", item);
                        temp.append("Number", bill.getDishesWithNumber().get(item).toString());
                        Long tmp_price = bill.getDishesWithPrice().get(item) * bill.getDishesWithNumber().get(item);
                        temp.append("Total price", tmp_price.toString());
                    }

                    d.insertOne(temp);

                    return true;

                } catch (Exception e){
                    e.printStackTrace();
                }
                return false;
            }
        });
    }
}
