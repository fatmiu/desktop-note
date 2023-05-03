package ui.note

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
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
    val selectedDate = Calendar.getInstance().apply { add(Calendar.MONTH, state.monthOffset) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
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
                text = selectedDate.getDisplayName(
                    Calendar.MONTH, Calendar.LONG, Locale.getDefault()
                ),
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
        WeekDayGrid()
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(7)
        ) {
            itemsIndexed(
                generateDatesForMonth(selectedDate)
            ) { _, date ->
                val isEnable = date.get(Calendar.MONTH) == selectedDate.get(Calendar.MONTH)
                val isNoted = state.notes?.any { it.date.contains(parseDate(date)) }

                Card(
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth()
                        .padding(4.dp),
                    shape = RoundedCornerShape(16.dp),
                    enabled = isEnable,
                    onClick = {
                        onEvent(NoteEvent.OnDateSelect(parseDate(date)))
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
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