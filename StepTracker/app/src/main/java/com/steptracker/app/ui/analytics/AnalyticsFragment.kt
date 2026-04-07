package com.steptracker.app.ui.analytics

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.steptracker.app.MainViewModel
import com.steptracker.app.databinding.FragmentAnalyticsBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AnalyticsFragment : Fragment() {

    private var _binding: FragmentAnalyticsBinding? = null
    private val binding get() = _binding!!
    private val vm: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnalyticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vm.loadStats()

        vm.weekData.observe(viewLifecycleOwner) { records ->
            if (records.isEmpty()) return@observe
            val profile = vm.profile.value ?: return@observe
            setupBarChart(records, profile.dailyGoalSteps)
            setupPieChart(records, profile.dailyGoalSteps)
        }

        vm.avgSteps.observe(viewLifecycleOwner) {
            binding.tvAvgSteps.text = "%,d".format(it.toInt()).replace(',', ' ')
        }
        vm.avgCalories.observe(viewLifecycleOwner) {
            binding.tvAvgKcal.text = it.toInt().toString()
        }
        vm.totalKm.observe(viewLifecycleOwner) {
            binding.tvTotalKm.text = "%.1f".format(it)
        }
    }

    private fun setupBarChart(records: List<com.steptracker.app.data.DayRecord>, goal: Int) {
        val fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dayFmt = DateTimeFormatter.ofPattern("dd.MM")
        val last7 = (6 downTo 0).map { LocalDate.now().minusDays(it.toLong()) }
        val labels = last7.map { it.format(dayFmt) }

        val entries = last7.mapIndexed { i, date ->
            val dateStr = date.format(fmt)
            val rec = records.firstOrNull { it.date == dateStr }
            BarEntry(i.toFloat(), (rec?.steps ?: 0).toFloat())
        }

        val color = 0xFF1D9E75.toInt()
        val dataSet = BarDataSet(entries, "Кроки").apply {
            setColor(color)
            valueTextSize = 10f
            setDrawValues(false)
        }

        binding.barChart.apply {
            data = BarData(dataSet).apply { barWidth = 0.6f }
            description.isEnabled = false
            legend.isEnabled = false
            setFitBars(true)

            xAxis.apply {
                valueFormatter = IndexAxisValueFormatter(labels)
                position = XAxis.XAxisPosition.BOTTOM
                granularity = 1f
                setDrawGridLines(false)
                textSize = 11f
            }
            axisLeft.apply {
                axisMinimum = 0f
                addLimitLine(LimitLine(goal.toFloat(), "Ціль").apply {
                    lineColor = Color.RED
                    lineWidth = 1.5f
                    textColor = Color.RED
                    textSize = 10f
                    enableDashedLine(10f, 5f, 0f)
                })
            }
            axisRight.isEnabled = false
            animateY(600)
            invalidate()
        }
    }

    private fun setupPieChart(records: List<com.steptracker.app.data.DayRecord>, goal: Int) {
        val low = records.count { it.steps in 1..4999 }.toFloat()
        val mid = records.count { it.steps in 5000..9999 }.toFloat()
        val high = records.count { it.steps >= 10000 }.toFloat()

        if (low + mid + high == 0f) return

        val entries = mutableListOf<PieEntry>()
        if (low > 0) entries.add(PieEntry(low, "Низька\n(<5k)"))
        if (mid > 0) entries.add(PieEntry(mid, "Середня\n(5-10k)"))
        if (high > 0) entries.add(PieEntry(high, "Висока\n(>10k)"))

        val colors = listOf(0xFFFAC775.toInt(), 0xFF5DCAA5.toInt(), 0xFF1D9E75.toInt())

        val dataSet = PieDataSet(entries, "").apply {
            setColors(colors)
            valueTextSize = 12f
            valueTextColor = Color.WHITE
            sliceSpace = 2f
        }

        binding.pieChart.apply {
            data = PieData(dataSet)
            description.isEnabled = false
            isDrawHoleEnabled = true
            holeRadius = 50f
            setHoleColor(Color.TRANSPARENT)
            setEntryLabelColor(Color.DKGRAY)
            setEntryLabelTextSize(11f)
            legend.textSize = 12f
            animateY(600)
            invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
