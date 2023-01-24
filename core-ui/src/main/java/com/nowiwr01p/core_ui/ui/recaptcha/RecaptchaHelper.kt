package com.nowiwr01p.core_ui.ui.recaptcha

import android.app.Application
import com.google.android.recaptcha.Recaptcha
import com.google.android.recaptcha.RecaptchaAction
import com.google.android.recaptcha.RecaptchaClient
import com.google.cloud.recaptchaenterprise.v1.RecaptchaEnterpriseServiceClient
import com.google.recaptchaenterprise.v1.Assessment
import com.google.recaptchaenterprise.v1.CreateAssessmentRequest
import com.google.recaptchaenterprise.v1.Event
import com.google.recaptchaenterprise.v1.ProjectName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

class RecaptchaHelper(
    private val apiKey: String,
    private val application: Application
) {

    private lateinit var coroutineScope: CoroutineScope
    private lateinit var recaptchaClient: RecaptchaClient
    private lateinit var recaptchaEnterpriseClient: RecaptchaEnterpriseServiceClient

    fun init(scope: CoroutineScope) {
        coroutineScope = scope
        initRecaptcha()
    }

    private fun initRecaptcha() = coroutineScope.launch {
        Recaptcha.getClient(application, apiKey).onSuccess { client ->
            recaptchaClient = client
        }
        // TODO: Хуйня нерабочая. Не API, а говно
        recaptchaEnterpriseClient = RecaptchaEnterpriseServiceClient.create()
    }

    fun showCaptcha(viewModelScope: CoroutineScope, callback: () -> Unit) = viewModelScope.launch {
        recaptchaClient
            .execute(RecaptchaAction.LOGIN)
            .onSuccess { token ->
                checkRiskScore(token, callback)
            }
    }

    private fun checkRiskScore(token: String, callback: () -> Unit) {
        val event = Event.newBuilder().setSiteKey(apiKey).setToken(token).build()

        val createAssessmentRequest = CreateAssessmentRequest.newBuilder()
            .setParent(ProjectName.of("name").toString())
            .setAssessment(Assessment.newBuilder().setEvent(event).build())
            .build()

        val response = recaptchaEnterpriseClient.createAssessment(createAssessmentRequest)

        if (!response.tokenProperties.valid) {
            Timber.d("The CreateAssessment call failed because the token was: ${response.tokenProperties.invalidReason.name}")
        }
        if (response.tokenProperties.action != RecaptchaAction.LOGIN.action) {
            Timber.d("The action attribute in reCAPTCHA tag is: ${response.tokenProperties.action}")
        }

        val score = response.riskAnalysis.score

        Timber.d("score = $score")
    }
}