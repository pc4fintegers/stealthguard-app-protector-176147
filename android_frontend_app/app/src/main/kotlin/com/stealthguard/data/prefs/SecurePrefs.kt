package com.stealthguard.data.prefs

import android.content.SharedPreferences
import android.util.Base64
import java.security.MessageDigest
import java.security.SecureRandom

/**
 * PUBLIC_INTERFACE
 * Secure PIN storage using salted SHA-256 in EncryptedSharedPreferences.
 */
class SecurePrefs(private val sp: SharedPreferences) {

    private val kSalt = "pin_salt"
    private val kHash = "pin_hash"

    fun hasPin(): Boolean = sp.contains(kHash) && sp.contains(kSalt)

    fun setPin(pin: String) {
        val salt = ByteArray(32).also { SecureRandom().nextBytes(it) }
        val hash = hash(pin, salt)
        sp.edit()
            .putString(kSalt, Base64.encodeToString(salt, Base64.NO_WRAP))
            .putString(kHash, Base64.encodeToString(hash, Base64.NO_WRAP))
            .apply()
    }

    fun validate(pin: String): Boolean {
        val saltB64 = sp.getString(kSalt, null) ?: return false
        val hashB64 = sp.getString(kHash, null) ?: return false
        val salt = Base64.decode(saltB64, Base64.NO_WRAP)
        val expected = Base64.decode(hashB64, Base64.NO_WRAP)
        val actual = hash(pin, salt)
        return expected.contentEquals(actual)
    }

    private fun hash(pin: String, salt: ByteArray): ByteArray {
        val md = MessageDigest.getInstance("SHA-256")
        md.update(salt)
        md.update(pin.toByteArray(Charsets.UTF_8))
        return md.digest()
    }
}
