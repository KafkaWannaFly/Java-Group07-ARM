package Models;

public class Item {
    private String type;
    private String name;
    private Long price;
    private String description;
    private String imgPath;

    //CONSTRUCTOR
    public Item(){}

    public Item(String type, String name, String price, String description) {
        this.type = type;
        this.name = name;
        this.price = Long.parseLong(price);
        this.description = description;
    }

    public Item(String type, String name, String price, String description, String imgPath) {
        this.type = type;
        this.name = name;
        this.price = Long.parseLong(price);
        this.description = description;
        this.imgPath = imgPath;
    }

    //SETTER
    public void setType(String type) { this.type = type; }
    public void setName(String name){ this.name = name; }
    public void setPrice(Long price){ this.price = price; }
    public void setImgPath(String imgPath){
        this.imgPath = imgPath;
    }
    public void setDescription(String description) { this.description = description; }
    public void replace(Item i) {
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Item{");
        sb.append("type='").append(type).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", price=").append(price);
        sb.append(", description='").append(description).append('\'');
        sb.append(", imgPath='").append(imgPath).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
