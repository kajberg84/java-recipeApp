package TheRecipeApp;

public class RecipeSearchByIngredient implements RecipeSearchStrategy {
    private Ingredient requiredIngredient;

    public RecipeSearchByIngredient(Ingredient requiredIngredient) {
        this.requiredIngredient = requiredIngredient;
    }

    /**
     * Show all recipes containing the required ingredient.
     */
    @Override
    public boolean matches(Recipe recipe) {
        return recipe.usesIngredient(requiredIngredient);
    }
}