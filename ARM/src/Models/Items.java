package Models;

public class Items {
    private String type;
    private String name;
    private Long price;
    private String imgPath;

    //CONSTRUCTOR
    public Items(){}
    public Items(String type, String name, Long price) {
        this.type = type;
        this.name = name;
        this.price = price;
    }

    //SETTER
    public void setName(String name){ this.name = name; }
    public void setPrice(Long price){ this.price = price; }
    public void setImgPath(){}

    //GETTER
    public String getName(){
        return name;
    }
    public Long getPrice(){
        return price;
    }
    public String getImgPath(){
        return imgPath;
    }
}
