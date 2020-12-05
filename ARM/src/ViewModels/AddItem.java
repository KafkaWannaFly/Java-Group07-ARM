package ViewModels;


import Models.Items;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AddItem {
    //TODO: Gửi yêu cầu đăng ký
    //Nếu thành công thì trả về True, không thì False
    public static CompletableFuture<Boolean> addItemAsync(Items items, String type, String des) {
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
                    MongoCollection<Document> d = database.getCollection("Item");


                    Document temp = new Document();
                    temp.append("type", type);
                    temp.append("name", items.getName());
                    temp.append("price", items.getPrice().toString());
                    temp.append("description", des);
                    temp.append("imgPath", items.getImgPath());

                    d.insertOne(temp);

                    return true;

                } catch (Exception e){
                    e.printStackTrace();
                }
                return false;
            }
        });
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner =  new Scanner(System.in);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Items i = new Items();

        String type, name, description, imgPath;
        Long price;

        System.out.println("Input item's information:");

        type="Drink"; name="Soda"; description="This cannot be drunk by normal way, lick it";
        imgPath="NaN";
        price= Long.valueOf(15000);

        /*
        System.out.print("Type: ");
        type = scanner.next();

        scanner.nextLine();
        System.out.print("Name: ");
        name = scanner.nextLine();
        i.setName(name);

        System.out.print("Price: ");
        price = scanner.nextLong();
        i.setPrice(price);

        scanner.nextLine();
        System.out.print("Description: ");
        description = scanner.nextLine();

        System.out.print("Image Path: ");
        imgPath = scanner.next();
        i.setImgPath(imgPath);
         */

        try {
            CompletableFuture<Boolean> addItemFuture = addItemAsync(i, type, description);
            addItemFuture.thenAccept(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean signUpSuccessful) {
                    if(signUpSuccessful) {
                        System.out.println("Add item successfully");
                    } else {
                        System.out.println("Add item fail");
                    }
                }
            });
        } catch (Exception exception) {
            System.out.println("Exception " + exception.getMessage());
        }
    }
}
