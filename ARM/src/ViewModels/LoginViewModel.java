package ViewModels;


/*Import libraries*/

import Models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;


public class LoginViewModel {

	//TODO: Gửi yêu cầu login
	//Nếu thông tin đúng thì trả về User object. Nếu sai thì trả về null
	public CompletableFuture<User> loginAsync(String user, String pass) {
		return CompletableFuture.supplyAsync(new Supplier<User>() {
			@Override
			public User get() {
				String hashedPass = org.apache.commons.codec.digest.DigestUtils.sha256Hex(pass);
				ConnectionString connString = new ConnectionString(
						"mongodb+srv://ManDuy:ManDuy177013@rootcluster.7m3s7.mongodb.net/RestatouilleDB?retryWrites=true&w=majority"
				);
				MongoClientSettings settings = MongoClientSettings.builder()
						.applyConnectionString(connString)
						.retryWrites(true)
						.build();
				MongoClient mongoClient = MongoClients.create(settings);
				MongoDatabase database = mongoClient.getDatabase("RestatouilleDB");
				MongoCollection<Document> d = database.getCollection("User");
				for(Document t : d.find()) {
					if (usr.compareTo(t.getString("username")) == 0 && hashedPass.compareTo(t.getString("password")) == 0) {
						String ID, username, password, name, phNumber, DoB, gender, email, citizenID;
						ArrayList<Salary> Salaries = new ArrayList<Salary>();
						ID = t.getString("ID");
						username = t.getString("username");
						password = t.getString("password");
						name = t.getString("name");
						phNumber = t.getString("phoneNumber");
						DoB = t.getString("DoB");
						gender = t.getString("gender");
						email = t.getString("email");
						citizenID = t.getString("citizenID");
						ArrayList<Document> salaries = (ArrayList<Document>) t.get("salary");
						for (Document salary : salaries) {
							String date = salary.getString("date");
							String amount = salary.getString("amount");
							Salary s = new Salary(date, amount);
							Salaries.add(s);
						}
						User u = new User(ID, username, password, name, phNumber, DoB, gender, email, citizenID, Salaries);
					}
				}
				return u;

					return null;
				} catch (IOException | ParseException e) {
					e.printStackTrace();
				}
				return null;
			}
		});

	}


}
