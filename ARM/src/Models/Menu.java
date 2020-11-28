package Models;

import java.util.ArrayList;

public class Menu {
    private ArrayList<Items> dishList = new ArrayList<>();
    private Bill bill = new Bill();

    public Menu(){}
    public Menu(ArrayList<Items> list) {
        this.dishList = list;
    }

    public ArrayList<Items> getDishList(){
        return dishList;
    }

    public Bill ExportBill(){
        return bill;
    }
}
