package com.example.pruebaandroid3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore

class TaskDetailActivity : ComponentActivity() {
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = FirebaseFirestore.getInstance()
        val taskId = intent.getStringExtra("taskId") ?: return
        val isDone = intent.getBooleanExtra("isDone", false)
        setContent {
            TaskDetailScreen(db, taskId, isDone, this)
        }
    }
}

@Composable
fun TaskDetailScreen(db: FirebaseFirestore, taskId: String, isDone: Boolean, activity: ComponentActivity) {
    var task by remember { mutableStateOf<Task?>(null) }

    LaunchedEffect(taskId, isDone) {
        val collection = if (isDone) "doneTasks" else "pendingTasks"
        db.collection(collection).document(taskId).get().addOnSuccessListener { document ->
            task = document.toObject(Task::class.java)
        }
    }

    task?.let {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text("Tarea", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(it.name.uppercase(), fontSize = 24.sp)
            Text("Descripción de la Tarea: ${it.description}")
            Text("Fecha de la Tarea: ${it.date}")
            Text(
                text = if (it.priority) "URGENTE" else "No Urgente",
                color = if (it.priority) Color.Red else Color.Black
            )
            Text("Costo de la Tarea: ${it.cost}")
            Spacer(modifier = Modifier.weight(1f))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = {
                    activity.finish()
                }) {
                    Text("Volver a la Lista")
                }
                Button(onClick = {
                    val newStatus = !isDone
                    val fromCollection = if (isDone) "doneTasks" else "pendingTasks"
                    val toCollection = if (newStatus) "doneTasks" else "pendingTasks"
                    db.collection(fromCollection).document(taskId).delete()
                    db.collection(toCollection).document(taskId).set(it.copy(isDone = newStatus))
                    activity.finish()
                }) {
                    Text(if (isDone) "Marcar como Pendiente" else "Marcar como Hecha")
                }
            }
        }
    }
}