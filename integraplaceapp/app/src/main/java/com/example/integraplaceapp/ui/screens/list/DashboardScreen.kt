package com.example.integraplaceapp.ui.screens.list

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.integraplaceapp.R
import com.example.integraplaceapp.data.model.PizzaGraphData
import com.example.integraplaceapp.data.network.ApiClient
import com.example.integraplaceapp.data.network.ApiService
import com.example.integraplaceapp.ui.theme.CharcoalGrey
import com.example.integraplaceapp.ui.theme.LimeGreen
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    val pizzaChartData = mutableStateOf<List<PizzaGraphData>>(emptyList())
    val apiService = ApiClient.retrofit.create(ApiService::class.java)
    val coroutineScope = rememberCoroutineScope()

    fun getResults() {
        coroutineScope.launch {
            try {
                val result = apiService.getOportunityResults()

                if (result.code() == 200) {
                    val responseBody = result.body()
                    val somaValues = (responseBody?.aceitas ?: 0) + (responseBody?.recusadas ?: 0)
                    if (somaValues > 0) {
                        val aceitasPercent = ((responseBody?.aceitas ?: 0) * 100f) / somaValues
                        val recusadasPercent = ((responseBody?.recusadas ?: 0) * 100f) / somaValues

                        pizzaChartData.value = listOf(
                            PizzaGraphData(label = "Aceitas", value = aceitasPercent),
                            PizzaGraphData(label = "Recusadas", value = recusadasPercent)
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("API", "Erro ao buscar os dados: ${e.message}")
            }
        }
    }

    LaunchedEffect(Unit) {
        getResults()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Aproveitamento de Licitações") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Gráfico de Aceitas x Recusadas",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            PizzaChart(pizzaChartData = pizzaChartData.value)

        }

    }
}

@Composable
fun PizzaChart(pizzaChartData: List<PizzaGraphData>){
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Licitações",
                style = TextStyle.Default,
                fontStyle = FontStyle.Normal,
                fontSize = 20.sp
            )
            Column(
                modifier = Modifier
                    .padding(18.dp)
                    .size(320.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Crossfade(targetState = pizzaChartData) { pieChartData ->
                    AndroidView(factory = { context ->
                        PieChart(context).apply {
                            layoutParams = LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT,
                            )
                            this.description.isEnabled = false
                            this.isDrawHoleEnabled = false
                            this.legend.isEnabled = true
                            this.legend.textSize = 14F
                            this.legend.horizontalAlignment =
                                Legend.LegendHorizontalAlignment.CENTER
                            ContextCompat.getColor(context, R.color.primaryColor)
                        }
                    },
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(5.dp), update = {
                            updatePieChartWithData(it, pieChartData)
                        })
                }
            }
        }
    }
}

fun updatePieChartWithData(
    chart: PieChart,
    data: List<PizzaGraphData>
) {
    val entries = ArrayList<PieEntry>()

    for (i in data.indices) {
        val item = data[i]
        entries.add(PieEntry(item.value ?: 0.toFloat(), item.label ?: ""))
    }

    val ds = PieDataSet(entries, "")

    ds.colors = arrayListOf(
        LimeGreen.toArgb(),
        CharcoalGrey.toArgb()
    )

    ds.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE

    ds.xValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE

    ds.sliceSpace = 2f

    ds.valueTextSize = 18f

    ds.valueTypeface = Typeface.DEFAULT_BOLD

    val d = PieData(ds)

    chart.data = d

    chart.invalidate()
}
