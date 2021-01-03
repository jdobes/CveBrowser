package cz.utb.jdobes.cvebrowser.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CveDao {
    @Query("select * from cvedbitem")
    fun getCves(): LiveData<List<CveDbItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( videos: List<CveDbItem>)
}

@Database(entities = [CveDbItem::class], version = 1, exportSchema = false)
abstract class CveDatabase: RoomDatabase() {
    abstract val cveDao: CveDao
}

private lateinit var INSTANCE: CveDatabase

fun getDatabase(context: Context): CveDatabase {
    synchronized(CveDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                CveDatabase::class.java,
                "cve").build()
        }
    }
    return INSTANCE
}
