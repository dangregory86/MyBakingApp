package gregory.dan.mybakingapp.recipe_objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Daniel Gregory on 30/07/2018.
 */
public class Recipe implements Parcelable {

    private String recipeName;
    private int recipeServes;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<CookingStep> cookingSteps;

    public Recipe(String recipeName, int recipeServes, ArrayList<Ingredient> ingredients, ArrayList<CookingStep> cookingSteps) {
        this.recipeName = recipeName;
        this.recipeServes = recipeServes;
        this.ingredients = ingredients;
        this.cookingSteps = cookingSteps;
    }

    protected Recipe(Parcel in) {
        recipeName = in.readString();
        recipeServes = in.readInt();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getRecipeName() {
        return recipeName;
    }

    public int getRecipeServes() {
        return recipeServes;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<CookingStep> getCookingSteps() {
        return cookingSteps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(recipeName);
        parcel.writeInt(recipeServes);
    }
}
