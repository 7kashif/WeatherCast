package com.kashif.weathercast.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kashif.weathercast.Utils
import com.kashif.weathercast.databinding.ViewHolderDailyBinding
import com.kashif.weathercast.models.weatherOneCall.Daily

class DailyAdapter: ListAdapter<Daily, DailyAdapter.DailyViewHolder>(diffUtil) {

    inner class DailyViewHolder(val binding: ViewHolderDailyBinding): RecyclerView.ViewHolder(binding.root)

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Daily>() {
            override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
                return oldItem.dt==newItem.dt
            }

            override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
                return oldItem==newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        return DailyViewHolder(
            ViewHolderDailyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        val item = getItem(position)

        holder.binding.apply {
            holder.itemView.apply {
                val max = item.temp.max.toInt()
                val min = item.temp.min.toInt()
                tvDay.text = Utils.formatDay(item.dt)
                tvDescription.text = item.weather[0].description
                tvMinMax.text = "$max\u2103/$min\u2103"
                tvTemperature.text = "${item.temp.day.toInt()}\u2103"
                Utils.loadWeatherIcons(item.weather[0].icon,ivIcon)
            }
        }
    }
}