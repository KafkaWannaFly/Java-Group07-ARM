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
        protected String CitizenID;
        protected ArrayList<HashMap<String, Integer>> salary = new ArrayList<>();

        public User(){}

        public User(String ID, String username, String password, String name, String phoneNumber, String DoB, String gender, String email, String CitizenID, double salary) {
                this.ID = ID;
                this.username = username;
                this.password = password;
                this.name = name;
                this.phoneNumber = phoneNumber;
                this.DoB = DoB;
                this.gender = gender;
                this.email = email;
                this.CitizenID = CitizenID;
                this.salary = salary;
        }

        public void InputInformation(){}

        //GETTER

        public String getUsername(){
            return username;
        }
        public String getName(){
            return name;
        }
        public String getPhoneNumber(){
            return phoneNumber;
        }
        public String getDoB(){
            return DoB;
        }
        public String getGender(){
            return gender;
        }
        public String getID(){
            return ID;
        }
        public String getEmail(){
            return email;
        }
        public String getCitizenID(){
            return CitizenID;
        }

        //SETTER
        public void setName(){}
        public void setPhoneNumber(){}
        public void setDoB(){}
        public void setGender(){}
        public void setID(){}
        public void setEmail(){}
        public void setCitizenID(){}

        //Function
        public void ShowInformation(){}
        public void ShowMenu(){}
}
