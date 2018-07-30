package gregory.dan.mybakingapp.utilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import gregory.dan.mybakingapp.R;
import gregory.dan.mybakingapp.recipe_objects.Recipe;

/**
 * Created by Daniel Gregory on 30/07/2018.
 */
public class MyMainRecyclerViewAdapter extends RecyclerView.Adapter<MyMainRecyclerViewAdapter.RecipeViewHolder> {

    private ArrayList<Recipe> recipeList;
    private ListItemClickListener mClickListener;

    public interface ListItemClickListener {
        void onClick(int item);
    }

    public MyMainRecyclerViewAdapter(ListItemClickListener listItemClickListener) {
        mClickListener = listItemClickListener;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView recipeImageView;
        TextView recipeTextView;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeImageView = itemView.findViewById(R.id.activity_main_list_item_image_view);
            recipeTextView = itemView.findViewById(R.id.activity_main_list_item_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.activity_main_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutId, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe mRecipe = recipeList.get(position);
        holder.recipeImageView.setImageResource(getImageResID(position));
        holder.recipeTextView.setText(mRecipe.getRecipeName());
    }

    private int getImageResID(int position) {
        switch (position) {
            case 0:
                return R.drawable.nutella_pie;
            case 1:
                return R.drawable.brownie;
            case 2:
                return R.drawable.yellow_cake;
            case 3:
                return R.drawable.cheescake;
            default:
                return 0;
        }
    }

    @Override
    public int getItemCount() {
        if (recipeList != null) {
            return recipeList.size();
        }
        return 0;
    }

    public void setRecipeData(ArrayList<Recipe> recipes) {
        recipeList = recipes;
        notifyDataSetChanged();
    }

}
