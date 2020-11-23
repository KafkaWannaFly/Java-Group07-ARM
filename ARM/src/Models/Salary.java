import java.awt.print.PrinterGraphics;
package Models;

public class Salary {
    // Attributes
    private String date;
    private Integer amount;

    // Constructors
    public Salary(){}
    public Salary(String date, Integer amount) {
        this.date = date;
        this.amount = amount;
    }
    public Salary(String date, String amount) {
        this.date = date;
        this.amount = Integer.parseInt(amount);
    }
    // Check validation of date (format: mm-yyyy)
    public boolean isValid(String date) {
        int month, year;
        String[] temp = date.split("-");
        month = Integer.parseInt(temp[0]);
        year = Integer.parseInt(temp[1]);
        if(year > 2021 || year < 2015) return false;
        else {
            if (month > 12 || month < 1) return false;
        }
        return true;
    }
    // Getters and setters
    public void setDate(String date) {
        if(isValid(date)) {
            this.date = date;
        }
    }
    public void setAmount(String amount) {
        this.amount = Integer.parseInt(amount);
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMonth() {
        String[] temp = this.date.split("-");
        return temp[0];
    }
    public String getYear() {
        String[] temp = this.date.split("-");
        return temp[1];
    }
    public Integer getAmount() {
        return this.amount;
    }
}
