package com.example.cryptoprices.presentation.coin_details

import android.icu.text.CompactDecimalFormat
import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cryptoprices.presentation.GreenUp
import com.example.cryptoprices.presentation.RedDown
import com.example.cryptoprices.presentation.coin_details.components.MySparkAdapter
import com.robinhood.spark.SparkView
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailsScreen(
    navController: NavController,
    viewModel: CoinDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    state.coin?.let { coin ->
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        androidx.compose.material3.Text(
                            text = coin.name,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Go back"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {  }) {
                            AsyncImage(
                                model = coin.image.thumb,
                                contentDescription = coin.name + "logo",
                                modifier = Modifier.size(25.dp)
                            )
                        }

                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.background)
                )
            },
            content = { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(MaterialTheme.colorScheme.background)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(20.dp),

                    ){
                    // Spark chart
                    AndroidView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(vertical = 30.dp),
                        factory = {
                            SparkView(it)
                        },
                        update = { sparkView ->
                            sparkView.adapter = MySparkAdapter(coin.market_data.sparkline_7d.price)
                            var color = RedDown.hashCode()
                            if(coin.market_data.sparkline_7d.price.last() >= coin.market_data.sparkline_7d.price[0]) {
                                color = GreenUp.hashCode()
                            }
                            sparkView.lineColor = color
                            sparkView.baseLineWidth = 0.5.toFloat()
                        }
                    )
                    Row(modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // current day
                        val calendar = Calendar.getInstance()
                        val year = calendar.get(Calendar.YEAR)
                        // Indexes for the month start from 0, for some odd reason
                        val month = calendar.get(Calendar.MONTH) + 1
                        val day = calendar.get(Calendar.DAY_OF_MONTH)
                        // 7 days before
                        for (i in 1..7){
                            calendar.add(Calendar.DATE, -1);
                        }
                        val prevYear = calendar.get(Calendar.YEAR)
                        // Indexes for the month start from 0, for some odd reason
                        val prevMonth = calendar.get(Calendar.MONTH) + 1
                        val prevDay = calendar.get(Calendar.DAY_OF_MONTH)

                        Column(

                        ) {
                            Text(
                                text = if(coin.market_data.sparkline_7d.price[0] - 1 < 0) "$" + String.format("%.9f", coin.market_data.sparkline_7d.price[0]) else "$" + String.format("%.4f", coin.market_data.sparkline_7d.price[0]),
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "${prevMonth}/${prevDay}/${prevYear}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = if(coin.market_data.sparkline_7d.price.last() - 1 < 0) "$" + String.format("%.9f", coin.market_data.sparkline_7d.price.last()) else "$" + String.format("%.4f", coin.market_data.sparkline_7d.price.last()),
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "${month}/${day}/${year}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }
                    Text(
                        text = "Overview",
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(start = 30.dp)
                    )
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 30.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(3.dp),
                            modifier = Modifier.weight(0.5f)
                        ) {
                            Text(
                                text = "Current Price",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                            Text(
                                text = if(coin.market_data.current_price.usd - 1 < 0) "$" + String.format("%.8f", coin.market_data.current_price.usd) else "$${coin.market_data.current_price.usd}",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = if(coin.market_data.price_change_percentage_24h >= 0) "+" + String.format("%.2f", coin.market_data.price_change_percentage_24h) + "%" else String.format("%.2f", coin.market_data.price_change_percentage_24h) + "%",
                                style = MaterialTheme.typography.bodySmall,
                                color = if(coin.market_data.price_change_percentage_24h >= 0) GreenUp else RedDown,

                                )
                        }
                        Column(
                            verticalArrangement = Arrangement.spacedBy(3.dp),
                            modifier = Modifier.weight(0.5f)
                        ) {
                            Text(
                                text = "Market Capitalization",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                            Text(
                                text = "$" + CompactDecimalFormat.getInstance(Locale.getDefault(), CompactDecimalFormat.CompactStyle.SHORT).format(coin.market_data.market_cap.usd),
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = if(coin.market_data.market_cap_change_percentage_24h >= 0) "+" + String.format("%.2f", coin.market_data.market_cap_change_percentage_24h) + "%" else String.format("%.2f", coin.market_data.market_cap_change_percentage_24h) + "%",
                                style = MaterialTheme.typography.bodySmall,
                                color = if(coin.market_data.market_cap_change_percentage_24h >= 0) GreenUp else RedDown,
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 30.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(3.dp),
                            modifier = Modifier.weight(0.5f)
                        ) {
                            Text(
                                text = "Rank",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                            Text(
                                text = "${coin.market_cap_rank}",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        Column(
                            verticalArrangement = Arrangement.spacedBy(3.dp),
                            modifier = Modifier.weight(0.5f)
                        ) {
                            Text(
                                text = "Volume",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                            Text(
                                "$" + CompactDecimalFormat.getInstance(Locale.getDefault(), CompactDecimalFormat.CompactStyle.SHORT).format(coin.market_data.total_volume.usd),
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                    }
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 30.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(3.dp),
                            modifier = Modifier.weight(0.5f)
                        ) {
                            Text(
                                text = "24H High",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                            Text(
                                text = if(coin.market_data.high_24h.usd - 1 < 0) "$" + String.format("%.8f", coin.market_data.high_24h.usd) else "$${coin.market_data.high_24h.usd}",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        Column(
                            verticalArrangement = Arrangement.spacedBy(3.dp),
                            modifier = Modifier.weight(0.5f)
                        ) {
                            Text(
                                text = "24H Low",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                            Text(
                                text = if(coin.market_data.low_24h.usd - 1 < 0) "$" + String.format("%.8f", coin.market_data.low_24h.usd) else "$${coin.market_data.low_24h.usd}",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                    }
                    if(coin.description.en.isNotBlank()) {
                        Divider(

                        )
                        Text(
                            text = "Description",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(start = 30.dp)
                        )
                        Text(
                            text = androidx.core.text.HtmlCompat.fromHtml(
                                coin.description.en,
                                HtmlCompat.FROM_HTML_MODE_LEGACY
                            ).toString(),
                            modifier = Modifier.padding(start = 30.dp, end = 30.dp, bottom = 30.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    if(state.error.isNotBlank()){
                        androidx.compose.material3.Text(
                            text = state.error,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        )
                    }
                    if(state.isLoading){
                        CircularProgressIndicator(
                        )
                    }
                }
            }

        )
    }


}
