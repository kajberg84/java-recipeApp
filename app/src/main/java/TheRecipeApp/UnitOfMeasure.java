package TheRecipeApp;

import java.util.ArrayList;
import java.util.List;

public class UnitOfMeasure {
    public static List<String> validMeasures = new ArrayList<String>(
            List.of("l", "dl", "cl", "ml", "msk", "tsk", "krm", "kg", "hg", "g", "st"));
    public static List<String> validBaseMeasures = new ArrayList<String>(
            List.of("ml", "g", "st"));
}