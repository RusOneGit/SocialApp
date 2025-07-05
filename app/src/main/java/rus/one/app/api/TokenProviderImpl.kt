package rus.one.app.api

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.core.DataStore
import rus.one.app.util.DataStoreModule.dataStore

private val KEY_AUTH_TOKEN = stringPreferencesKey("token")

class TokenProviderImpl(context: Context) : TokenProvider {
    private val dataStore: DataStore<Preferences> = context.dataStore

    override val authToken: StateFlow<String?> = dataStore.data
        .map { prefs -> prefs[KEY_AUTH_TOKEN] }
        .stateIn(
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            started = SharingStarted.Eagerly,
            initialValue = null
        )
}
