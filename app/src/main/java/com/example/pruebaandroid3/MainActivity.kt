package com.example.pruebaandroid3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(this)
        }
    }
}

@Composable
fun MainScreen(activity: ComponentActivity) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            val intent = Intent(activity, TaskListActivity::class.java)
            activity.startActivity(intent)
        }) {
            Text("Ver Tareas")
        }
        Button(onClick = {
            val intent = Intent(activity, RegisterTaskActivity::class.java)
            activity.startActivity(intent)
        }) {
            Text("Registrar Tarea")
        }
    }
}