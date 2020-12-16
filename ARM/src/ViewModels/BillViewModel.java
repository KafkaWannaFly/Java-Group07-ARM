package ViewModels;

import Models.Bill;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
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
                try{
                    ConnectionString connString = new ConnectionString(
                            "mongodb+srv://ManDuy:ManDuy177013@rootcluster.7m3s7.mongodb.net/RestatouilleDB?retryWrites=true&w=majority"
                    );
                    MongoClientSettings settings = MongoClientSettings.builder()
                            .applyConnectionString(connString)
                            .retryWrites(true)
                            .build();
                    MongoClient mongoClient = MongoClients.create(settings);
                    MongoDatabase database = mongoClient.getDatabase("RestatouilleDB");
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
