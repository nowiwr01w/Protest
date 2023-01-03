package com.nowiwr01p.domain.auth.validators

import com.nowiwr01p.domain.auth.data.error.AuthError

/***
 * Base validator contract. Describes common generic contract to validate any objects.
 */
interface AuthDataValidator<in T> {

    /***
     * @return `null` when object is valid, `AuthError` otherwise
     */
    fun validate(value: T): AuthError?
}