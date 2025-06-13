package rus.one.app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import rus.one.app.events.EventEntity
import rus.one.app.events.data.EventDao
import rus.one.app.posts.data.PostDao
import rus.one.app.posts.data.PostEntity
import rus.one.app.profile.UserDao
import rus.one.app.profile.UserEntity

@Database(entities = [PostEntity::class, EventEntity::class, UserEntity::class], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun eventDao(): EventDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDb::class.java,
                    "app.db"
                )
                    .build()
                    .also { instance = it }
            }
        }
    }

}
