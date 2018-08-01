package gregory.dan.mybakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import gregory.dan.mybakingapp.recipe_objects.Ingredient;
import gregory.dan.mybakingapp.utilities.MyIngredientRecyclerViewAdapter;

public class IngredientFragment extends Fragment {

    public static final String INGREDIENTS_LIST_FRAGMENT = "ingredients";
    private static ArrayList<Ingredient> ingredients;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public IngredientFragment() {
    }

    public static IngredientFragment newInstance(int columnCount) {
        IngredientFragment fragment = new IngredientFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(INGREDIENTS_LIST_FRAGMENT, ingredients);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            ingredients = getArguments().getParcelableArrayList(INGREDIENTS_LIST_FRAGMENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView && ingredients != null) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
                recyclerView.setLayoutManager(new LinearLayoutManager(context));

            recyclerView.setAdapter(new MyIngredientRecyclerViewAdapter(ingredients));
        }
        return view;
    }




}
