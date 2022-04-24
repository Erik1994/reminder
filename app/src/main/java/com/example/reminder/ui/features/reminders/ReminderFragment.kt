package com.example.reminder.ui.features.reminders

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.reminder.R
import com.example.reminder.databinding.FragmentReminderBinding
import com.example.reminder.ui.common.BaseFragment
import com.example.reminder.ui.extensions.clicks
import com.example.reminder.ui.extensions.collectLifeCycleFlow
import com.example.reminder.ui.extensions.emptyString
import com.example.reminder.ui.extensions.showSnackbar
import com.example.reminder.ui.features.reminders.adapter.ReminderAdapter
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel


class ReminderFragment : BaseFragment(R.layout.fragment_reminder) {
    override val viewModel: ReminderVIewModel by viewModel()
    private var binding: FragmentReminderBinding? = null
    private val reminderAdapter: ReminderAdapter by lazy {
        ReminderAdapter()
    }

    private val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val reminder = reminderAdapter.currentList[position]
            viewModel.deleteReminderById(reminder.id, reminder.workId)
            showSnackbar(
                getString(R.string.reminder_deleted_message),
                getString(R.string.undo)
            ) {
                viewModel.addReminder(reminder)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = FragmentReminderBinding.inflate(
                layoutInflater,
                container,
                false
            )
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
        getData()
        observeClicks()
        observeData()
    }

    private fun getData() {
        viewModel.getAllLocalReminders()
    }

    @OptIn(FlowPreview::class)
    private fun observeClicks() {
        binding?.fabAddReminder?.clicks()?.onEach {
            viewModel.navigate(
                ReminderFragmentDirections.actionReminderFragmentToAddEditReminderFragment(emptyString())
            )
        }?.launchIn(viewLifecycleOwner.lifecycleScope)

        reminderAdapter.clicksFlow()
            .onEach { id ->
                id?.let {
                    viewModel.navigate(
                        ReminderFragmentDirections.actionReminderFragmentToAddEditReminderFragment(it)
                    )
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun observeData() {
        collectLifeCycleFlow(viewModel.getRemindersSharedFlow) {
            reminderAdapter.submitList(it)
        }
    }

    private fun initRv() {
        binding?.rvReminder?.apply {
            adapter = reminderAdapter
            hasFixedSize()
            ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(this)
        }
    }
}