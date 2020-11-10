package Models;

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
        protected double salary;

        public User(){}

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
