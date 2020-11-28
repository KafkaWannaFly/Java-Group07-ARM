package ViewModels;


/*Import libraries*/

import Models.ModelManager;
import Models.Salary;
import Models.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class LoginViewModel {

	//TODO: Gửi yêu cầu login
	//Nếu thông tin đúng thì trả về User object. Nếu sai thì trả về null
	public CompletableFuture<User> loginAsync(String user, String pass) {
		return CompletableFuture.supplyAsync(new Supplier<User>() {
			@Override
			public User get() {
				try {
					String hashedPass = org.apache.commons.codec.digest.DigestUtils.sha256Hex(pass);

					MongoDatabase database = ModelManager.getInstance().getDatabase();
					MongoCollection<Document> d = database.getCollection("User");
					User u = null;
					for (Document t : d.find()) {
						if (user.compareTo(t.getString("username")) == 0 && hashedPass.compareTo(t.getString("password")) == 0) {
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
							u = new User(ID, username, password, name, phNumber, DoB, gender, email, citizenID, Salaries);
							return u;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});

	}


}
