package Models;

public class Items {
    private String name;
    private Long price;
    private String imgPath;
    private String type, description;

    public Items(){}

    //SETTER
    public void setName(String name){
        this.name = name;
    }
    public void setPrice(Long price){
        this.price = price;
    }
    public void setImgPath(String imgPath){
        this.imgPath = imgPath;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setDescription(String description){
        this.description = description;
    }

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
    public String getType(){
        return type;
    }
    public String getDescription() {
        return description;
    }
}
