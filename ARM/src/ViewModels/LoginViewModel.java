package ViewModels;

import Models.User;

import java.util.concurrent.CompletableFuture;

public class LoginViewModel {

	//TODO: Gửi yêu cầu login
	//Nếu thông tin đúng thì trả về User object. Nếu sai thì trả về null
	public CompletableFuture<User> loginAsync(String user, String pass) {
		return null;
	}

	//TODO: Gửi yêu cầu đăng ký
	//Nếu thành công thì trả về True, không thì False
	public CompletableFuture<Boolean> signUpAsync(User user) {
		return null;
	}

}
