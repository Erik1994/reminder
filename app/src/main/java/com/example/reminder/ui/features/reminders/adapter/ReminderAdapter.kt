package com.example.reminder.ui.features.reminders.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.reminder.R
import com.example.reminder.data.model.data.ReminderViewData
import com.example.reminder.databinding.ReminderItemBinding
import com.example.reminder.ui.extensions.dateTimeFormat
import com.example.reminder.ui.extensions.debounceClicks
import com.example.reminder.ui.extensions.formatDate
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ReminderAdapter : ListAdapter<ReminderViewData, ReminderAdapter.ViewHolder>(DiffCallBack()) {
    private var coroutineScope: CoroutineScope? = null
    private val clicksSharedFlow = MutableSharedFlow<Int?>()
    fun clicksFlow() = clicksSharedFlow.asSharedFlow()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ReminderItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = currentList[position]
        holder.bind(currentItem)
    }

    @OptIn(FlowPreview::class)
    inner class ViewHolder(private val itemBinding: ReminderItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private var currentItem: ReminderViewData? = null

        init {
            coroutineScope?.let {
                itemView.debounceClicks()
                    .onEach {
                        clicksSharedFlow.emit(currentItem?.id)
                    }
                    .launchIn(it)
            }
        }

        fun bind(item: ReminderViewData) {
            currentItem = item
            with(itemBinding) {
                if (item.isComplited) {
                    syncImage.setImageResource(R.drawable.ic_check)
                } else {
                    syncImage.setImageResource(R.drawable.ic_time)
                }
                dateTime.text = item.dateTime.formatDate(dateTimeFormat())
                descriptionTv.text = item.description
                titleTv.text = item.title
            }
        }

    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        coroutineScope = CoroutineScope(Dispatchers.Main)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        coroutineScope?.apply {
            if (isActive) {
                cancel()
            }
        }
        coroutineScope = null
        super.onDetachedFromRecyclerView(recyclerView)
    }

    class DiffCallBack : DiffUtil.ItemCallback<ReminderViewData>() {
        override fun areItemsTheSame(
            oldItem: ReminderViewData,
            newItem: ReminderViewData
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ReminderViewData,
            newItem: ReminderViewData
        ): Boolean =
            oldItem.id == newItem.id && oldItem.description == newItem.description && oldItem.isComplited == newItem.isComplited
    }
}