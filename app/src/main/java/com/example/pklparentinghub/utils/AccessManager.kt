package com.example.pklparentinghub.utils

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*

class AccessManager(private val context: Context) {

    private val emptyString = ""
    private val emptyInt = 0

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

    fun setUserId(usedId: Int, scope: CoroutineScope) = scope.launch {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.userIdKey] = usedId
        }
    }

    // Deletion with Suspend Function based on Key
    suspend fun clearAccess() {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.accessKey] = emptyString
        }
    }

    suspend fun clearUserId() {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.userIdKey] = emptyInt
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

    val accessUserId = context.dataStore.data
        .catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[PreferencesKey.userIdKey] ?: emptyInt
        }



    suspend fun setFirstTimeAccess(status: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[firstTimeAccessKey] = status
        }
    }

    suspend fun setHomeFirstTimeAccess(status: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[homeFirstTimeAccessKey] = status
        }
    }

    val isFirstTimeAccess = context.dataStore.data
        .catch { throwable ->
            emit(emptyPreferences())
            Log.e(TAG, "$throwable" )
        }.map { preferences ->
            preferences[firstTimeAccessKey] ?: true
        }

    val isHomeFirstTimeAccess = context.dataStore.data
        .catch { throwable ->
            emit(emptyPreferences())
            Log.e(TAG, "$throwable" )
        }.map { preferences ->
            preferences[homeFirstTimeAccessKey] ?: true
        }

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = PreferencesKey.AUTH_PREFERENCES_KEY.toUpperCase(Locale.ROOT)
        )

        private val firstTimeAccessKey = booleanPreferencesKey("first_time_access")
        private val homeFirstTimeAccessKey = booleanPreferencesKey("home_first_time_access")
    }

    private object PreferencesKey {
        const val AUTH_PREFERENCES_KEY = "auth_preferences"
        const val TOKEN_ACCESS_REF = "token_access_key"
        const val USER_ID_REF = "user_id_key"

        val accessKey = stringPreferencesKey(TOKEN_ACCESS_REF)
        val userIdKey = intPreferencesKey(USER_ID_REF)
    }
}