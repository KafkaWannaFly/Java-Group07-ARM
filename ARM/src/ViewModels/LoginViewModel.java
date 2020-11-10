package ViewModels;


/*Import libraries*/
import Models.User;
import java.util.concurrent.CompletableFuture;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Supplier;


public class LoginViewModel {

	//TODO: Gửi yêu cầu login
	//Nếu thông tin đúng thì trả về User object. Nếu sai thì trả về null
	public CompletableFuture<User> loginAsync(String user, String pass) {
		return CompletableFuture.supplyAsync(new Supplier<User>() {
			@Override
			public User get() {
				String hashedPass = org.apache.commons.codec.digest.DigestUtils.sha256Hex(pass);
				JSONParser parser = new JSONParser();
				try {
					JSONObject jsonObject = (JSONObject)parser.parse(new FileReader("./UserFormat.json"));;
					JSONArray userList = (JSONArray) jsonObject.get("listOfUsers");
					Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
					Iterator<JSONObject> iterator = userList.iterator();
					while (iterator.hasNext()) {
						JSONObject temp = iterator.next();
						if(hashedPass.equals(temp.get("password")) && user.equals(temp.get("username"))) {
							User u = gson.fromJson(String.valueOf(temp), User.class);
							return u;
						}
					}
				} catch (IOException | ParseException e) {
					e.printStackTrace();
				}
				return null;
			}
		});

	}

	//TODO: Gửi yêu cầu đăng ký
	//Nếu thành công thì trả về True, không thì False
	public CompletableFuture<Boolean> signUpAsync(User user) {
		return null;
	}

}
