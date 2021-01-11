package ViewModels;

import Models.ModelManager;
import Models.Salary;
import Models.User;
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
					MongoDatabase database = ModelManager.getInstance().getDatabase();
					MongoCollection<Document> d = database.getCollection("User");

					int count = 1;

					User u = null;

					String check_user_name;

					for (Document t : d.find()) {
							check_user_name = t.getString("username");
							if (check_user_name.compareTo(user.getUsername()) == 0)
								return false;
						}

					user.setID("EMP-" + System.currentTimeMillis());

					Document temp = new Document();
					temp.append("ID", user.getID());
					temp.append("username", user.getUsername());
					temp.append("password", hashedPass);
					temp.append("name", user.getName());
					temp.append("phoneNumber", user.getPhoneNumber());
					temp.append("DoB", user.getDoB());
					temp.append("gender", user.getGender());
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
