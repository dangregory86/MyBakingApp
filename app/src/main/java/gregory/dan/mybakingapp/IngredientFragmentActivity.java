package gregory.dan.mybakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;

import gregory.dan.mybakingapp.recipe_objects.CookingStep;

/**
 * Created by Daniel Gregory on 31/07/2018.
 */
public class IngredientFragmentActivity extends AppCompatActivity {

    public ArrayList<CookingStep> mSteps;
    public String recipeName;
    public int selected;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipeinstruction_detail);
        Intent intent = getIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(intent != null){
            mSteps = intent.getParcelableArrayListExtra(RecipeInstructionDetailFragment.ARG_ITEM_ID);
            recipeName = intent.getStringExtra(RecipeInstructionListActivity.INTENT_EXTRA_NAME);
            selected = intent.getIntExtra(RecipeInstructionListActivity.SELECTED_STEP, 0);
        }

        if(savedInstanceState == null){

            Bundle arguments = new Bundle();
            arguments.putString(RecipeInstructionListActivity.INTENT_EXTRA_NAME,
                   recipeName);
            IngredientFragment fragment = new IngredientFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipeinstruction_detail_container, fragment)
                    .commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, RecipeInstructionListActivity.class);
                intent.putExtra(RecipeInstructionListActivity.SELECTED_STEP, selected);
                intent.putExtra(RecipeInstructionListActivity.INTENT_EXTRA_NAME, recipeName);
                intent.putExtra(RecipeInstructionListActivity.INTENT_EXTRA_STEPS, mSteps);
                startActivity(intent);
                break;
        }
        return true;
    }
}
