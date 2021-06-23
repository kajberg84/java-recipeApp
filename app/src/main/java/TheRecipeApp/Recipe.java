package TheRecipeApp;

import java.util.ArrayList;
import java.util.Iterator;

public class Recipe {
    private String name;
    private int sizes;
    private String unitOfMeasure;

    public ArrayList<RecipeIngredient> ingredients = new ArrayList<RecipeIngredient>();
    public ArrayList<String> instructions = new ArrayList<String>();

    public Recipe(String name, int sizes, String unitOfMeasure) {
        this.name = name;
        this.sizes = sizes;
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSizes() {
        return sizes;
    }

    public void setSizes(int sizes) {
        this.sizes = sizes;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public void addIngredient(Ingredient ingredient, String unitOfMeasure, int value, String comment) {
        RecipeIngredient recipeIngredient = new RecipeIngredient(ingredient, unitOfMeasure, value, comment);
        ingredients.add(recipeIngredient);
    }

    public void deleteIngredient(Ingredient ingredient) {
        for (Iterator<RecipeIngredient> it = ingredients.iterator(); it.hasNext();) {
            RecipeIngredient recipeIngredient = it.next();
            if (recipeIngredient.getIngredient() == ingredient) {
                it.remove();
            }
        }
    }

    public void addInstruction(String instruction) {
        instructions.add(instruction);
    }

    public boolean usesIngredient(Ingredient ingredient) {
        for (RecipeIngredient recipeIngredient : ingredients) {
            if (recipeIngredient.getIngredient() == ingredient) {
                return true;
            }
        }
        return false;
    }

    public Double getPrice() {
        Double result = 0.0;
        for (RecipeIngredient recipeIngredient : ingredients) {
            result += recipeIngredient.getPrice();
        }
        return result;
    }
}
