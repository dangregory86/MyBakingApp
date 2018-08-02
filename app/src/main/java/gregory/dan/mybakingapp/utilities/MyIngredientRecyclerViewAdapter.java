package gregory.dan.mybakingapp.utilities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import gregory.dan.mybakingapp.R;
import gregory.dan.mybakingapp.database.IngredientItem;


public class MyIngredientRecyclerViewAdapter extends RecyclerView.Adapter<MyIngredientRecyclerViewAdapter.ViewHolder> {

    private static ArrayList<IngredientItem> mIngredients;

    public MyIngredientRecyclerViewAdapter(ArrayList<IngredientItem> mIngredients) {
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
        holder.mIngredientMeasure.setText(mIngredients.get(position).measure);
        holder.mIngredientText.setText(mIngredients.get(position).ingredient);
        holder.mIngredientAmount.setText(String.valueOf(mIngredients.get(position).quantity));

    }

    @Override
    public int getItemCount() {
        if(mIngredients == null){
            return 0;
        }
        return mIngredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIngredientAmount;
        public final TextView mIngredientMeasure;
        public final TextView mIngredientText;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIngredientAmount = view.findViewById(R.id.ingridient_list_item_amount);
            mIngredientMeasure = view.findViewById(R.id.ingridient_list_item_measure);
            mIngredientText = view.findViewById(R.id.ingredient_list_item_ingredient);
        }

    }

    public void setData(ArrayList<IngredientItem> ingredientItems){
        mIngredients = ingredientItems;
        notifyDataSetChanged();
    }
}
