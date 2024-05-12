package jp.co.yumemi.android.code_check.core.ui.extensions

import androidx.compose.runtime.saveable.Saver

val IntRangeSaver = Saver<IntRange, Pair<Int, Int>>(
    save = { it.first to it.last },
    restore = { it.first..it.second },
)
