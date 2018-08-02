package gregory.dan.mybakingapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import gregory.dan.mybakingapp.database.IngredientItem;
import gregory.dan.mybakingapp.database.IngredientViewModel;
import gregory.dan.mybakingapp.utilities.MyIngredientRecyclerViewAdapter;

public class IngredientFragment extends Fragment {

    public static final String INGREDIENTS_LIST_FRAGMENT = "ingredients";
    private  static String recipeName;
    private static ArrayList<IngredientItem> ingredients;
    IngredientViewModel ingredientViewModel;
    MyIngredientRecyclerViewAdapter myIngredientRecyclerViewAdapter;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public IngredientFragment() {
    }

    public static IngredientFragment newInstance(int columnCount) {
        IngredientFragment fragment = new IngredientFragment();
        Bundle args = new Bundle();
//        args.putParcelableArrayList(INGREDIENTS_LIST_FRAGMENT, ingredients);
        args.putString(RecipeInstructionListActivity.INTENT_EXTRA_NAME, recipeName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ingredientViewModel = ViewModelProviders.of(this).get(IngredientViewModel.class);
        if (getArguments() != null) {
            recipeName = getArguments().getString(RecipeInstructionListActivity.INTENT_EXTRA_NAME);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            new GetIngredients(ingredientViewModel).execute();
            myIngredientRecyclerViewAdapter = new MyIngredientRecyclerViewAdapter(ingredients);
            recyclerView.setAdapter(myIngredientRecyclerViewAdapter);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void setTheData(ArrayList<IngredientItem> ingredientItems){
        myIngredientRecyclerViewAdapter.setData(ingredientItems);
    }

    private class GetIngredients extends AsyncTask<Void, Void, List<IngredientItem>>{

        IngredientViewModel mIngredientViewModel;

        public GetIngredients(IngredientViewModel ingredientViewModel){
            mIngredientViewModel = ingredientViewModel;
        }

        @Override
        protected List<IngredientItem> doInBackground(Void... voids) {
            return mIngredientViewModel.getIngredients();
        }

        @Override
        protected void onPostExecute(List<IngredientItem> ingredientItems) {
            ArrayList<IngredientItem> ingredientItems1 = new ArrayList<>();
            for(IngredientItem ingredientItem: ingredientItems){
                if(ingredientItem.recipeName.equals(recipeName)){
                    ingredientItems1.add(ingredientItem);
                }
            }
            ingredients = ingredientItems1;
            setTheData(ingredients);
        }
    }
}
