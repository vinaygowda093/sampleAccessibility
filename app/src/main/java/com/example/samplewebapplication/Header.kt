package com.example.samplewebapplication

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenHeader(
    title: String? = null,
    onClickedHeaderButton: (button: HomeHeaderButton) -> Unit = {},
    one: FocusRequester,
    two: FocusRequester,
    three: FocusRequester

) {
    TopAppBar(
        modifier = Modifier.height(48.dp),
        title = { title?.let { Text(text = it) } },
        actions = {
            HomeHeaderButton.values().forEach { button ->
                HeaderButton(
                    identifier = button.ordinal,
                    icon = button.icon,
                    label = button.label,
                    onClick = { onClickedHeaderButton(HomeHeaderButton.values()[it]) },
                    one = one,
                    two = two,
                    three = three
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Gray)
    )
}


enum class HomeHeaderButton(
    @StringRes val label: Int,
    @DrawableRes val icon: Int
) {
    HELP(R.string.help, R.drawable.ic_help),
    ACCOUNT(R.string.account, R.drawable.ic_account)
}

@Composable
fun HeaderButton(
    identifier: Int,
    @DrawableRes icon: Int,
    @StringRes label: Int,
    onClick: (identifier: Int) -> Unit = {},
    one: FocusRequester,
    two: FocusRequester,
    three: FocusRequester
) {
    IconButton(
        onClick = { onClick(identifier) },
        Modifier
            .focusRequester(if (identifier == 0) one else two)
            .focusProperties { next = if (identifier == 0) two else three }
    ) {
        Icon(
            painterResource(id = icon),
            stringResource(id = label)
        )
    }
}