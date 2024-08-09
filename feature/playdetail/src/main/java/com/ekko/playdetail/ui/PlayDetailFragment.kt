package com.ekko.playdetail.ui

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ekko.play.detail.databinding.FragmentPlayDetailBinding

class PlayDetailFragment : Fragment() {

    private lateinit var binding: FragmentPlayDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.getInsetsController(
            requireActivity().window,
            requireActivity().window.decorView
        ).apply {
            isAppearanceLightStatusBars = false
        }
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.list.adapter = StringAdapter(listOf("1", "2", "3", "4", "5", "6", "7", "8"))
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.e("huqiang", "onConfigurationChanged: $newConfig")
    }
}


class StringAdapter(private val data: List<String>) : RecyclerView.Adapter<StringViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder =
        StringViewHolder.create(parent)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        holder.bind(data[position])
    }

}


class StringViewHolder(val view: TextView) : RecyclerView.ViewHolder(view) {


    fun bind(data: String) {
        view.text = data
    }


    companion object {
        fun create(parent: ViewGroup): StringViewHolder {
            return StringViewHolder(TextView(parent.context))
        }
    }
}