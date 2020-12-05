package Models;

public class Items {
    private String type;
    private String name;
    private Long price;
    private String description;
    private String imgPath;

    //CONSTRUCTOR
    public Items(){}

    public Items(String type, String name, String price, String description) {
        this.type = type;
        this.name = name;
        this.price = Long.parseLong(price);
        this.description = description;
    }

    //SETTER
    public void setType(String type) { this.type = type; }
    public void setName(String name){ this.name = name; }
    public void setPrice(Long price){ this.price = price; }
    public void setImgPath(String imgPath){
        this.imgPath = imgPath;
    }
    public void setDescription(String description) { this.description = description; }
    public void replace(Items i) {
        type = i.getType();
        name = i.getName();
        price = i.getPrice();
        description = i.getDescription();
    }

    //GETTER
    public String getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public Long getPrice() {
        return price;
    }
    public String getImgPath() {
        return imgPath;
    }
    public String getDescription() {
        return description;
    }
}
