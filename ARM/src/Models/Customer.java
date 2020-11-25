package Models;

public class Customer {
    private String customerID;
    private String name;
    private String phoneNumber;
    private Integer point;
    private DiscountPolicy discount;

    public Customer(){}

    //SETTER
    public void setCustomerID(){}
    public void setName(){}
    public void setPhoneNumber(){}
    public void setPoint(){/*Use function in Discount Policy*/}
    public void setDiscount(){/*Use function in Discount Policy*/}

    //GETTER
    public String getCustomerID(){
        return customerID;
    }
    public String getName(){
        return name;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }

    //Function
    public double CalcMoneyAfterDiscount(){
        return 0;
    }
}
