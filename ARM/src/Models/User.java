package Models;

import java.util.ArrayList;
import java.util.HashMap;

//TODO: Fill with proper attributes
public class User {
	protected String username, password;
	protected String name;
	protected String phoneNumber;
	protected String DoB;
	protected String gender;
	protected String ID;
	protected String email;
	protected String citizenID;
	protected ArrayList<HashMap<String, Integer>> salary = new ArrayList<>();

	public User() {
	}

	public User(String ID, String username, String password, String name, String phoneNumber, String DoB, String gender, String email, String CitizenID, ArrayList<HashMap<String, Integer>> salary) {
		this.ID = ID;
		this.username = username;
		this.password = password;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.DoB = DoB;
		this.gender = gender;
		this.email = email;
		this.citizenID = CitizenID;
		this.salary = salary;
	}

	public void InputInformation() {
	}

	//GETTER

	public String getUsername() {
		return username;
	}

	public String getName() {
		return name;
	}

	//SETTER
	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phone) {
		this.phoneNumber = phone;
	}

	public String getDoB() {
		return DoB;
	}

	public void setDoB(String doB) {
		this.DoB = doB;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		this.ID = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCitizenID() {
		return citizenID;
	}

	public void setCitizenID(String citizenID) {
		this.citizenID = citizenID;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	//Function
	public void ShowInformation() {
	}

	public void ShowMenu() {
	}
}
