package gregory.dan.mybakingapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Daniel Gregory on 02/08/2018.
 */
@Database(entities = IngredientItem.class, version = 2, exportSchema = false)
public abstract class IngredientDatabase extends RoomDatabase {

    public abstract IngredientDao ingredientDao();

    // make the database a singleton
    private static IngredientDatabase INSTANCE;

    public static IngredientDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (IngredientDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context,
                            IngredientDatabase.class,
                            "ingredients")
                            .build();
                }
            }
        }
        return INSTANCE;
    }



}
