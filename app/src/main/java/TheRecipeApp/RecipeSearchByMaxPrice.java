package TheRecipeApp;

public class RecipeSearchByMaxPrice implements RecipeSearchStrategy {
    private Double maxPrice;

    public RecipeSearchByMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    /**
     * Show all recipes costing up to the max value.
     */
    @Override
    public boolean matches(Recipe recipe) {
        return recipe.getPrice() <= maxPrice;
    }
}