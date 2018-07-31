package gregory.dan.mybakingapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import gregory.dan.mybakingapp.recipe_objects.Ingredient;


public class MyIngredientRecyclerViewAdapter extends RecyclerView.Adapter<MyIngredientRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Ingredient> mIngredients;

    public MyIngredientRecyclerViewAdapter(ArrayList<Ingredient> mIngredients) {
        this.mIngredients = mIngredients;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mIdView.setText(mIngredients.get(position).getMeasure());
        holder.mContentView.setText(mIngredients.get(position).getIngredient());

    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
