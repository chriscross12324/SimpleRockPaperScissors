package com.simplegames.chris.rockpaperscissors.component

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.simplegames.chris.rockpaperscissors.R
import com.simplegames.chris.rockpaperscissors.utils.Conversions
import com.simplegames.chris.rockpaperscissors.utils.SharedPreferenceKeys
import com.simplegames.chris.rockpaperscissors.utils.UIUtilities
import com.simplegames.chris.rockpaperscissors.utils.ValuesNew
import com.simplegames.chris.rockpaperscissors.utils.VibrationType
import com.simplegames.chris.rockpaperscissors.utils.vibrate

class BackgroundOptionAdapter(
    private val context: Context,
    private val options: List<BackgroundOption>,
    private var selectedIndex: Int,
    private val onSelectionChanges: (Int) -> Unit
) : RecyclerView.Adapter<BackgroundOptionAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val layout: MaterialCardView = view.findViewById(R.id.buttonColour)
        val background: ImageView = view.findViewById(R.id.buttonColourBackground)
        val text: TextView = view.findViewById(R.id.buttonColourText)

        init {
            view.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION && position != selectedIndex) {
                    val previousIndex = selectedIndex
                    selectedIndex = position
                    notifyItemChanged(previousIndex)
                    notifyItemChanged(selectedIndex)

                    ValuesNew.backgroundGradient = position
                    vibrate(context, VibrationType.WEAK)
                    ValuesNew.saveValue(
                        context,
                        SharedPreferenceKeys.KEY_SETTING_BACKGROUND_GRADIENT,
                        position
                    )
                    onSelectionChanges(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_background_option, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val option = options[position]
        holder.text.text = option.text
        UIUtilities.setBackground(holder.background, option.colours, 0f)
        holder.layout.strokeWidth = (if (position == selectedIndex)
            Conversions.dpToPx(2.5f)
        else
            0f).toInt()
    }

    override fun getItemCount(): Int {
        return options.size
    }
}