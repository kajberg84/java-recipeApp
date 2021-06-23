package TheRecipeApp;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class IngredientList {
    private Map<String, Ingredient> ingredients;
    private final String FIELD_DELIMETER = ":";

    public IngredientList() {
        ingredients = new HashMap<String, Ingredient>();
    }

    // skapa en ny url med ingred
    public void readFromFile(String fileURL) {
        try {
            File file = new File(fileURL);
            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {
                String myRow = scan.nextLine();
                Ingredient ingredient = parseIngredient(myRow);
                ingredients.put(ingredient.getName(), ingredient);
            }
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Ingredient parseIngredient(String input) {

        String[] fields = input.split(FIELD_DELIMETER);
        if (fields.length != 3) {
            System.out.println("broken Ingredient recipe"); // TODO errorhandling
            return null;
        }
        Ingredient ingredient = new Ingredient(fields[0].trim(), fields[1].trim(),
                Double.parseDouble(fields[2].trim()));

        return ingredient;
    }

    public void saveToFile(String fileURL) {
        String text = "";

        for (Map.Entry<String, Ingredient> entry : ingredients.entrySet()) {
            Ingredient i = entry.getValue();
            text += String.join(FIELD_DELIMETER, i.getName(), i.getUnitOfMeasure(), String.valueOf(i.getPrice()))
                    + System.lineSeparator();
        }

        File saveFile = new File(fileURL);
        try {
            PrintWriter printer = new PrintWriter(saveFile);
            printer.print(text);
            printer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Ingredient getIngredient(String ingredientName) {
        return ingredients.get(ingredientName);
    }

    public boolean hasIngredient(String ingredientName) {
        return ingredients.containsKey(ingredientName);
    }

    public ArrayList<String> getIngredientNames() {
        var result = new ArrayList<String>();

        for (Map.Entry<String, Ingredient> ingredient : ingredients.entrySet()) {
            result.add(ingredient.getValue().getName());
        }

        return result;
    }

    public void addIngredient(String name, String unitOfMeasure, Double price) {
        ingredients.put(name, new Ingredient(name, unitOfMeasure, price));
    }

    public void deleteIngredient(String name) {
        ingredients.remove(name);
    }
}
