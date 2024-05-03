package com.example.samplewebapplication

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.samplewebapplication.ui.theme.BottomBarColor
import kotlinx.coroutines.flow.MutableStateFlow


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RowScope.FooterButton(
    identifier: Int,
    @DrawableRes iconSelected: Int,
    @DrawableRes iconUnSelected: Int,
    @StringRes label: Int,
    isSelected: Boolean,
    onClick: (identifier: Int) -> Unit = {},
    focusRequester: FocusRequester
) {
    val contentDesc = "${stringResource(id = label)}"
    NavigationBarItem(
        modifier = if (identifier == FooterButton.HOME.ordinal) {
            Modifier.semantics { contentDescription = contentDesc }.focusRequester(focusRequester)
        } else {
            Modifier.semantics { contentDescription = contentDesc }.focusRequester(focusRequester)
        },
        onClick = {
            onClick(identifier)
        },
        label = {
            Text(
                text = stringResource(id = label),
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.semantics {
                    this.invisibleToUser()
                }
            )
        },
        icon = {
            Icon(
                painterResource(
                    if (isSelected) {
                            iconSelected
                    } else {
                            iconUnSelected
                    }
                ),
                contentDescription = "$label Icon",
                tint = Color.Unspecified
            )
        },
        selected = isSelected,
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = BottomBarColor,
            selectedTextColor = White,
            unselectedTextColor = White
        )
    )
}

enum class FooterButton(
    @StringRes val label: Int,
    @DrawableRes val iconSelected: Int,
    @DrawableRes val iconUnSelected: Int,
) {
    HOME(R.string.home_icon, R.drawable.selected_home, R.drawable.outline_home),
    SETTINGS(
        R.string.settings,
        R.drawable.selected_settings,
        R.drawable.outline_settings,
    ),
    CALENDAR(R.string.calendar, R.drawable.selected_calendar, R.drawable.outline_calendar),
    MESSAGES(
        R.string.messages,
        R.drawable.selected_message,
        R.drawable.outline_message,
    )
}

@Composable
fun HomeScreenFooter(
    selectedTabIndex:Int,
    onClickedFooterButton: (button: FooterButton) -> Unit,
    focusRequester: FocusRequester
) {
    androidx.compose.material3.NavigationBar(
        containerColor = BottomBarColor,
        contentColor = White,
        modifier = Modifier.height(80.dp)
//            .focusRequester(focusRequester)
//            .focusable(true)
    ) {
        FooterButton.values().forEach { button ->
            FooterButton(
                button.ordinal,
                button.iconSelected,
                button.iconUnSelected,
                button.label,
                selectedTabIndex == button.ordinal,
                onClick = { onClickedFooterButton(FooterButton.values()[it]) },
                focusRequester
            )
        }
    }
}