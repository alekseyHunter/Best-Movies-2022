package company.bestmovies.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val settingsDataStore = context.dataStore

    val token: Flow<String>
        get() = settingsDataStore.data.map { preferences ->
            preferences[TOKEN].orEmpty()
        }

    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings_app")

        val TOKEN = stringPreferencesKey("api_token")
    }
}