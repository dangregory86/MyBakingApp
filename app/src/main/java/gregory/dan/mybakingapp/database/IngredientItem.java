package gregory.dan.mybakingapp.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Daniel Gregory on 02/08/2018.
 */
@Entity(tableName = "ingredients")
public class IngredientItem {

    @PrimaryKey(autoGenerate = true)
    public int _id;

    @ColumnInfo(name = "recipe_name")
    public String recipeName;

    @ColumnInfo(name = "quantity")
    public double quantity;

    @ColumnInfo(name = "measure")
    public String measure;

    @ColumnInfo(name = "ingredient")
    public String ingredient;

    public IngredientItem(String recipeName, double quantity, String measure, String ingredient) {
        this.recipeName = recipeName;
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }
}
