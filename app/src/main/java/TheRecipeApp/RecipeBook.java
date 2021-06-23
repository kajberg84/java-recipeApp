package TheRecipeApp;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RecipeBook {
    private Map<String, Recipe> recipes;
    private final String FIELD_DELIMETER = "; ";
    private final String INGREDIENT_DELIMETER = "/ ";
    private final String INSTRUCTION_DELIMETER = ", ";

    private IngredientList ingredientList;

    public RecipeBook(IngredientList ingredientList) {
        this.ingredientList = ingredientList;
        recipes = new HashMap<String, Recipe>();
    }

    public void readFromFile(String fileURL) {
        try {
            File file = new File(fileURL);
            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {
                String myRow = scan.nextLine();

                Recipe recipe = parseRecipe(myRow, ingredientList);
                if (recipe != null) {
                    recipes.put(recipe.getName(), recipe);
                }
            }
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Recipe parseRecipe(String input, IngredientList ingredientList) {

        String[] fields = input.split(";");
        if (fields.length != 5) {
            System.out.println("broken recipe"); // TODO errorhandling
            return null;
        }
        Recipe recipe = new Recipe(fields[0].trim(), Integer.parseInt(fields[1].trim()), fields[2].trim());

        // ParseIngredients.
        String tmpString = fields[3].trim();
        tmpString = tmpString.substring(1, tmpString.length() - 1);
        String[] tmpArray = tmpString.split("/");

        for (String n : tmpArray) {
            String[] tmp = n.split(",");
            int value = Integer.parseInt(tmp[0].trim());
            String unitOfMeasure = tmp[1].trim();
            String name = tmp[2].trim();
            String comment = tmp[3].trim();
            if (!ingredientList.hasIngredient(name)) {
                // skip ingredients not found
                continue;
            }
            Ingredient ingredient = ingredientList.getIngredient(name);
            recipe.addIngredient(ingredient, unitOfMeasure, value, comment);
        }

        // parseInstructions.
        tmpArray = fields[4].split(",");
        for (String s : tmpArray) {
            recipe.addInstruction(s.trim());
        }
        return recipe;
    }

    public void saveToFile(String fileURL) {
        String text = "";

        for (Map.Entry<String, Recipe> entry : recipes.entrySet()) {
            Recipe recipe = entry.getValue();

            String ingredients = "";
            for (RecipeIngredient i : recipe.ingredients) {
                ingredients += i.toString() + INGREDIENT_DELIMETER;
            }
            if (ingredients.length() > 0) {
                // remove last INGREDIENT_DELIMETER
                ingredients = ingredients.substring(0, ingredients.length() - INGREDIENT_DELIMETER.length());
            }
            ingredients = "[" + ingredients + "]";

            String instructions = String.join(INSTRUCTION_DELIMETER, recipe.instructions);

            text += String.join(FIELD_DELIMETER, recipe.getName(), String.valueOf(recipe.getSizes()),
                    recipe.getUnitOfMeasure(), ingredients, instructions) + System.lineSeparator();
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

    public Recipe getRecipe(String recipeName) {
        return recipes.get(recipeName);
    }

    public boolean hasRecipe(String recipeName) {
        return recipes.containsKey(recipeName);
    }

    public void addRecipe(Recipe recipe) {
        recipes.put(recipe.getName(), recipe);
    }

    public ArrayList<String> getRecipeNames() {
        var result = new ArrayList<String>();

        for (Map.Entry<String, Recipe> recipe : recipes.entrySet()) {
            result.add(recipe.getKey());
        }

        return result;
    }

    public ArrayList<String> findRecipesByStrategy(RecipeSearchStrategy searchStrategy) {
        var result = new ArrayList<String>();

        for (Map.Entry<String, Recipe> recipe : recipes.entrySet()) {
            if (searchStrategy.matches(recipe.getValue())) {
                result.add(recipe.getKey());
            }
        }

        return result;
    }

    public void deleteIngredient(Ingredient ingredient) {
        for (Map.Entry<String, Recipe> recipe : recipes.entrySet()) {
            if (recipe.getValue().usesIngredient(ingredient)) {
                recipe.getValue().deleteIngredient(ingredient);
            }
        }

        ingredientList.deleteIngredient(ingredient.getName());
    }

    public void deleteRecipe(String recipeName) {
        recipes.remove(recipeName);
    }
}
