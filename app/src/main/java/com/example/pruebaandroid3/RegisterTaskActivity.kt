package com.example.pruebaandroid3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore

class RegisterTaskActivity : ComponentActivity() {
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = FirebaseFirestore.getInstance()
        setContent {
            RegisterTaskScreen(db, this)
        }
    }
}

@Composable
fun RegisterTaskScreen(db: FirebaseFirestore, activity: ComponentActivity) {
    var taskName by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var taskDate by remember { mutableStateOf("") }
    var taskPriority by remember { mutableStateOf(false) }
    var taskCost by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Nueva Tarea", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = taskName,
            onValueChange = { taskName = it },
            label = { Text("Nombre de la Tarea") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = taskDescription,
            onValueChange = { taskDescription = it },
            label = { Text("Descripción de la Tarea") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = taskDate,
            onValueChange = { taskDate = it },
            label = { Text("Fecha de la Tarea") },
            modifier = Modifier.fillMaxWidth()
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = taskPriority,
                onCheckedChange = { taskPriority = it }
            )
            Text("Urgente")
        }
        TextField(
            value = taskCost,
            onValueChange = { taskCost = it },
            label = { Text("Costo de la Tarea") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = {
                activity.finish()
            }) {
                Text("Cancelar")
            }
            Button(onClick = {
                val newTaskRef = db.collection("pendingTasks").document()
                val newTask = Task(
                    id = newTaskRef.id,
                    name = taskName,
                    description = taskDescription,
                    date = taskDate,
                    priority = taskPriority,
                    cost = taskCost.toDoubleOrNull() ?: 0.0
                )
                newTaskRef.set(newTask)
                activity.finish()
            }) {
                Text("Registrar Tarea")
            }
        }
    }
}