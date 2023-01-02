package com.nowiwr01p.domain.auth.validators

/***
 * Base validator contract. Describes common generic contract to validate any objects.
 */
interface Validator<in T> {

    /***
     * @return `true` when object is valid, `false` otherwise
     */
    fun validate(value: T): Boolean
}