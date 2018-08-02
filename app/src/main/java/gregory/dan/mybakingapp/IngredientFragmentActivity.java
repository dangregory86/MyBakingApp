package gregory.dan.mybakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Daniel Gregory on 31/07/2018.
 */
public class IngredientFragmentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipeinstruction_detail);

        if(savedInstanceState == null){
            Intent intent = getIntent();
            Bundle arguments = new Bundle();
            arguments.putString(RecipeInstructionListActivity.INTENT_EXTRA_NAME,
                   intent.getStringExtra(RecipeInstructionListActivity.INTENT_EXTRA_NAME));
            IngredientFragment fragment = new IngredientFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipeinstruction_detail_container, fragment)
                    .commit();
        }

    }
}
