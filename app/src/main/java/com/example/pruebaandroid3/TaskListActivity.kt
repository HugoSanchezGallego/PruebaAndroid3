package com.example.pruebaandroid3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore

class TaskListActivity : ComponentActivity() {
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = FirebaseFirestore.getInstance()
        setContent {
            TaskListScreen(db, this)
        }
    }
}

@Composable
fun TaskListScreen(db: FirebaseFirestore, activity: ComponentActivity) {
    var tasks by remember { mutableStateOf(listOf<Task>()) }
    var showDoneTasks by remember { mutableStateOf(false) }

    LaunchedEffect(showDoneTasks) {
        val collection = if (showDoneTasks) "doneTasks" else "pendingTasks"
        db.collection(collection).addSnapshotListener { snapshot, _ ->
            tasks = snapshot?.toObjects(Task::class.java) ?: listOf()
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Lista de Tareas", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(tasks) { task ->
                TaskItem(task) {
                    val intent = Intent(activity, TaskDetailActivity::class.java)
                    intent.putExtra("taskId", task.id)
                    intent.putExtra("isDone", showDoneTasks)
                    activity.startActivity(intent)
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { showDoneTasks = true }) {
                Text("Hechas")
            }
            Button(onClick = { showDoneTasks = false }) {
                Text("Pendientes")
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(task.description)
        }
    }
}