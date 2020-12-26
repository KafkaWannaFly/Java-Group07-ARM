package Models;

import java.util.ArrayList;

public class Menu {
    private ArrayList<Item> dishList = new ArrayList<>();
    private Bill bill = new Bill();

    public Menu(){}
    public Menu(ArrayList<Item> list) {
        this.dishList = list;
    }

    public ArrayList<Item> getDishList(){
        return dishList;
    }

    public Bill ExportBill(){
        return bill;
    }
}
