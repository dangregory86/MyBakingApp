package gregory.dan.mybakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import gregory.dan.mybakingapp.service.IngredientLoaderService;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    public int recipeNumber = 0;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setTextViewText(R.id.app_widget_text_view, "Banana sandwich");

        /*Intent wateringIntent = new Intent(context, PlantWateringService.class);
wateringIntent.setAction(PlantWateringService.ACTION_WATER_PLANTS);
PendingIntent wateringPendingIntent = PendingIntent.getService(
                                             context,
                                             0,
                                             wateringIntent,
                                             PendingIntent.FLAG_UPDATE_CURRENT);
views.setOnClickPendingIntent(R.id.widget_water_button, wateringPendingIntent);*/
        Intent nextRecipeIntent = new Intent(context, IngredientLoaderService.class);
        nextRecipeIntent.putExtra(IngredientLoaderService.ACTION_INTENT_EXTRA_NAME, "Brownie");
        nextRecipeIntent.setAction(IngredientLoaderService.ACTION_NEXT_RECIPE);
        PendingIntent nextPendingIntent = PendingIntent.getService(context, 0, nextRecipeIntent, PendingIntent.FLAG_UPDATE_CURRENT);


//        PendingIntent pendingIntent = new PendingIntent();




       views.setOnClickPendingIntent(R.id.app_widget_button, nextPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

