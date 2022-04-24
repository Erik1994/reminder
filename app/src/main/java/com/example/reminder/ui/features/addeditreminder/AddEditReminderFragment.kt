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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class AddEditReminderFragment : BaseFragment(R.layout.fragment_add_edit_reminder) {
    override val viewModel: AddEditReminderViewModel by viewModel()
    private var binding: FragmentAddEditReminderBinding? = null
    private val args: AddEditReminderFragmentArgs by navArgs()
    private var reminderId: Int? = null
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reminderId = if (args.id.isNotEmpty()) args.id.toInt() else null
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
                    addOrUpdateReminder()
                    viewModel.navigateBack()
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            dateTime.debounceClicks()
                .onEach {
                    openDatePicker()
                }.launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    private fun addOrUpdateReminder() {
        binding?.apply {
            val title = titleTv.text.toString()
            val description = descriptionTv.text.toString()
            val dateTime = dateTime.text.toString().stringDateToMilis(dateTimeFormat())
            reminderId?.let {  remId ->
                val reminder = ReminderViewData(
                    id = remId,
                    title = title,
                    description = description,
                    dateTime = dateTime
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
        datePickerDialog.show()
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
                }
            } ?: showSnackbar(getString(R.string.reminder_finding_issue_massage))
        }
    }
}