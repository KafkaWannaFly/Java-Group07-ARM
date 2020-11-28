package ViewModels;

import Models.Salary;
import Models.User;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class SignUpViewModel {
	//TODO: Gửi yêu cầu đăng ký
	//Nếu thành công thì trả về True, không thì False
	public CompletableFuture<Boolean> signUpAsync(User user) {
		return CompletableFuture.supplyAsync(new Supplier<Boolean>() {
			@Override
			public Boolean get() {
				try{
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDateTime now = LocalDateTime.now();

					String hashedPass = org.apache.commons.codec.digest.DigestUtils.sha256Hex(user.getPassword());
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

					int count = 1;

					User u = null;
					for (Document t : d.find()) {
							String ID, username, password, name, phNumber, DoB, gender, email, citizenID;
							ArrayList<Salary> Salaries = new ArrayList<Salary>();
							ID = t.getString("ID");

							String[] idGroup = ID.split("-");

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

							if (idGroup[0].compareTo("EMP") == 0)
								count++;
						}

					user.setID("EMP-" + Integer.toString(count));

					Document temp = new Document();
					temp.append("ID", user.getID());
					temp.append("username", user.getUsername());
					temp.append("password", hashedPass);
					temp.append("name", user.getName());
					temp.append("phoneNumber", user.getPhoneNumber());
					temp.append("DoB", user.getDoB());
					temp.append("append", user.getGender());
					temp.append("email", user.getEmail());
					temp.append("citizenID", user.getCitizenID());

					ArrayList<Salary> salary = new ArrayList<>();

					String date = dtf.format(now).toString();
					String amount = "5000000";
					Salary s = new Salary();
					s.append("date", date);
					s.append("amount", amount);

					salary.add(s);
					temp.append("salary", salary);

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
