/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package TheRecipeApp;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String urlRecipes = "recipesave/recipes.txt";
        String urlIngredients = "recipesave/ingredients.txt";

        IngredientList ingredientList = new IngredientList();
        ingredientList.readFromFile(urlIngredients);

        RecipeBook recipeBook = new RecipeBook(ingredientList);
        recipeBook.readFromFile(urlRecipes);

        UserInterface userInterface = new UserInterface(recipeBook, scan, ingredientList);
        userInterface.Show();

        ingredientList.saveToFile(urlIngredients);
        recipeBook.saveToFile(urlRecipes);

        scan.close();
    }
}
