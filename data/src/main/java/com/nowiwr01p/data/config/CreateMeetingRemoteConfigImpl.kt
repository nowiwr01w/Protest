package com.nowiwr01p.data.config

import com.google.firebase.remoteconfig.ktx.get
import com.nowiwr01p.domain.config.CreateMeetingRemoteConfig
import com.nowiwr01p.domain.config.RemoteConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreateMeetingRemoteConfigImpl: RemoteConfig(), CreateMeetingRemoteConfig {

    override fun initValues() {
        initTextLength()
        initTitleLength()
        initSloganLength()
        initPathDotsCount()
        initHoursDifference()
        initLocationPlaceLength()
        initLocationDetailsLength()
    }

    /**
     * TEXT LENGTH
     */
    private val _textLengthStateFlow = MutableStateFlow(72)
    override fun getTextLength() = _textLengthStateFlow.asStateFlow()

    private fun initTextLength() = _textLengthStateFlow.update {
        config[TEXT_LENGTH].asLong().toInt()
    }

    /**
     * TITLE LENGTH
     */
    private val _titleLengthStateFlow = MutableStateFlow(72)
    override fun getTitleLength() = _titleLengthStateFlow.asStateFlow()

    private fun initTitleLength() = _titleLengthStateFlow.update {
        config[TITLE_LENGTH].asLong().toInt()
    }

    /**
     * SLOGANS LENGTH
     */
    private val _sloganLengthStateFlow = MutableStateFlow(72)
    override fun getSloganLength() = _sloganLengthStateFlow.asStateFlow()

    private fun initSloganLength() = _sloganLengthStateFlow.update {
        config[SLOGAN_LENGTH].asLong().toInt()
    }

    /**
     * PATH DOTS COUNT
     */
    private val _pathDotsCountStateFlow = MutableStateFlow(72)
    override fun getPathDotsCount() = _pathDotsCountStateFlow.asStateFlow()

    private fun initPathDotsCount() = _pathDotsCountStateFlow.update {
        config[PATH_DOTS_COUNT].asLong().toInt()
    }

    /**
     * HOURS DIFFERENCE
     */
    private val _hoursDifferenceStateFlow = MutableStateFlow(72)
    override fun getHoursDifference() = _hoursDifferenceStateFlow.asStateFlow()

    private fun initHoursDifference() = _hoursDifferenceStateFlow.update {
        config[HOURS_DIFFERENCE].asLong().toInt()
    }

    /**
     * LOCATION PLACE LENGTH
     */
    private val _locationPlaceLengthStateFlow = MutableStateFlow(72)
    override fun getLocationPlaceLength() = _locationPlaceLengthStateFlow.asStateFlow()

    private fun initLocationPlaceLength() = _locationPlaceLengthStateFlow.update {
        config[LOCATION_PLACE_LENGTH].asLong().toInt()
    }

    /**
     * LOCATION DETAILS LENGTH
     */
    private val _locationDetailsLengthStateFlow = MutableStateFlow(72)
    override fun getLocationDetailsLength() = _locationDetailsLengthStateFlow.asStateFlow()

    private fun initLocationDetailsLength() = _locationDetailsLengthStateFlow.update {
        config[LOCATION_DETAILS_LENGTH].asLong().toInt()
    }

    private companion object {
        const val TEXT_LENGTH = "meeting_text_length"
        const val TITLE_LENGTH = "meeting_title_length"
        const val SLOGAN_LENGTH = "meeting_slogan_length"
        const val PATH_DOTS_COUNT = "meeting_path_items_count"
        const val HOURS_DIFFERENCE = "meeting_hours_difference"
        const val LOCATION_PLACE_LENGTH = "meeting_location_place_length"
        const val LOCATION_DETAILS_LENGTH = "meeting_location_details_length"
    }
}