package TheRecipeApp;

public class RecipeIngredient {
    private Ingredient ingredient;
    private String unitOfMeasure;
    private int value;
    private String comment;
    private final String FIELD_DELIMETER = ", ";

    public RecipeIngredient(Ingredient ingredient, String unitOfMeasure, int value, String comment) {
        this.value = value;
        this.unitOfMeasure = unitOfMeasure;
        this.ingredient = ingredient;
        this.comment = comment;
    }

    public String getName() {
        return ingredient.getName();
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public int getValue() {
        return value;
    }

    public String getComment() {
        return comment;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Double getPrice() {
        switch (ingredient.getUnitOfMeasure()) {
            case "ml":
                switch (unitOfMeasure) {
                    case "ml":
                        return value * ingredient.getPrice();
                    case "cl":
                        return 10 * value * ingredient.getPrice();
                    case "dl":
                        return 100 * value * ingredient.getPrice();
                    case "l":
                        return 1000 * value * ingredient.getPrice();
                    default:
                        return 0.0;
                }
            case "g":
                switch (unitOfMeasure) {
                    case "g":
                        return value * ingredient.getPrice();
                    case "hg":
                        return 100 * value * ingredient.getPrice();
                    case "kg":
                        return 1000 * value * ingredient.getPrice();
                    default:
                        return 0.0;
                }
            case "st":
                return value * ingredient.getPrice();
            default:
                return 0.0;
        }

    }

    @Override
    public String toString() {
        return String.join(FIELD_DELIMETER, String.valueOf(value), unitOfMeasure, this.getName(), this.getComment());
    }
}
