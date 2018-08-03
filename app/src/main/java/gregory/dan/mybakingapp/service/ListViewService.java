package gregory.dan.mybakingapp.service;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import gregory.dan.mybakingapp.R;
import gregory.dan.mybakingapp.database.IngredientItem;
import gregory.dan.mybakingapp.database.IngredientViewModel;

import static gregory.dan.mybakingapp.RecipeInstructionDetailFragment.RECIPE_NAME;

/**
 * Created by Daniel Gregory on 02/08/2018.
 */
public class ListViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListViewServiceInner(getApplicationContext(), intent);
    }

    class ListViewServiceInner implements RemoteViewsService.RemoteViewsFactory {

        Context context;
        ArrayList<IngredientItem> ingredients;
        private int appWidegetId;
        private String recipeName;
        private IngredientViewModel ingredientViewModel;

        public ListViewServiceInner(Context context, Intent intent) {
            this.context = context;
            this.appWidegetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            this.recipeName = intent.getStringExtra(RECIPE_NAME);

        }

        @Override
        public void onCreate() {
            ingredientViewModel = new IngredientViewModel(getApplication());
            new MyNetworkTasker(recipeName).execute(recipeName);
        }

        @Override
        public void onDataSetChanged() {
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (ingredients != null) {
                return ingredients.size();
            }
            return 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
            if (ingredients != null) {
                remoteViews.setTextViewText(R.id.widget_list_item_ammount_text_view, String.valueOf(ingredients.get(position).quantity));
                remoteViews.setTextViewText(R.id.widget_list_item_measure_text_view, ingredients.get(position).measure);
                remoteViews.setTextViewText(R.id.widget_list_item_ingredient_text_view, ingredients.get(position).ingredient);
                return remoteViews;
            }
            return null;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }


        public class MyNetworkTasker extends AsyncTask<String, Void, List<IngredientItem>> {

            IngredientViewModel ingredientViewModel;
            String recipeName;

            public MyNetworkTasker(String mRecipeName) {
                ingredientViewModel = new IngredientViewModel(getApplication());
                recipeName = mRecipeName;
            }


            @Override
            protected List<IngredientItem> doInBackground(String... params) {
                return ingredientViewModel.getIngredients();
            }

            @Override
            protected void onPostExecute(List<IngredientItem> recipes) {

                ArrayList<IngredientItem> ingredientItems = new ArrayList<>();
                if(recipes != null) {
                    for (IngredientItem ingredientItem : recipes) {
                        if (ingredientItem.recipeName.equalsIgnoreCase(recipeName)) {
                            ingredientItems.add(ingredientItem);
                        }
                    }
                }
                ingredients = ingredientItems;
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidegetId, R.id.widget_list_view);

            }
        }
    }
}
