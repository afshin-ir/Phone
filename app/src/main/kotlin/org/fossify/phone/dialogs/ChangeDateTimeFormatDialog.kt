package org.fossify.phone.dialogs

import android.app.Activity
import android.icu.text.SimpleDateFormat
import android.icu.util.ULocale
import org.fossify.commons.databinding.DialogChangeDateTimeFormatBinding
import org.fossify.commons.extensions.baseConfig
import org.fossify.commons.extensions.getAlertDialogBuilder
import org.fossify.commons.extensions.setupDialogStuff
import org.fossify.commons.helpers.DATE_FORMAT_EIGHT
import org.fossify.commons.helpers.DATE_FORMAT_FIVE
import org.fossify.commons.helpers.DATE_FORMAT_FOUR
import org.fossify.commons.helpers.DATE_FORMAT_ONE
import org.fossify.commons.helpers.DATE_FORMAT_SEVEN
import org.fossify.commons.helpers.DATE_FORMAT_SIX
import org.fossify.commons.helpers.DATE_FORMAT_THREE
import org.fossify.commons.helpers.DATE_FORMAT_TWO
import org.fossify.phone.extensions.config
import java.util.Date
import java.util.Locale

class ChangeDateTimeFormatDialog(
    private val activity: Activity,
    private val callback: () -> Unit,
) {
    private val view = DialogChangeDateTimeFormatBinding.inflate(activity.layoutInflater, null, false)

    init {
        view.apply {
            changeDateTimeDialogRadioOne.text = formatDateSample(DATE_FORMAT_ONE)
            changeDateTimeDialogRadioTwo.text = formatDateSample(DATE_FORMAT_TWO)
            changeDateTimeDialogRadioThree.text = formatDateSample(DATE_FORMAT_THREE)
            changeDateTimeDialogRadioFour.text = formatDateSample(DATE_FORMAT_FOUR)
            changeDateTimeDialogRadioFive.text = formatDateSample(DATE_FORMAT_FIVE)
            changeDateTimeDialogRadioSix.text = formatDateSample(DATE_FORMAT_SIX)
            changeDateTimeDialogRadioSeven.text = formatDateSample(DATE_FORMAT_SEVEN)
            changeDateTimeDialogRadioEight.text = formatDateSample(DATE_FORMAT_EIGHT)
            changeDateTimeDialog24Hour.isChecked = activity.baseConfig.use24HourFormat

            val selectedButton = when (activity.baseConfig.dateFormat) {
                DATE_FORMAT_ONE -> changeDateTimeDialogRadioOne
                DATE_FORMAT_TWO -> changeDateTimeDialogRadioTwo
                DATE_FORMAT_THREE -> changeDateTimeDialogRadioThree
                DATE_FORMAT_FOUR -> changeDateTimeDialogRadioFour
                DATE_FORMAT_FIVE -> changeDateTimeDialogRadioFive
                DATE_FORMAT_SIX -> changeDateTimeDialogRadioSix
                DATE_FORMAT_SEVEN -> changeDateTimeDialogRadioSeven
                else -> changeDateTimeDialogRadioEight
            }
            selectedButton.isChecked = true
        }

        activity.getAlertDialogBuilder()
            .setPositiveButton(org.fossify.commons.R.string.ok) { _, _ -> saveChanges() }
            .setNegativeButton(org.fossify.commons.R.string.cancel, null)
            .apply { activity.setupDialogStuff(view.root, this) }
    }

    private fun saveChanges() {
        activity.baseConfig.dateFormat = when (view.changeDateTimeDialogRadioGroup.checkedRadioButtonId) {
            view.changeDateTimeDialogRadioOne.id -> DATE_FORMAT_ONE
            view.changeDateTimeDialogRadioTwo.id -> DATE_FORMAT_TWO
            view.changeDateTimeDialogRadioThree.id -> DATE_FORMAT_THREE
            view.changeDateTimeDialogRadioFour.id -> DATE_FORMAT_FOUR
            view.changeDateTimeDialogRadioFive.id -> DATE_FORMAT_FIVE
            view.changeDateTimeDialogRadioSix.id -> DATE_FORMAT_SIX
            view.changeDateTimeDialogRadioSeven.id -> DATE_FORMAT_SEVEN
            else -> DATE_FORMAT_EIGHT
        }
        activity.baseConfig.use24HourFormat = view.changeDateTimeDialog24Hour.isChecked
        callback()
    }

    private fun formatDateSample(format: String): String {
        val locale = if (activity.config.useJalaliCalendar) {
            ULocale.forLocale(Locale.forLanguageTag("fa-IR")).setKeywordValue("calendar", "persian")
        } else {
            ULocale.forLocale(Locale.ENGLISH)
        }
        return SimpleDateFormat(format, locale).format(Date(TIME_SAMPLE))
    }

    private companion object {
        const val TIME_SAMPLE = 1676419200000L
    }
}
