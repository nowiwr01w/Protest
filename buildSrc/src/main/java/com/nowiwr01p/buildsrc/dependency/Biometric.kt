package com.nowiwr01p.buildsrc.dependency

import com.nowiwr01p.buildsrc.dependency.Biometric.Version.BIOMETRIC_VERSION
import com.nowiwr01p.buildsrc.dependency.Biometric.Version.CAPTCHA_ENTERPRISE_VERSION
import com.nowiwr01p.buildsrc.dependency.Biometric.Version.CAPTCHA_VERSION

object Biometric {

    const val CAPTCHA = "com.google.android.recaptcha:recaptcha:$CAPTCHA_VERSION"
    const val CAPTCHA_ENTERPRISE = "com.google.cloud:google-cloud-recaptchaenterprise:$CAPTCHA_ENTERPRISE_VERSION"
    const val BIOMETRIC = "androidx.biometric:biometric:$BIOMETRIC_VERSION"

    private object Version {
        const val CAPTCHA_VERSION = "18.0.1"
        const val CAPTCHA_ENTERPRISE_VERSION = "3.6.0"
        const val BIOMETRIC_VERSION = "1.1.0"
    }
}