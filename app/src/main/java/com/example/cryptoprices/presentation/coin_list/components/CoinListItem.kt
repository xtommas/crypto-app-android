package com.example.cryptoprices.presentation.coin_list.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cryptoprices.R
import com.example.cryptoprices.data.repository.CoinDto
import com.example.cryptoprices.presentation.GreenUp
import com.example.cryptoprices.presentation.RedDown
import java.lang.Character.toUpperCase

@Composable
fun CoinListItem(
    coin: CoinDto,
    onItemClick: (CoinDto) -> Unit
) {
    // Lining up the market cap rank in the UI
    var rowStartPadding = 30.dp
    if(coin.market_cap_rank.toString().length == 2) {
        rowStartPadding = 25.dp
    } else if(coin.market_cap_rank.toString().length == 3) {
        rowStartPadding = 19.dp
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = rowStartPadding, end = 30.dp, top = 10.dp, bottom = 10.dp)
            .clickable { onItemClick(coin) },
        verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "${coin.market_cap_rank}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            AsyncImage(
                model = coin.image,
                contentDescription = coin.name + "logo",
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .size(25.dp)
            )
            Column(

            ) {
                Text(
                    text = coin.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = coin.symbol.uppercase(),
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Spacer(Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = if(coin.current_price - 1 < 0) "$" + String.format("%.8f", coin.current_price) else "$${coin.current_price}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if(coin.price_change_percentage_24h >= 0) "+" + String.format("%.2f", coin.price_change_percentage_24h) + "%" else String.format("%.2f", coin.price_change_percentage_24h) + "%",
                    style = MaterialTheme.typography.labelSmall,
                    color = if(coin.price_change_percentage_24h >= 0) GreenUp else RedDown
                )
            }
        }
}