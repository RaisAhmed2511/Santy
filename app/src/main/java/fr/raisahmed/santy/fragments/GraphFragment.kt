package fr.raisahmed.santy.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import fr.raisahmed.santy.MainActivity
import fr.raisahmed.santy.ParamPopup
import fr.raisahmed.santy.ProfilRepository
import fr.raisahmed.santy.R

class GraphFragment ( private val context: MainActivity
) : Fragment() {

    lateinit var barList : ArrayList<BarEntry>
    lateinit var lineDataSet : BarDataSet
    lateinit var barData : BarData

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_graph, container, false)
        val param = view.findViewById<BarChart>(R.id.barChart)
        val repo = ProfilRepository()
        barList = ArrayList()
        barList.clear()
        repo.findPas {
            var i = 0
            ProfilRepository.Singleton.laliste.forEach(){
                barList.add(BarEntry(i.toFloat(), it.toFloat()))
                i++
            }

            System.out.println(barList)
            lineDataSet = BarDataSet(barList, "Evolution du nombres de pas")
            barData = BarData(lineDataSet)
            lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS, 250)
            param.data = barData
            lineDataSet.valueTextColor = Color.BLACK
            lineDataSet.valueTextSize = 15f


        }

        return view

    }

}