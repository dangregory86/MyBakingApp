package gregory.dan.mybakingapp.database;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by Daniel Gregory on 02/08/2018.
 */
public class IngredientRepository {


    private IngredientDao ingredientDao;
    private static List<IngredientItem> mIngredientItems;

    public IngredientRepository(Application application) {
        IngredientDatabase ingredientDatabase = IngredientDatabase.getDatabase(application);
        ingredientDao = ingredientDatabase.ingredientDao();
//        new GetAll(ingredientDao).execute();
    }

    public void insertIngredient(IngredientItem ingredient) {
        new InsertAsyncTask(ingredientDao).execute(ingredient);
    }

    public List<IngredientItem> getRecipeIngredients(){
        return ingredientDao.getIngredients();
    }

    public void deleteAll(){
        new DeleteAllAsyncTask(ingredientDao).execute();
    }

    private static class InsertAsyncTask extends AsyncTask<IngredientItem, Void, Void> {
        //get a dao for the async task
        private IngredientDao aSyncIngredientDao;

        InsertAsyncTask(IngredientDao dao) {
            aSyncIngredientDao = dao;
        }

        @Override
        protected Void doInBackground(IngredientItem... params) {
            aSyncIngredientDao.insert(params[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        //get a dao for the async task
        private IngredientDao aSyncIngredientDao;

        DeleteAllAsyncTask(IngredientDao dao) {
            aSyncIngredientDao = dao;
        }

        @Override
        protected Void doInBackground(Void... params) {
            aSyncIngredientDao.deleteAll();
            return null;
        }
    }

//    private static class GetAll extends AsyncTask<Void, Void, List<IngredientItem> >{
//        //get a dao for the async task
//        private IngredientDao aSyncIngredientDao;
//
//        GetAll(IngredientDao dao) {
//            aSyncIngredientDao = dao;
//        }
//
//        @Override
//        protected List<IngredientItem> doInBackground(Void... params) {
//            return aSyncIngredientDao.getIngredients();
//        }
//
//        @Override
//        protected void onPostExecute(List<IngredientItem> ingredientItems) {
//            mIngredientItems = ingredientItems;
//        }
//    }


}
