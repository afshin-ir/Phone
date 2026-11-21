package org.fossify.phone.dialogs

import android.icu.text.SimpleDateFormat
import android.icu.util.ULocale
import androidx.appcompat.app.AlertDialog
import org.fossify.commons.extensions.*
import org.fossify.phone.R
import org.fossify.phone.activities.SimpleActivity
import org.fossify.phone.databinding.DialogExportCallHistoryBinding
import java.util.Date
import java.util.Locale

class ExportCallHistoryDialog(val activity: SimpleActivity, callback: (filename: String) -> Unit) {

    init {
        val filenameDateLocale = ULocale.forLocale(Locale.forLanguageTag("fa-IR"))
            .setKeywordValue("calendar", "persian")
            .setKeywordValue("numbers", "latn")
        val todayJalali = SimpleDateFormat("yyyy-MM-dd", filenameDateLocale).format(Date())

        val binding = DialogExportCallHistoryBinding.inflate(activity.layoutInflater).apply {
            exportCallHistoryFilename.setText("Call-History $todayJalali")
        }

        activity.getAlertDialogBuilder().setPositiveButton(R.string.ok, null).setNegativeButton(R.string.cancel, null).apply {
            activity.setupDialogStuff(binding.root, this, R.string.export_call_history) { alertDialog ->
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {

                    val filename = binding.exportCallHistoryFilename.value
                    when {
                        filename.isEmpty() -> activity.toast(R.string.empty_name)
                        filename.isAValidFilename() -> {
                            callback(filename)
                            alertDialog.dismiss()
                        }

                        else -> activity.toast(R.string.invalid_name)
                    }
                }
            }
        }
    }
}
