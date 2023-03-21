package com.example.pklparentinghub.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.Flow

class AccessManager(private val context: Context) {

    private val emptyString = ""

    // Insertion with Suspend Function based on Key
    suspend fun setAccess(tokenAccess: String) {
        // Insert into Preference Data Store
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.accessKey] = "${Const.Token.AUTH_PREFIX} $tokenAccess"
        }
    }

    // Insertion without Suspend Function based on Key
    fun setAccess(tokenAccess: String, scope: CoroutineScope) = scope.launch {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.accessKey] = "${Const.Token.AUTH_PREFIX} $tokenAccess"
        }
    }

    // Deletion with Suspend Function based on Key
    suspend fun clearAccess() {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.accessKey] = emptyString
        }
    }

    fun clearAccess(scope: CoroutineScope) = scope.launch {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.accessKey] = emptyString
        }
    }

    // Properties Variable to access Authorization Bearer (Token)
    val access = context.dataStore.data
        .catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[PreferencesKey.accessKey] ?: emptyString
        }

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = PreferencesKey.AUTH_PREFERENCES_KEY.toUpperCase(Locale.ROOT)
        )
    }

    private object PreferencesKey {
        const val AUTH_PREFERENCES_KEY = "auth_preferences"
        const val TOKEN_ACCESS_REF = "token_access_key"

        private val accessTokenKey = stringPreferencesKey("access_token")
        private val firstTimeAccessKey = booleanPreferencesKey("first_time_access")
        private val homeFirstTimeAccessKey = booleanPreferencesKey("home_first_time_access")

        val accessKey = stringPreferencesKey(TOKEN_ACCESS_REF)
    }
}