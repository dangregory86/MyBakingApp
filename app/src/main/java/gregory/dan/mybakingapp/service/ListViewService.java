package gregory.dan.mybakingapp.service;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import gregory.dan.mybakingapp.R;
import gregory.dan.mybakingapp.RecipeWidget;
import gregory.dan.mybakingapp.database.IngredientItem;

/**
 * Created by Daniel Gregory on 02/08/2018.
 */
class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListViewService(this.getApplicationContext());
    }
}

public class ListViewService implements RemoteViewsService.RemoteViewsFactory {

    static Context context;
    static ArrayList<IngredientItem> ingredients;

    public ListViewService(Context context){
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(ingredients != null){
            return ingredients.size();
        }
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if(ingredients!= null){

        }
        return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    public static void updateData(ArrayList<IngredientItem> ingredientItems) {

        ingredients = ingredientItems;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, RecipeWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);



    }
}
