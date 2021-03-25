package com.smqpro.whyareyourunningtracker.ui.tracking

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.smqpro.whyareyourunningtracker.R

class CancelDialogFragment : DialogFragment() {

    private var positiveButtonClickListener: (() -> Unit)? = null
    fun setPositiveButtonClickListener(listener: () -> Unit) {
        positiveButtonClickListener = listener
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
            .setTitle("Cancel the Run?")
            .setMessage("Are you sure to cancel the current run and delete all its data")
            .setIcon(R.drawable.ic_delete)
            .setPositiveButton("Yes") { _, _ ->
                positiveButtonClickListener?.let { yes -> yes() }
            }
            .setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .create()
    }
}