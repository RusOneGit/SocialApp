package rus.one.app.util

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import rus.one.app.api.TokenProvider
import rus.one.app.api.TokenProviderImpl


@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {


    val Context.dataStore by preferencesDataStore(name = "settings")

    @Provides
    @Singleton
    fun provideTokenProvider(@ApplicationContext context: Context): TokenProvider =
        TokenProviderImpl(context)
}
