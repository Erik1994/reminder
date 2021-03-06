package com.example.reminder.ui.features.addeditreminder

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.reminder.R
import com.example.reminder.data.model.data.ReminderViewData
import com.example.reminder.databinding.FragmentAddEditReminderBinding
import com.example.reminder.ui.common.BaseFragment
import com.example.reminder.ui.extensions.*
import com.example.reminder.ui.features.reminders.ReminderVIewModel
import com.example.reminder.ui.util.checkValidation
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class AddEditReminderFragment : BaseFragment(R.layout.fragment_add_edit_reminder) {
    override val viewModel: AddEditReminderViewModel by viewModel()
    private var binding: FragmentAddEditReminderBinding? = null
    private val args: AddEditReminderFragmentArgs by navArgs()
    private var reminderId: String? = null
    private val calendar = Calendar.getInstance()
    private var workId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reminderId = args.id.ifEmpty { null }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = FragmentAddEditReminderBinding.inflate(
                layoutInflater,
                container,
                false
            )
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        observeData()
        observeClicks()
    }

    private fun getData() {
        reminderId?.let {
            viewModel.getReminder(it)
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeClicks() {
        binding?.apply {
            saveButton.clicks()
                .onEach {
                    addOrUpdateReminder { isValid ->
                        if (isValid) {
                            viewModel.navigateBack()
                        }
                    }
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            dateTime.debounceClicks()
                .onEach {
                    openDatePicker()
                }.launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    private fun addOrUpdateReminder(isReadyToBack: (Boolean) -> Unit) {
        binding?.apply {
            val title = titleTv.text.toString()
            val description = descriptionTv.text.toString()
            dateTime.checkValidation { isVald ->
                if (!isVald) {
                    isReadyToBack(isVald)
                    return@checkValidation
                }
                val dateTime = dateTime.text.toString().stringDateToMilis(dateTimeFormat())
                reminderId?.let {  remId ->
                    val reminder = ReminderViewData(
                        id = remId,
                        title = title,
                        description = description,
                        dateTime = dateTime,
                        workId = workId ?: emptyString(),
                        isComplited = dateTime < System.currentTimeMillis()
                    )
                    viewModel.updateReminder(reminder)
                } ?: run {
                    val reminder = ReminderViewData(
                        title = title,
                        description = description,
                        dateTime = calendar.timeInMillis
                    )
                    viewModel.addReminder(reminder)
                }
                isReadyToBack(isVald)
            }
        }
    }

    private fun openDatePicker() {
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                calendar.set(year, month, dayOfMonth)
                openTimePicker()
            }
        val datePickerDialog = DatePickerDialog(
            requireContext(), dateSetListener,
            calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog.apply {
            datePicker.minDate = System.currentTimeMillis() - 1000
            show()
        }
    }

    private fun openTimePicker() {
        val timeSetListener =
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                setDate()
            }
        val timePickerDialog = TimePickerDialog(
            requireContext(), timeSetListener,
            calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE], true
        )
        timePickerDialog.show()
    }

    private fun setDate() {
        val myFormatToShow = dateTimeFormat()
        val sdf = SimpleDateFormat(myFormatToShow, Locale.US)
        binding?.dateTime?.text = sdf.format(calendar.time)
    }

    private fun observeData() {
        collectLifeCycleFlow(viewModel.reminderSharedFlow) { reminder ->
            reminder?.let { reminder ->
                binding?.apply {
                    titleTv.setText(reminder.title)
                    descriptionTv.setText(reminder.description)
                    dateTime.text = reminder.dateTime.formatDate(dateTimeFormat())
                    workId = reminder.workId
                }
            } ?: showSnackbar(getString(R.string.reminder_finding_issue_massage))
        }
    }
}