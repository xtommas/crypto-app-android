package com.example.cryptoprices.presentation.coin_list

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cryptoprices.presentation.Screen
import com.example.cryptoprices.presentation.coin_list.components.CoinListItem
import com.example.cryptoprices.presentation.coin_list.components.ScrollToTop
import com.example.cryptoprices.presentation.coin_list.components.isScrollingUp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun CoinListScreen(
    navController: NavController,
    viewModel: CoinListViewModel = hiltViewModel()
    ) {
    val state = viewModel.state.value

    // Pull to refresh
    val refreshScope = rememberCoroutineScope()
    fun refresh() = refreshScope.launch {
        viewModel.getCoins()
    }
    val ptrState = rememberPullRefreshState(state.isLoading, ::refresh)

    // Scroll to top
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Live Prices",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .pullRefresh(ptrState),
            ){
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    state = listState,
                ){
                    if (!state.isLoading) {
                        items(state.coins) { coin ->
                            CoinListItem(
                                coin = coin,
                                onItemClick = {
                                    navController.navigate(Screen.CoinDetailsScreen.route + "/${coin.id}")
                                }
                            )
                        }
                    }
                }
                // Pull to refresh
                PullRefreshIndicator(state.isLoading, ptrState, Modifier.align(Alignment.TopCenter))

                // Scroll to top
                AnimatedVisibility(visible = !listState.isScrollingUp(), enter = fadeIn(), exit = fadeOut()) {
                    ScrollToTop {
                        scope.launch {
                            listState.animateScrollToItem(0)
                        }
                    }
                }
                if(state.error.isNotBlank()){
                    Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .align(Alignment.Center)
                    )
                }
//                if(state.isLoading){
//                    CircularProgressIndicator(
//                        modifier = Modifier.align(Alignment.Center)
//                    )
//                }
            }
        }
    )

}
