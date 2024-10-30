# PruebaAndroid3 - Gestión de Tareas

## Enlace del repositorio --> [https://github.com/HugoSanchezGallego/PruebaAndroid3.git](https://github.com/HugoSanchezGallego/PruebaAndroid3.git)

Este proyecto de Android en Kotlin implementa una aplicación de gestión de tareas utilizando Firebase Firestore para almacenamiento en la nube. Los usuarios pueden registrar nuevas tareas, ver detalles de las tareas, y marcar tareas como hechas o pendientes. La interfaz está desarrollada usando Jetpack Compose.

## Estructura de Clases

### 1. MainActivity

`MainActivity` es la actividad principal de la aplicación. Proporciona botones para navegar a la lista de tareas y para registrar una nueva tarea.

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(this)
        }
    }
}
```

#### Métodos

- `onCreate`: Configura la interfaz de usuario y carga `MainScreen`.

### 2. TaskListActivity

`TaskListActivity` muestra una lista de tareas, permitiendo al usuario ver tareas pendientes o hechas.

```kotlin
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
```

#### Métodos

- `onCreate`: Inicializa Firebase Firestore y carga `TaskListScreen`.

### 3. TaskDetailActivity

`TaskDetailActivity` muestra los detalles de una tarea seleccionada y permite cambiar su estado entre pendiente y hecha.

```kotlin
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
```

#### Métodos

- `onCreate`: Inicializa Firebase Firestore y carga `TaskDetailScreen` con los detalles de la tarea.

### 4. RegisterTaskActivity

`RegisterTaskActivity` permite al usuario registrar una nueva tarea en la colección de tareas pendientes.

```kotlin
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
```

#### Métodos

- `onCreate`: Inicializa Firebase Firestore y carga `RegisterTaskScreen`.

### 5. Task

`Task` es una clase de datos que representa una tarea en la aplicación.

```kotlin
data class Task(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val date: String = "",
    val priority: Boolean = false,
    val cost: Double = 0.0,
    val isDone: Boolean = false
)
```

#### Propiedades

- `id`: Identificador único de la tarea.
- `name`: Nombre de la tarea.
- `description`: Descripción de la tarea.
- `date`: Fecha de la tarea.
- `priority`: Indica si la tarea es urgente.
- `cost`: Costo asociado a la tarea.
- `isDone`: Indica si la tarea está hecha.

## Dependencias Externas

- `FirebaseFirestore`: Proporciona acceso a Firestore para almacenar y leer los datos de las tareas.

## Ejecución y Funcionalidad

La aplicación permite al usuario:

- Registrar nuevas tareas en la colección de tareas pendientes.
- Ver una lista de tareas pendientes o hechas.
- Ver los detalles de una tarea seleccionada.
- Marcar una tarea como hecha o pendiente.

## Requerimientos Previos

Asegúrese de configurar Firebase Firestore en su proyecto de Firebase y de agregar la configuración correspondiente a `google-services.json` en el proyecto de Android.
