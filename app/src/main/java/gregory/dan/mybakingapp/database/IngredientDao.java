package gregory.dan.mybakingapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Daniel Gregory on 02/08/2018.
 */
@Dao
public abstract class IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insert(IngredientItem ingredientItem);

    @Query("DELETE FROM ingredients")
    abstract void deleteAll();

    @Query("SELECT * from ingredients")
    abstract List<IngredientItem> getIngredients();

}

