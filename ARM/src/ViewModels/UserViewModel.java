package ViewModels;
import Models.ModelManager;
import Models.User;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

public class UserViewModel {
    public CompletableFuture<Boolean> updateOneUser(User newUser) {
        return CompletableFuture.supplyAsync(new Supplier<Boolean>() {
            @Override
            public Boolean get() {
                try {
                    MongoDatabase db = ModelManager.getInstance().getDatabase();
                    MongoCollection<Document> userCollection = db.getCollection("User");
                    Document specificUser = userCollection.find(Filters.eq("ID", newUser.getID())).first();

                    if (specificUser == null) {
                        System.out.println("Can't find user");
                    } else {
                        updatePassword(newUser.getPassword(), userCollection, specificUser);
                        updateName(newUser.getName(), userCollection, specificUser);
                        updatePhoneNumber(newUser.getPhoneNumber(), userCollection, specificUser);
                        updateGender(newUser.getGender(), userCollection, specificUser);
                        updateDoB(newUser.getDoB(), userCollection, specificUser);
                        updateEmail(newUser.getEmail(), userCollection, specificUser);
                    }
                    return true;
                }
                catch(MongoException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }

    public void updatePassword(String newPassword, MongoCollection<Document> userCollection, Document document) throws MongoException {
        if(newPassword.isEmpty() || newPassword.isBlank()) {
            return;
        }
        else {
            String hashedPassword = org.apache.commons.codec.digest.DigestUtils.sha256Hex(newPassword);
            BasicDBObject newDocument = new BasicDBObject();
            newDocument.put("password", hashedPassword);
            BasicDBObject query = new BasicDBObject();
            query.put("password", document.getString("password"));
            BasicDBObject updateObject = new BasicDBObject();
            updateObject.put("$set", newDocument);

            userCollection.updateOne(query, updateObject);
        }
    }
    public void updateName(String newName, MongoCollection<Document> userCollection, Document document) throws MongoException {
        if(newName.isEmpty()) {
            return;
        }
        else {
            BasicDBObject newDocument = new BasicDBObject();
            newDocument.put("name", newName);
            BasicDBObject query = new BasicDBObject();
            query.put("name", document.getString("name"));
            BasicDBObject updateObject = new BasicDBObject();
            updateObject.put("$set", newDocument);

            userCollection.updateOne(query, updateObject);
        }
    }

    public void updatePhoneNumber(String newPhoneNumber, MongoCollection<Document> userCollection, Document document) throws MongoException {
        if(newPhoneNumber.isEmpty()) {
            return;
        }
        else {
            BasicDBObject newDocument = new BasicDBObject();
            newDocument.put("phoneNumber", newPhoneNumber);
            BasicDBObject query = new BasicDBObject();
            query.put("phoneNumber", document.getString("phoneNumber"));
            BasicDBObject updateObject = new BasicDBObject();
            updateObject.put("$set", newDocument);

            userCollection.updateOne(query, updateObject);
        }
    }

    public void updateDoB(String newDoB, MongoCollection<Document> userCollection, Document document) throws MongoException {
        if(newDoB.isEmpty()) {
            return;
        }
        else {
            BasicDBObject newDocument = new BasicDBObject();
            newDocument.put("DoB", newDoB);
            BasicDBObject query = new BasicDBObject();
            query.put("DoB", document.getString("DoB"));
            BasicDBObject updateObject = new BasicDBObject();
            updateObject.put("$set", newDocument);

            userCollection.updateOne(query, updateObject);
        }
    }

    public void updateGender(String newGender, MongoCollection<Document> userCollection, Document document) throws MongoException {
        if(newGender.isEmpty()) {
            return;
        }
        else {
            BasicDBObject newDocument = new BasicDBObject();
            newDocument.put("gender", newGender);
            BasicDBObject query = new BasicDBObject();
            query.put("gender", document.getString("gender"));
            BasicDBObject updateObject = new BasicDBObject();
            updateObject.put("$set", newDocument);

            userCollection.updateOne(query, updateObject);
        }
    }

    public void updateEmail(String newEmail, MongoCollection<Document> userCollection, Document document) throws MongoException {
        if(newEmail.isEmpty()) {
            return;
        }
        else {
            BasicDBObject newDocument = new BasicDBObject();
            newDocument.put("email", newEmail);
            BasicDBObject query = new BasicDBObject();
            query.put("email", document.getString("email"));
            BasicDBObject updateObject = new BasicDBObject();
            updateObject.put("$set", newDocument);

            userCollection.updateOne(query, updateObject);
        }
    }
}
