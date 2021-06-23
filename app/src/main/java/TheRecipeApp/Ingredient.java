package TheRecipeApp;

public class Ingredient {
    private String name;
    private String unitOfMeasure; 
    private double price;

    public Ingredient(String name, String unitOfMeasure, double price){
        this.name = name;
        this.unitOfMeasure = unitOfMeasure;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
