package TheRecipeApp;

public class RecipeSearchShowAll implements RecipeSearchStrategy {
    /**
     * Show all recipes.
     */
    @Override
    public boolean matches(Recipe recipe) {
        return true;
    }
}