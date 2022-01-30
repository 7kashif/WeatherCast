package com.kashif.weathercast.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kashif.weathercast.Utils
import com.kashif.weathercast.databinding.ViewHolderHourlyBinding
import com.kashif.weathercast.models.weatherOneCall.Hourly

class HourlyAdapter: ListAdapter<Hourly, HourlyAdapter.HourlyViewHolder>(diffCallBack) {

    inner class HourlyViewHolder(val binding: ViewHolderHourlyBinding): RecyclerView.ViewHolder(binding.root)

    companion object {
        private val diffCallBack= object : DiffUtil.ItemCallback<Hourly>() {
            override fun areItemsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
                return oldItem.dt==newItem.dt
            }

            override fun areContentsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
                return oldItem==newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        return HourlyViewHolder(
            ViewHolderHourlyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        val item = getItem(position)

        holder.binding.apply {
            holder.itemView.apply {
                tvTime.text = Utils.formatTime(item.dt)
                tvDescription.text = item.weather[0].description
                tvTemperature.text = "${item.temp}\u2103"
                Utils.loadWeatherIcons(item.weather[0].icon,ivIcon)
            }
        }
    }
}