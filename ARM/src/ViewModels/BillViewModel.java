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

class objectInBill extends Document{
    private String name;
    private Integer amount;
    private Long totalPrice;

    public objectInBill(){}

    public objectInBill(String name, Integer amount, Long totalPrice){
        this.name = name;
        this.amount = amount;
        this.totalPrice = totalPrice;
    }

    public objectInBill(String name, String amount, String totalPrice){
        this.append("Name", name);
        this.append("Amount", amount);
        this.append("Total", totalPrice);
    }

    public String getName(){
        return name;
    }

    public Integer getAmount(){
        return amount;
    }

    public Long getTotalPrice(){
        return totalPrice;
    }
}

public class BillViewModel {
    //TODO: Gửi yêu cầu đăng ký
    //Nếu thành công thì trả về True, không thì False
    public CompletableFuture<Boolean> purchaseBill(Bill bill) {
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

                    ArrayList<objectInBill> list = new ArrayList<>();

                    for (String item: key){
                        objectInBill obj = new objectInBill();

                        obj.append("Name", item);
                        obj.append("Amount", bill.getDishesWithNumber().get(item).toString());
                        Long tmp_price = bill.getDishesWithPrice().get(item) * bill.getDishesWithNumber().get(item);
                        obj.append("Total", tmp_price.toString());

                        list.add(obj);
                    }

                    temp.append("Dishes list", list);

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
