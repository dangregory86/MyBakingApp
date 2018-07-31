package gregory.dan.mybakingapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gregory.dan.mybakingapp.recipe_objects.CookingStep;

/**
 * A fragment representing a single RecipeInstruction detail screen.
 * This fragment is either contained in a {@link RecipeInstructionListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeInstructionDetailActivity}
 * on handsets.
 */
public class RecipeInstructionDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private CookingStep mCookingStep;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeInstructionDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCookingStep = getArguments().getParcelable(ARG_ITEM_ID);

        Activity activity = this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipeinstruction_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mCookingStep != null) {
            ((TextView) rootView.findViewById(R.id.detail_step_text_view)).setText(mCookingStep.getDescription());
        }

        return rootView;
    }
}
