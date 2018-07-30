package gregory.dan.mybakingapp.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import gregory.dan.mybakingapp.recipe_objects.CookingStep;
import gregory.dan.mybakingapp.recipe_objects.Ingredient;
import gregory.dan.mybakingapp.recipe_objects.Recipe;

/**
 * Created by Daniel Gregory on 30/07/2018.
 */
public class MyJsonParser {

    //the outer object key strings
    private final static String RECIPE_NAME_KEY = "name";
    private final static String RECIPE_INGREDIENTS_KEY = "ingredients";
    private final static String RECIPE_STEPS_KEY = "steps";
    private final static String RECIPE_SERVES_KEY = "servings";

    //the ingredients key strings
    private final static String INGREDIENT_NAME_KEY = "ingredient";
    private final static String INGREDIENT_MEASURE_KEY = "measure";
    private final static String INGREDIENT_QUANTITY_KEY = "quantity";

    //the step key strings
    private final static String STEP_COUNTER_KEY = "id";
    private final static String STEP_SHORT_DESCRIPTION_KEY = "shortDescription";
    private final static String STEP_DESCRIPTION_KEY = "description";
    private final static String STEP_VIDEO_URL_KEY = "videoURL";

    public static ArrayList<Recipe> getRecipesFromJson(Context context, String recipeJson) throws JSONException {
        ArrayList<Recipe> recipes;

        JSONArray resultArray = new JSONArray(recipeJson);

        recipes = new ArrayList<Recipe>(resultArray.length());

        for(int i = 0; i < resultArray.length(); i++){
            JSONObject recipe = resultArray.getJSONObject(i);
            String recipeName = recipe.getString(RECIPE_NAME_KEY);
            int recipeServes = recipe.getInt(RECIPE_SERVES_KEY);

            //create an ingredient arraylist
            JSONArray ingredientArray = recipe.getJSONArray(RECIPE_INGREDIENTS_KEY);
            ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>(ingredientArray.length());
            for(int j = 0; j < ingredientArray.length(); j++){
                JSONObject ingredientObject = ingredientArray.getJSONObject(j);
                double ingredientQuantity = ingredientObject.getDouble(INGREDIENT_QUANTITY_KEY);
                String ingredientMeasure = ingredientObject.getString(INGREDIENT_MEASURE_KEY);
                String ingredientName = ingredientObject.getString(INGREDIENT_NAME_KEY);

                ingredients.add(j, new Ingredient(ingredientQuantity, ingredientMeasure, ingredientName));
            }

            //create a cooking step array list
            JSONArray stepsArray = recipe.getJSONArray(RECIPE_STEPS_KEY);
            ArrayList<CookingStep> cookingSteps = new ArrayList<CookingStep>(stepsArray.length());
            for(int j = 0; j < stepsArray.length(); j++){
                JSONObject stepsObject = stepsArray.getJSONObject(j);
                int stepNumber = stepsObject.getInt(STEP_COUNTER_KEY) + 1;
                String shortDescription = stepsObject.getString(STEP_SHORT_DESCRIPTION_KEY);
                String description = stepsObject.getString(STEP_DESCRIPTION_KEY);
                String videoUrl = stepsObject.getString(STEP_VIDEO_URL_KEY);

                cookingSteps.add(j, new CookingStep(stepNumber, shortDescription, description, videoUrl));
            }
            recipes.add(i, new Recipe(recipeName, recipeServes, ingredients, cookingSteps));

        }
        return recipes;
    }
}
