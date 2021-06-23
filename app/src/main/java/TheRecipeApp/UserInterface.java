package TheRecipeApp;

import java.util.Scanner;
import java.text.DecimalFormat;

public class UserInterface {
    private RecipeBook recipeBook;
    private Scanner scan;
    private IngredientList ingredientList;

    public UserInterface(RecipeBook recipeBook, Scanner scan, IngredientList ingredientList) {
        this.recipeBook = recipeBook;
        this.scan = scan;
        this.ingredientList = ingredientList;
    }

    public void Show() {
        boolean continueMainLoop = true;

        while (continueMainLoop) {
            System.out.println("What do you want to do?(input a number)");
            System.out.println("1. Manage Recipes.");
            System.out.println("2. Manage ingredients.");
            System.out.println("0. Quit.");

            String userInput = scan.nextLine();
            int userOption = Integer.parseInt(userInput);
            switch (userOption) {
            case 1:
                showRecipeMenu();
                break;
            case 2:
                showIngredientMenu();
                break;
            case 0:
                System.out.println("Quit!");
                continueMainLoop = false;
            }
        }
    }

    // #region Recipes
    private void showRecipeMenu() {
        System.out.println("  _______________");
        System.out.println("  |              |");
        System.out.println("  |    Recipes   |");
        System.out.println("  |______________|");
        System.out.println("");
        System.out.println("1. Search recipes");
        System.out.println("2. Show a recipe");
        System.out.println("3. Add a new recipe");
        System.out.println("4. Delete a particular recipe");
        System.out.println("0. Back to main menu");

        String userInput = scan.nextLine();
        int userOption = Integer.parseInt(userInput);
        switch (userOption) {
        case 1:
            searchRecipes();
            break;
        case 2:
            showRecipe();
            break;
        case 3:
            addNewRecipe();// TODO: continue
            break;
        case 4:
            deleteRecipe();
            break;
        case 0:
            return;
        }
    }

    private void searchRecipes() {
        int userOption;
        System.out.println("  _______________");
        System.out.println("  |              |");
        System.out.println("  |Search Recipes|");
        System.out.println("  |______________|");
        System.out.println("What do you wanna do?");
        System.out.println("1. List all recipes");
        System.out.println("2. Search recipes by ingredient");
        System.out.println("3. Search recipes by max price");
        System.out.println("0. Main menu.");

        String userInput = scan.nextLine();
        userOption = Integer.parseInt(userInput);

        RecipeSearchStrategy searchStrategy = null;
        while (searchStrategy == null) {
            switch (userOption) {
            case 1:
                searchStrategy = new RecipeSearchShowAll();
                break;
            case 2:
                Ingredient ingredient = askUserToSelectAnIngredient();
                if (ingredient == null) {
                    break;
                }
                searchStrategy = new RecipeSearchByIngredient(ingredient);
                break;
            case 3:
                Double maxPrice = askUserForAMaxPrice();
                searchStrategy = new RecipeSearchByMaxPrice(maxPrice);
                break;
            case 0:
                System.out.println("you pressed 0");
                return;
            }
        }

        for (String recipeName : recipeBook.findRecipesByStrategy(searchStrategy)) {
            System.out.println(recipeName);
        }
    }

    private Ingredient askUserToSelectAnIngredient() {
        while (true) {
            System.out.println("Enter an ingredient name");
            System.out.println("(or an empty string to abort)");
            String ingredientName = scan.nextLine();
            if (ingredientName.isEmpty()) {
                return null;
            }
            if (!ingredientList.hasIngredient(ingredientName)) {
                System.out.println("Ingredient '" + ingredientName + "' not found");
                continue;
            }
            return ingredientList.getIngredient(ingredientName);
        }
    }

    private Double askUserForAMaxPrice() {
        Double maxPrice = -1.0;
        String maxPriceInput;
        while (maxPrice < 0.0) {
            System.out.println("Enter a maximum price");
            maxPriceInput = scan.nextLine();
            try {
                maxPrice = Double.parseDouble(maxPriceInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid pice: " + maxPriceInput);
                maxPrice = -1.0;
            }
        }
        return maxPrice;
    }

    private int askUserForIntAboveZero(String prompt) {
        int result = -1;
        String input;
        while (result < 0) {
            System.out.println(prompt);
            input = scan.nextLine();
            try {
                result = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid value: " + input);
                System.out.println("Enter an integer value above zero");
                result = -1;
            }
        }
        return result;

    }

    private Recipe askUserToSelectRecipeByName() {
        while (true) {
            System.out.println("Enter a recipe name");
            System.out.println("(or an empty string to abort)");
            String name = scan.nextLine();
            if (name.isEmpty()) {
                return null;
            }
            if (!recipeBook.hasRecipe(name)) {
                System.out.println("Recipe '" + name + "' not found");
                continue;
            }
            return recipeBook.getRecipe(name);
        }
    }

    private void showRecipe() {
        System.out.println("  ____________");
        System.out.println("  |           |");
        System.out.println("  |Show Recipe|");
        System.out.println("  |___________|");

        Recipe recipe = askUserToSelectRecipeByName();
        if (recipe == null) {
            return;
        }

        int portions = askUserForIntAboveZero("For how many " + recipe.getUnitOfMeasure() + "?" + System.lineSeparator()
                + "(default " + recipe.getSizes() + ")");

        Double factor = Double.valueOf(portions) / Double.valueOf(recipe.getSizes());

        System.out.println(recipe.getName());
        System.out.println(portions + " " + recipe.getUnitOfMeasure());

        DecimalFormat numberFormat = new DecimalFormat("0.00");
        System.out.println(numberFormat.format(recipe.getPrice() * factor) + " kr");

        for (RecipeIngredient i : recipe.ingredients) {
            Double value = i.getValue() * factor;
            String prettyValue = "";
            if (i.getUnitOfMeasure().equals("st")) {
                prettyValue = String.valueOf((int) Math.ceil(value));
            } else {
                prettyValue = numberFormat.format(value);
            }

            System.out.println(prettyValue + " " + i.getUnitOfMeasure() + " " + i.getName() + " " + i.getComment());
        }
        for (String s : recipe.instructions) {
            System.out.println(s);
        }
    }

    private void addNewRecipe() {
        System.out.println("  _______________");
        System.out.println("  |              |");
        System.out.println("  |Add New Recipe|");
        System.out.println("  |______________|");

        String recipeName = "";
        while (recipeName.isEmpty()) {
            System.out.println("Enter a recipe name");
            System.out.println("(or an empty string to abort)");
            recipeName = scan.nextLine();
            if (recipeName.isEmpty()) {
                return;
            }
            if (recipeBook.hasRecipe(recipeName)) {
                System.out.println("That recipe already exists");
                recipeName = "";
            }
        }

        System.out.println("Enter number of portions/pieces this recipe makes");
        String portionsInput = "";
        Integer portions = -1;
        while (portions < 0.0) {
            portionsInput = scan.nextLine();
            try {
                portions = Integer.parseInt(portionsInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number: " + portionsInput);
                portions = -1;
            }
        }

        String unitOfMeasure = "";
        while (unitOfMeasure.isEmpty()) {
            System.out.println("Enter the unit of measure to use, like ´portions´ or ´pieces´ for the above number");
            System.out.println("(or an empty string to abort)");
            unitOfMeasure = scan.nextLine();
            if (unitOfMeasure.isEmpty()) {
                return;
            }
        }

        Recipe recipe = new Recipe(recipeName, portions, unitOfMeasure);

        boolean ingredientsDone = false;
        String ingredientName = "";
        while (!ingredientsDone) {
            System.out.println("Enter the name of an ingredient to add");
            System.out.println("(or an empty string to finish adding ingredients)");
            ingredientName = scan.nextLine();
            if (ingredientName.isEmpty()) {
                ingredientsDone = true;
                continue;
            }
            if (!ingredientList.hasIngredient(ingredientName)) {
                System.out.println("That ingredient does not exist");
                continue;
            }

            Ingredient ingredient = ingredientList.getIngredient(ingredientName);

            System.out.println("How many [" + ingredient.getUnitOfMeasure() + "] of that ingredient is used?");
            String ingredientMeasureInput = "";
            Integer ingredientMeasure = -1;

            while (ingredientMeasure < 0) {
                ingredientMeasureInput = scan.nextLine();
                try {
                    ingredientMeasure = Integer.parseInt(ingredientMeasureInput);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid value: " + ingredientMeasureInput);
                    ingredientMeasure = -1;
                }
            }

            System.out.println("Enter the ingredient comment");
            String comment = scan.nextLine();

            recipe.addIngredient(ingredient, ingredient.getUnitOfMeasure(), ingredientMeasure, comment);
        }

        boolean instructionsDone = false;
        String instruction = "";
        while (!instructionsDone) {
            System.out.println("Enter a instruction for the recipe");
            System.out.println("(or an empty string to finish adding instructions)");
            instruction = scan.nextLine();
            if (instruction.isEmpty()) {
                instructionsDone = true;
                continue;
            }
            recipe.addInstruction(instruction);
        }

        recipeBook.addRecipe(recipe);
    }

    private void deleteRecipe() {
        System.out.println("  ____________");
        System.out.println("  |           |");
        System.out.println("  |Show Recipe|");
        System.out.println("  |___________|");

        Recipe recipe = askUserToSelectRecipeByName();
        if (recipe == null) {
            return;
        }

        recipeBook.deleteRecipe(recipe.getName());
    }
    // #endregion Recipes

    // #region Ingredients
    private void showIngredientMenu() {
        int userOption;
        System.out.println("  _______________");
        System.out.println("  |              |");
        System.out.println("  | Ingredients  |");
        System.out.println("  |______________|");
        System.out.println("");
        System.out.println("1. List all available ingredients.");
        System.out.println("2. Look at the details of a particular ingredient.");
        System.out.println("3. Add a new ingredient.");
        System.out.println("4. Delete a particular ingredients (note that this may effect recipes)");
        System.out.println("0. Back to main menu.");
        String userInput = scan.nextLine();

        userOption = Integer.parseInt(userInput);

        switch (userOption) {
        case 1:
            listAllIngredients();
            break;
        case 2:
            showIngredient();
            break;
        case 3:
            addIngredient();
            break;
        case 4:
            deleteIngredient();
            break;
        case 0:
            return;
        }
    }

    private void listAllIngredients() {
        System.out.println("  _______________");
        System.out.println("  |              |");
        System.out.println("  | Ingredients  |");
        System.out.println("  |______________|");
        System.out.println("");
        for (String ingredientName : ingredientList.getIngredientNames()) {
            System.out.println(ingredientName);
        }
    }

    private void showIngredient() {
        System.out.println("What ingredient do you wanna show?");
        String ingredientName = scan.nextLine();
        if (!ingredientList.hasIngredient(ingredientName)) {
            System.out.println("____________________________");
            System.out.println("Ingredient not found");
            System.out.println("____________________________");
            return;
        }

        Ingredient ingredient = ingredientList.getIngredient(ingredientName);
        System.out.println("____________________________");
        System.out.println("Name: " + ingredientName);
        System.out.println("Measure: " + ingredient.getUnitOfMeasure());
        System.out.println("Price: " + ingredient.getPrice());
        System.out.println("____________________________");
    }

    private void addIngredient() {
        // Look at a particular recipe

        // user should be able to define the number of portions needed and the recipe
        // should be updated accordingly. Note that some ingredients cannot be divided,
        // for example eggs, this should be handled by the application rounding to the
        // nearest even divisor above the desired number of portions. The price of the
        // recipe should be calculated.

        // Enhet Förkortas Lika med
        // Deciliter dl 1 dl = 20 tsk = 100 ml
        // Matsked msk 1 msk = 3 tsk = 15 ml
        // Tesked tsk 1 tsk = 5 krm = 5 ml
        // Kryddmått krm 1 krm = 1 ml
        String ingredientName = "";
        while (ingredientName.isEmpty()) {
            System.out.println("Enter an ingredient name");
            System.out.println("(or an empty string to abort)");
            ingredientName = scan.nextLine();
            if (ingredientName.isEmpty()) {
                return;
            }
            if (ingredientList.hasIngredient(ingredientName)) {
                System.out.println("That ingredient already exists");
                ingredientName = "";
            }
        }

        String ingredientMeasure = "";
        while (ingredientMeasure.isEmpty()) {
            System.out.println("Enter an ingredient measure");
            System.out.println("[ml, g, st]");
            System.out.println("(or an empty string to abort)");
            ingredientMeasure = scan.nextLine();
            if (ingredientMeasure.isEmpty()) {
                return;
            }
            if (!UnitOfMeasure.validBaseMeasures.contains(ingredientMeasure)) {
                System.out.println("Invalid unit of measure: " + ingredientMeasure);
                ingredientMeasure = "";
            }
        }

        System.out.println("Enter an ingredient price, per unit of measure");
        String ingredientPriceInput = "";
        Double ingredientPrice = -1.0;

        while (ingredientPrice < 0.0) {
            ingredientPriceInput = scan.nextLine();
            try {
                ingredientPrice = Double.parseDouble(ingredientPriceInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid pice: " + ingredientPriceInput);
                ingredientPrice = -1.0;
            }
        }

        ingredientList.addIngredient(ingredientName, ingredientMeasure, ingredientPrice);
    }

    private void deleteIngredient() {
        Ingredient ingredient = null;
        String ingredientName = "";
        while (ingredient == null) {
            System.out.println("Enter name of ingredient to delete");
            System.out.println("(or an empty string to abort)");
            ingredientName = scan.nextLine();
            if (ingredientName.isEmpty()) {
                return;
            }
            if (!ingredientList.hasIngredient(ingredientName)) {
                System.out.println("That ingredient already exists");
            }
            ingredient = ingredientList.getIngredient(ingredientName);
        }

        recipeBook.deleteIngredient(ingredient);
    }
    // #endregion Ingredients
}
