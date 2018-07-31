package gregory.dan.mybakingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import gregory.dan.mybakingapp.recipe_objects.Recipe;
import gregory.dan.mybakingapp.utilities.MyJsonParser;
import gregory.dan.mybakingapp.utilities.MyMainRecyclerViewAdapter;
import gregory.dan.mybakingapp.utilities.MyRecipeFetcher;

public class MainActivity extends AppCompatActivity implements MyMainRecyclerViewAdapter.ListItemClickListener{

    private static final String RECIPE_URI_STRING = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private ArrayList<Recipe> mRecipes;
    RecyclerView mRecyclerView;
    MyMainRecyclerViewAdapter viewAdapter;

    //TODO implement a database for offline access to instructions
    //TODO update the views to match the design
    //TODO create a widget including a service to move onto the next instruction and displays ingredients list
    //TODO get the videos working with exoplayer on the detail fragment
    //TODO use butterknife
    //TODO setup expresso test files



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.activity_main_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewAdapter = new MyMainRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(viewAdapter);

        getDataFromNetwork();
    }

    private void getDataFromNetwork() {
        new MyNetworkTasker().execute(RECIPE_URI_STRING);
    }

    @Override
    public void onClick(int item) {
        Intent intent = new Intent(MainActivity.this, RecipeInstructionListActivity.class);
        intent.putExtra(RecipeInstructionListActivity.INTENT_EXTRA_NAME, mRecipes.get(item).getRecipeName());
        intent.putExtra(RecipeInstructionListActivity.INTENT_EXTRA_INGREDIENTS, mRecipes.get(item).getIngredients());
        intent.putExtra(RecipeInstructionListActivity.INTENT_EXTRA_STEPS, mRecipes.get(item).getCookingSteps());
        startActivity(intent);
    }


    public class MyNetworkTasker extends AsyncTask<String, Void, ArrayList<Recipe>> {


        public MyNetworkTasker(){
        }


        @Override
        protected ArrayList<Recipe> doInBackground(String... params) {
            if(params.length == 0){
                return null;
            }
            URL url;
            String results;
            try {
                url = new URL(params[0]);
                results = MyRecipeFetcher.getResponseFromHttpUrl(url);
                return MyJsonParser.getRecipesFromJson(MainActivity.this, results);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> recipes) {
            setNewData(recipes);
        }
    }

    private void setNewData(ArrayList<Recipe> recipes) {
        mRecipes = recipes;
        viewAdapter.setRecipeData(recipes);
    }

}
