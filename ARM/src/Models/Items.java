package Models;

public class Items {
    private String name;
    private Long price;
    private String imgPath;

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
