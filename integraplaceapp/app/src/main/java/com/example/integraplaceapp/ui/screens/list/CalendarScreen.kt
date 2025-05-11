package com.example.integraplaceapp.ui.screens.list

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.provider.CalendarContract
import android.widget.CalendarView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import java.util.Calendar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(){
    val context = LocalContext.current
    var dateAux by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Agenda de Licitações"
                    )
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AndroidView(factory = { CalendarView(it) }, update = {
                    it.setOnDateChangeListener { calendarView, year, month, day ->
                        dateAux = "$day/${month + 1}/$year"
                        showDialog = true
                    }
                })
                Text(text = dateAux)
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(text = "Adicionar Evento") },
                    text = {
                        Column {
                            Text(text = "Deseja adicionar um evento para $dateAux?")
                        }
                    },
                    confirmButton = {
                        Button(onClick = {
                            val calendar = Calendar.getInstance().apply {
                                set(dateAux.split("/")[2].toInt(), dateAux.split("/")[1].toInt() - 1, dateAux.split("/")[0].toInt())
                            }
                            val startTime = calendar.timeInMillis
                            val endTime = startTime + 60 * 60 * 1000 // 1 hora depois

                            addEventToCalendar(
                                context = context,
                                title = "Novo Evento",
                                description = "Descrição do evento",
                                location = "Local do evento",
                                startTime = startTime,
                                endTime = endTime
                            )
                            showDialog = false
                        }) {
                            Text("Adicionar")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDialog = false }) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    )
}

@SuppressLint("MissingPermission")
fun addEventToCalendar(
    context: Context,
    title: String,
    description: String,
    location: String,
    startTime: Long,
    endTime: Long
): Boolean {
    val values = ContentValues().apply {
        put(CalendarContract.Events.DTSTART, startTime)
        put(CalendarContract.Events.DTEND, endTime)
        put(CalendarContract.Events.TITLE, title)
        put(CalendarContract.Events.DESCRIPTION, description)
        put(CalendarContract.Events.CALENDAR_ID, 1)
        put(CalendarContract.Events.EVENT_TIMEZONE, "UTC")
        put(CalendarContract.Events.EVENT_LOCATION, location)
    }

    val uri = context.contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
    return uri != null
}


@Preview
@Composable
fun CalendarScreenPreview(){
    CalendarScreen()
}