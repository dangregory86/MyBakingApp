package gregory.dan.mybakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import gregory.dan.mybakingapp.recipe_objects.CookingStep;

/**
 * An activity representing a single RecipeInstruction detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeInstructionListActivity}.
 */
public class RecipeInstructionDetailActivity extends AppCompatActivity {

    public ArrayList<CookingStep> mSteps;
    public int cookingStepNumber;
    public String recipeName;
    public static final String TITLE_TEXT = " - Step number: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipeinstruction_detail);

        ActionBar actionBar = getSupportActionBar();


        if (savedInstanceState == null) {
            Intent intent = getIntent();
            mSteps = intent.getParcelableArrayListExtra(RecipeInstructionDetailFragment.ARG_ITEM_ID);
            cookingStepNumber = intent.getIntExtra(RecipeInstructionDetailFragment.RECIPE_STEP, 0);
            recipeName = intent.getStringExtra(RecipeInstructionDetailFragment.RECIPE_NAME);
            actionBar.setTitle(recipeName);
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putParcelableArrayList(RecipeInstructionDetailFragment.ARG_ITEM_ID,
                    mSteps);
            arguments.putInt(RecipeInstructionDetailFragment.RECIPE_STEP, cookingStepNumber);
            RecipeInstructionDetailFragment fragment = new RecipeInstructionDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipeinstruction_detail_container, fragment)
                    .commit();
        }
    }

}
