package gregory.dan.mybakingapp.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import java.util.List;

/**
 * Created by Daniel Gregory on 02/08/2018.
 */
public class IngredientViewModel extends AndroidViewModel {

    //get a repository
    private IngredientRepository ingredientRepository;
    private static List<IngredientItem> ingredientItems;

    public IngredientViewModel(Application application) {
        super(application);
        ingredientRepository = new IngredientRepository(application);
//        ingredientItems = ingredientRepository.getRecipeIngredients();
    }

    public List<IngredientItem> getIngredients() {
        return ingredientRepository.getRecipeIngredients();
    }

    public void insertIngredient(IngredientItem ingredientItem) {
        ingredientRepository.insertIngredient(ingredientItem);
    }

    public void deleteAll(){
        ingredientRepository.deleteAll();
    }


}
