package pet.perpet.equal.presentation.examination.fragment

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet
import pet.perpet.data.nonnull
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ExaminationResultFragmentBinding
import pet.perpet.equal.presentation.examination.viewmodel.ExaminationResultViewModel
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.presentation.observeBindingNotifyView
import pet.perpet.framework.fragment.Fragment
import java.util.Collections


class ExaminationResultFragment : Fragment<ExaminationResultViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: ExaminationResultFragmentBinding

    private val score: ArrayList<String> = ArrayList()

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.observeBindingNotify {
            binding.model = viewmodel
        }

        viewmodel.observeBindingNotifyView {
            when(it.id) {
                R.id.ic_date_left_arrow -> {

                }
                R.id.ic_date_right_arrow -> {
//                    if(viewmodel.arrowTypeCount.value == 0) {
//                        viewmodel.arrowTypeCount.value = viewmodel.arrowTypeCount.value.nonnull() + 1
//
//                        if(viewmodel.result.size == viewmodel.arrowTypeCount.value.nonnull()) {
//                            viewmodel.arrowType.value = 2
//                        }
//                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.examination_result_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = ExaminationResultFragmentBinding.bind(view)
        binding.model = viewmodel

        context?.let {

            binding.chart.description.isEnabled = false
            binding.chart.webLineWidth = 0.5f
            binding.chart.webColor = ContextCompat.getColor(it , R.color.grape_line)
            binding.chart.webLineWidthInner = 0.5f
            binding.chart.webColorInner = ContextCompat.getColor(it , R.color.grape_line)
            binding.chart.webAlpha = 100
            binding.chart.isRotationEnabled = false

            settingMedical(viewmodel.args.id.nonnull().toInt())

            binding.chart.animateXY(1400, 1400, Easing.EaseInOutQuad)

            val xAxis: XAxis = binding.chart.xAxis
            xAxis.typeface = resources.getFont(R.font.gmarket_sans_medium)
            xAxis.textSize = 11f
            xAxis.yOffset = 0f
            xAxis.textColor = ContextCompat.getColor(it , R.color.grape_line)
            xAxis.xOffset = 0f
            xAxis.valueFormatter =
                IAxisValueFormatter { value, axis ->
                    score[value.toInt() % score.size].nonnull()
                }

            val yAxis: YAxis = binding.chart.yAxis
            yAxis.typeface = resources.getFont(R.font.gmarket_sans_medium)
            yAxis.setLabelCount(4, false)
            yAxis.textSize = 11f
            yAxis.axisMinimum = 0f
            yAxis.axisMaximum = 1.2f
            xAxis.textColor = ContextCompat.getColor(it , R.color.grape_line)
            yAxis.setDrawLabels(false)

            val l: Legend = binding.chart.legend
            l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            l.orientation = Legend.LegendOrientation.HORIZONTAL
            l.mTextHeightMax = 12f
            l.setDrawInside(true)
            l.typeface = resources.getFont(R.font.gmarket_sans_medium)
            l.xEntrySpace = 7f
            l.yEntrySpace = 5f
            l.textColor =  ContextCompat.getColor(it , R.color.grape_line)
            viewmodel.getDataList()

        }


        super.onViewCreated(view, savedInstanceState)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun settingMedical(medicalId : Int) {
        viewmodel.getData(medicalId) {

            it.results?.let {
                val max = 80f
                val min = 10f

                val entriesScore = ArrayList<RadarEntry>()
                val entriesAvg = ArrayList<RadarEntry>()

                val avgArray : ArrayList<Float> = ArrayList()
                val petArray : ArrayList<Float> = ArrayList()

                it.forEach {
                    avgArray.add(it.avg.nonnull())
                    petArray.add(it.score.nonnull())
                    score.add(it.diagnosis?.name_kor.nonnull())
                }
                val avgMax = Collections.max(avgArray)
                val scoreMax = Collections.max(petArray)

                avgArray.forEach {
                    entriesAvg.add(RadarEntry(((it.nonnull() * it.nonnull()) / avgMax)))
                }
                petArray.forEach {
                    entriesScore.add(RadarEntry(((it.nonnull() * it.nonnull()) / scoreMax)))
                }


                val set1 = RadarDataSet(entriesAvg, "건강한 아이 평균")
                context?.let {
                    set1.color = ContextCompat.getColor(it , R.color.grape_line)
                    set1.fillDrawable = ContextCompat.getDrawable(it , R.drawable.examination_gradient_gray)
                    set1.setDrawFilled(true)
                    set1.fillAlpha = 102
                    set1.lineWidth = 0.8f
                    set1.isDrawHighlightCircleEnabled = true
                    set1.setDrawHighlightIndicators(false)

                    val set2 = RadarDataSet(entriesScore, viewmodel.args.name)
                    set2.color = ContextCompat.getColor(it , R.color.grape_line_2)
                    set2.fillDrawable = ContextCompat.getDrawable(it , R.drawable.examination_gradient)
                    set2.setDrawFilled(true)
                    set2.lineWidth = 0.8f
                    set2.isDrawHighlightCircleEnabled = true
                    set2.setDrawHighlightIndicators(false)

                    val sets = ArrayList<IRadarDataSet>()
                    sets.add(set1)
                    sets.add(set2)

                    val data = RadarData(sets)
                    data.setValueTypeface(resources.getFont(R.font.gmarket_sans_medium))
                    data.setValueTextSize(13f)
                    data.setDrawValues(false)
                    data.setValueTextColor(R.color.bottom_title)

                    binding.chart.data = data
                    binding.chart.invalidate()
                }



            }
        }

    }
}