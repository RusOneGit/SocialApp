package rus.one.app.di


import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import rus.one.app.db.AppDb
import rus.one.app.dao.PostDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDb(@ApplicationContext context: Context): AppDb {
        val db = AppDb.getInstance(context)
        // Принудительно открыть базу, чтобы создать файл
        db.openHelper.writableDatabase
        return db
    }

    @Provides
    fun providePostDao(appDb: AppDb): PostDao {
        return appDb.postDao()
    }


}
