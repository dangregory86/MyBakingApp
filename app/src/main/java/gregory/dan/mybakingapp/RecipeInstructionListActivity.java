package gregory.dan.mybakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import gregory.dan.mybakingapp.recipe_objects.CookingStep;
import gregory.dan.mybakingapp.utilities.InstructionViewAdapter;

public class RecipeInstructionListActivity extends AppCompatActivity implements InstructionViewAdapter.ListItemClickListener {

    public final static String INTENT_EXTRA_INGREDIENTS = "Ingredients";
    public final static String INTENT_EXTRA_STEPS = "steps";
    public final static String INTENT_EXTRA_NAME = "name";
    public static final String SELECTED_STEP = "selected_step";
    public static final String TWO_PANE_BOOLEAN = "two_pane_boolean";

    private boolean mTwoPane;
    public ArrayList<CookingStep> mSteps;
    public String recipeName;
    private int selectedStep;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipeinstruction_list);

        mRecyclerView = findViewById(R.id.recipeinstruction_list);



        Intent intent = getIntent();
        if (intent != null) {
            mSteps = intent.getParcelableArrayListExtra(INTENT_EXTRA_STEPS);
            recipeName = intent.getStringExtra(INTENT_EXTRA_NAME);
            selectedStep = intent.getIntExtra(SELECTED_STEP, 0);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(recipeName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(savedInstanceState != null){
            selectedStep = savedInstanceState.getInt("current_item");
        }

        if (findViewById(R.id.recipeinstruction_detail_container) != null) {
            mTwoPane = true;
            if(savedInstanceState == null){
                Bundle arguments = new Bundle();
                arguments.putString(RecipeInstructionListActivity.INTENT_EXTRA_NAME, recipeName);
                IngredientFragment fragment = new IngredientFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipeinstruction_detail_container, fragment)
                        .commit();
            }
        }
        assert mRecyclerView != null;
        setupRecyclerView(mRecyclerView);



    }

    private String[] instructionList() {
        String[] instructions = new String[mSteps.size() + 1];
        instructions[0] = INTENT_EXTRA_INGREDIENTS;
        for (int i = 0; i < mSteps.size(); i++) {
            instructions[i + 1] = mSteps.get(i).getShortDescription();
        }
        return instructions;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("current_item", selectedStep);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new InstructionViewAdapter(instructionList(), this, selectedStep));
    }

    @Override
    public void onClick(int item) {
        if (mTwoPane) {
            if (item == 0) {
                selectedStep = item;
                Bundle arguments = new Bundle();
                arguments.putString(RecipeInstructionListActivity.INTENT_EXTRA_NAME, recipeName);
                IngredientFragment fragment = new IngredientFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipeinstruction_detail_container, fragment)
                        .commit();
            } else {
                selectedStep = item;
                Bundle arguments = new Bundle();
                arguments.putParcelableArrayList(RecipeInstructionDetailFragment.ARG_ITEM_ID, mSteps);
                arguments.putInt(RecipeInstructionDetailFragment.RECIPE_STEP, item - 1);
                arguments.putBoolean(TWO_PANE_BOOLEAN, mTwoPane);
                RecipeInstructionDetailFragment fragment = new RecipeInstructionDetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipeinstruction_detail_container, fragment)
                        .commit();
            }

        } else {
            if (item == 0) {
                Context context = this;
                Intent intent = new Intent(context, IngredientFragmentActivity.class);
                intent.putExtra(RecipeInstructionListActivity.INTENT_EXTRA_NAME, recipeName);
                intent.putExtra(RecipeInstructionDetailFragment.ARG_ITEM_ID, mSteps);
                intent.putExtra(SELECTED_STEP, item);
                context.startActivity(intent);
            } else {
                Context context = this;
                Intent intent = new Intent(context, RecipeInstructionDetailActivity.class);
                intent.putExtra(RecipeInstructionDetailFragment.ARG_ITEM_ID, mSteps);
                intent.putExtra(RecipeInstructionDetailFragment.RECIPE_STEP, item - 1);
                intent.putExtra(RecipeInstructionDetailFragment.RECIPE_NAME, recipeName);
                intent.putExtra(SELECTED_STEP, item);
                context.startActivity(intent);
            }
        }
    }


}
