package Models;

import java.util.HashMap;

public class Bill {
    private HashMap<Items, Integer> dishes = new HashMap<>();
    private String billID;
    private Customer customer;

    public Bill(){}

    //These two function will use on getBill()
    private String getBillID(){
        return billID;
    }
    private Customer getCustomerInformation(){
        return null;
    }

    public Bill MakeBill(){
        return null;
    }
}
