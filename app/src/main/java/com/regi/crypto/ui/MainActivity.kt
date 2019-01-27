package com.regi.crypto.ui

import android.os.Bundle
import android.os.Handler
import android.text.format.DateUtils
import android.view.Gravity
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.android.material.snackbar.Snackbar
import com.regi.crypto.R
import com.regi.crypto.injection.ViewModelFactory
import com.regi.crypto.model.Price
import com.robinhood.ticker.TickerUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private val entries = ArrayList<Entry>()
    private var isAdvancedButton = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
        setupViewModel()
    }

    private fun setupViews() {
        tickerView.setCharacterLists(TickerUtils.provideAlphabeticalList())
        tickerView.animationDuration = 500
        tickerView.animationInterpolator = OvershootInterpolator()
        tickerView.gravity = Gravity.START
        tickerView.typeface = ResourcesCompat.getFont(this, R.font.roboto_thin)
        tickerView.setOnClickListener {
            //Something fun ;)
            tickerView.isClickable = false
            tickerView.text = getString(R.string.all_time_high)
            tv_ath_text.visibility = View.VISIBLE
            lottie_rocket.playAnimation()
            Handler().postDelayed({
                viewModel.loadItems()
                tv_ath_text.visibility = View.INVISIBLE
                tickerView.isClickable = true
            }, 7000)
        }

        btn_advanced.setOnClickListener {
            btn_advanced.text = if (isAdvancedButton) {
                getString(R.string.advanced)
            } else {
                getString(R.string.basic)
            }
            isAdvancedButton = !isAdvancedButton
            chart.highlightValues(null)
            setupChart(isAdvancedButton)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(MainViewModel::class.java)
        viewModel.priceList().observe(this, Observer {
            popuplateChartData(it)
            tickerView.text = getString(R.string.formatted_string_with_currency).format(it.last().y)
            tv_update_status.text = getString(R.string.formatted_update_period)
                .format(DateUtils.getRelativeTimeSpanString(it.last().x * 1000L))
            tv_duration.text = getString(R.string.formatted_duration_period).format(it.size)
            lottie_view.visibility = View.GONE
            cardview_price.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        })
        viewModel.errorOccurred().observe(this, Observer {
            tickerView.text = it
            cardview_price.visibility = View.INVISIBLE
            lottie_view.visibility = View.VISIBLE
            lottie_view.playAnimation()
            Snackbar.make(root_view, getString(R.string.something_went_wrong), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry)) { viewModel.loadItems() }.show()
            progressBar.visibility = View.GONE
        })
    }

    private fun popuplateChartData(prices: List<Price>) {
        entries.clear()
        prices.forEachIndexed { index, price ->
            entries.add(Entry(index.toFloat(), price.y))
        }
        setupChart()
    }

    private fun setupChart(advanced: Boolean = false) {
        chart.invalidate()
        //So much tweaking so that it doesnt look like a cluttered mess
        val lineDataSet = LineDataSet(entries, "")
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        lineDataSet.color = ContextCompat.getColor(this, R.color.cardBackgroundWhite)
        lineDataSet.setDrawValues(advanced)
        lineDataSet.setDrawCircles(advanced)
        lineDataSet.highLightColor = ContextCompat.getColor(this, R.color.colorBackground)

        val lineData = LineData(lineDataSet)
        chart.data = lineData
        chart.axisLeft.setDrawGridLines(advanced)
        chart.xAxis.setDrawGridLines(advanced)
        chart.legend.isEnabled = advanced

        val test = Description()
        test.text = ""
        chart.description = test
        chart.axisLeft.setDrawLabels(advanced)
        chart.axisRight.setDrawLabels(advanced)
        chart.xAxis.setDrawLabels(advanced)
        chart.xAxis.setDrawLabels(advanced)
        chart.legend.isEnabled = advanced
        chart.setScaleEnabled(advanced)
        chart.isHighlightPerTapEnabled = advanced


        chart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry, h: Highlight) {
                tv_highlight_price.text = getString(R.string.formatted_string_with_currency).format(h.y)
            }

            override fun onNothingSelected() {}
        })

        updatePercentage()
    }

    private fun updatePercentage() {
        if (entries.isEmpty()) return

        val decrease = entries.first().y - entries.last().y
        val amount = (decrease / entries.first().y) * 100
        if (amount < 0) {
            ic_arrow.rotation = 180f
        }
        tv_chart_price.text = getString(R.string.formatted_string_percentage).format(amount)
    }
}