package jp.co.yumemi.android.code_check.feature.home.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import jp.co.yumemi.android.code_check.core.model.GhOrder
import jp.co.yumemi.android.code_check.core.model.GhRepositorySort
import me.matsumo.yumemi.codecheck.R

@Composable
internal fun HomeSearchSettingDialog(
    selectedOrder: GhOrder,
    selectedSort: GhRepositorySort,
    onClickUpdateSetting: (GhOrder, GhRepositorySort) -> Unit,
    onDismiss: () -> Unit,
) {
    val (order, setOrder) = remember { mutableStateOf(selectedOrder) }
    val (sort, setSort) = remember { mutableStateOf(selectedSort) }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            OrderSection(
                modifier = Modifier.fillMaxWidth(),
                selectedOrder = order,
                onClickOrder = { setOrder.invoke(it) },
            )

            SortSection(
                modifier = Modifier.fillMaxWidth(),
                selectedSort = sort,
                onClickSort = { setSort.invoke(it) },
            )

            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(4.dp),
                    onClick = { onDismiss.invoke() },
                ) {
                    Text(text = stringResource(R.string.common_cancel))
                }

                Button(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                    onClick = {
                        onClickUpdateSetting.invoke(order, sort)
                        onDismiss.invoke()
                    },
                ) {
                    Text(text = stringResource(R.string.common_ok))
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun OrderSection(
    selectedOrder: GhOrder,
    onClickOrder: (GhOrder) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Default.SwapVert,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )

            Text(
                text = stringResource(R.string.search_order),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        HorizontalDivider()

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            for (order in GhOrder.entries) {
                FilterChip(
                    selected = (selectedOrder == order),
                    onClick = { onClickOrder.invoke(order) },
                    label = {
                        Text(order.value)
                    },
                    leadingIcon = {
                        if (selectedOrder == order) {
                            Icon(
                                modifier = Modifier.size(FilterChipDefaults.IconSize),
                                imageVector = Icons.Default.Done,
                                contentDescription = null,
                            )
                        }
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SortSection(
    selectedSort: GhRepositorySort,
    onClickSort: (GhRepositorySort) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Default.SwapVert,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )

            Text(
                text = stringResource(R.string.search_sort),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        HorizontalDivider()

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            for (order in GhRepositorySort.entries) {
                FilterChip(
                    selected = (selectedSort == order),
                    onClick = { onClickSort.invoke(order) },
                    label = {
                        Text(order.value)
                    },
                    leadingIcon = {
                        if (selectedSort == order) {
                            Icon(
                                modifier = Modifier.size(FilterChipDefaults.IconSize),
                                imageVector = Icons.Default.Done,
                                contentDescription = null,
                            )
                        }
                    },
                )
            }
        }
    }
}
