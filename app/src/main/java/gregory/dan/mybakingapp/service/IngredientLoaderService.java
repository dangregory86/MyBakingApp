package gregory.dan.mybakingapp.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by Daniel Gregory on 02/08/2018.
 */
public class IngredientLoaderService extends IntentService {

    public static final String ACTION_NEXT_RECIPE = "gregory.dan.mybakingapp.action.action-next-recipe";
    public static final String ACTION_INTENT_EXTRA_NAME = "recipe_name";

    public IngredientLoaderService() {
        super("Default");
    }

    public IngredientLoaderService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_NEXT_RECIPE.equals(action)) {
                String recipeName = intent.getStringExtra(ACTION_INTENT_EXTRA_NAME);
                handleActionNextRecipe(recipeName);
            }
        }
    }

    private void handleActionNextRecipe(String recipeName) {


    }

    public static void startActionNextRecipe(Context context) {
        Intent intent = new Intent(context, IngredientLoaderService.class);
        intent.setAction(ACTION_NEXT_RECIPE);
        context.startService(intent);
    }


}
