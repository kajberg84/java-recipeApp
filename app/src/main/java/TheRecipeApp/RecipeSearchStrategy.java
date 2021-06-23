package TheRecipeApp;

/**
 * Common interface for all strategies.
 */
public interface RecipeSearchStrategy {
    boolean matches(Recipe recipe);
}