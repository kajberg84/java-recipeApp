/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package TheRecipeApp;

import org.junit.Test;
import static org.junit.Assert.*;

public class IngredientTest {
    @Test
    public void testIngredientConstructorAndGetters() {
        String name = "Wasabi";
        String unitOfMeasure = "g";
        double price = 10.0;

        Ingredient classUnderTest = new Ingredient(name, unitOfMeasure, price);
        assertEquals("name should be same", name, classUnderTest.getName());
        assertEquals("unitOfMeasure should be same", unitOfMeasure, classUnderTest.getUnitOfMeasure());
        assertEquals("price should be same", price, classUnderTest.getPrice(), 0.00001);
    }
}