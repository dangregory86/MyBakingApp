package gregory.dan.mybakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import gregory.dan.mybakingapp.recipe_objects.CookingStep;
import gregory.dan.mybakingapp.recipe_objects.Ingredient;
import gregory.dan.mybakingapp.utilities.InstructionViewAdapter;

/**
 * An activity representing a list of RecipeInstructions. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeInstructionDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeInstructionListActivity extends AppCompatActivity implements InstructionViewAdapter.ListItemClickListener{

    public final static String INTENT_EXTRA_INGREDIENTS = "Ingredients";
    public final static String INTENT_EXTRA_STEPS = "steps";
    public final static String INTENT_EXTRA_NAME = "name";

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    public ArrayList<Ingredient> mIngredients;
    public ArrayList<CookingStep> mSteps;
    public String recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipeinstruction_list);



        Intent intent = getIntent();
        if(intent != null){
            mIngredients = intent.getParcelableArrayListExtra(INTENT_EXTRA_INGREDIENTS);
            mSteps = intent.getParcelableArrayListExtra(INTENT_EXTRA_STEPS);
            recipeName = intent.getStringExtra(INTENT_EXTRA_NAME);
        }

        if (findViewById(R.id.recipeinstruction_detail_container) != null) {
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.recipeinstruction_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private String[] instructionList(){
        String[] instructions = new String[mSteps.size() + 1];
        instructions[0] = INTENT_EXTRA_INGREDIENTS;
        for(int i = 1; i < mSteps.size(); i++){
            instructions[i] = mSteps.get(i -1).getShortDescription();
        }
        return instructions;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new InstructionViewAdapter(instructionList(), this));
    }

    @Override
    public void onClick(int item) {
        if (mTwoPane) {
            if(item == 0){
                Bundle arguments = new Bundle();
                arguments.putParcelableArrayList(IngredientFragment.INGREDIENTS_LIST_FRAGMENT, mIngredients);
                IngredientFragment fragment = new IngredientFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipeinstruction_detail_container, fragment)
                        .commit();
            }else{
                Bundle arguments = new Bundle();
                arguments.putParcelable(RecipeInstructionDetailFragment.ARG_ITEM_ID, mSteps.get(item - 1));
                RecipeInstructionDetailFragment fragment = new RecipeInstructionDetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipeinstruction_detail_container, fragment)
                        .commit();
            }

        } else {
            if(item == 0) {
                Context context = this;
                Intent intent = new Intent(context, IngredientFragmentActivity.class);
                intent.putExtra(IngredientFragment.INGREDIENTS_LIST_FRAGMENT, mIngredients);

                context.startActivity(intent);
            }else {
                Context context = this;
                Intent intent = new Intent(context, RecipeInstructionDetailActivity.class);
                intent.putExtra(RecipeInstructionDetailFragment.ARG_ITEM_ID, mSteps.get(item - 1));

                context.startActivity(intent);
            }
        }
    }


}
