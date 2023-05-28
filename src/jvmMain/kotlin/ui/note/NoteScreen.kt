package ui.note

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import util.DateUtil.generateDatesForMonth
import util.DateUtil.parseDate
import java.util.*

@ExperimentalMaterialApi
@Composable
fun NoteGridScreen(
    state: NoteState,
    onEvent: (NoteEvent) -> Unit,
) {
    val localDensity = LocalDensity.current

    var calendarHeightDp by remember { mutableStateOf(0.dp) }

    val selectedDate = Calendar.getInstance().apply { add(Calendar.MONTH, state.monthOffset) }
    val displayYear = selectedDate.get(Calendar.YEAR)
    val displayMonth = selectedDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())

    val dateItems = generateDatesForMonth(selectedDate)
    val rows = dateItems.size / 7

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                onEvent(NoteEvent.OnPreviousMonth)
            }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Previous month")
            }
            Text(
                text = "$displayYear $displayMonth",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
            IconButton(onClick = {
                onEvent(NoteEvent.OnNextMonth)
            }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Next month")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        WeekDayGrid()
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned { coordinates ->
                calendarHeightDp = with(localDensity) { coordinates.size.height.toDp() }
            },
            columns = GridCells.Fixed(7)
        ) {
            itemsIndexed(dateItems) { _, date ->
                val isEnable = date.get(Calendar.MONTH) == selectedDate.get(Calendar.MONTH)
                val isNoted = state.notes?.any { it.date.contains(parseDate(date)) }

                Card(
                    modifier = Modifier
                        .padding(4.dp),
                    shape = RoundedCornerShape(16.dp),
                    enabled = isEnable,
                    onClick = {
                        onEvent(NoteEvent.OnDateSelect(parseDate(date)))
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((calendarHeightDp + 4.dp - 8.dp * rows) / rows)
                            .alpha(
                                if (isEnable) 1f
                                else 0.3f
                            )
                            .background(
                                if (isNoted == true) Color.Green
                                else Color.White
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = date.get(Calendar.DAY_OF_MONTH).toString(),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WeekDayGrid() {
    val weekDay = listOf("日", "一", "二", "三", "四", "五", "六")
    LazyVerticalGrid(
        columns = GridCells.Fixed(7)
    ) {
        items(weekDay.size) { index ->
            val day = weekDay[index]
            Text(
                text = day,
                textAlign = TextAlign.Center
            )
        }
    }
}