package gregory.dan.mybakingapp.utilities;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gregory.dan.mybakingapp.R;

/**
 * Created by Daniel Gregory on 31/07/2018.
 */
public final class InstructionViewAdapter
        extends RecyclerView.Adapter<InstructionViewAdapter.ViewHolder> {

    public ListItemClickListener mClickListener;
    private int mSelectedItem;

    public interface ListItemClickListener {
        void onClick(int item);
    }

    private final String[] instructions;

    public InstructionViewAdapter(String[] recipe, ListItemClickListener listItemClickListener, int selectedItem) {
        instructions = recipe;
        mClickListener = listItemClickListener;
        mSelectedItem = selectedItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipeinstruction_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mContentView.setText(instructions[position]);
        holder.mCardView.setBackgroundResource(R.color.color_primary_light);
        holder.itemView.setTag(position);
        if(position == mSelectedItem){
            holder.mCardView.setBackgroundResource(R.color.colorAccent);
        }
    }

    @Override
    public int getItemCount() {
        return instructions.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView mContentView;
        final CardView mCardView;
        ViewHolder(View view) {
            super(view);
            mContentView = (TextView) view.findViewById(R.id.content);
            mCardView = view.findViewById(R.id.step_list_item);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mSelectedItem = getAdapterPosition();
            mClickListener.onClick(getAdapterPosition());
            notifyDataSetChanged();
        }
    }
}
