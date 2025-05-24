package com.example.geopulse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TeacherDashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TeacherDashboardTheme {
                TeacherDashboard()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherDashboard() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Teacher Dashboard") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            HeaderSection()
            TeacherInfoCard()
            AttendanceSummary()
            StudentAttendanceList()
        }
    }
}

@Composable
private fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "HELLO, Teacher",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = LocalTime.now().format(DateTimeFormatter.ofPattern("h:mm a")),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun TeacherInfoCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "23010**",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = "Course: CS202",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun AttendanceSummary() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Today's Attendance",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AttendanceProgressIndicator(
                percentage = 65,
                label = "Present",
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(16.dp))

            AttendanceProgressIndicator(
                percentage = 35,
                label = "Absent",
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
private fun AttendanceProgressIndicator(
    percentage: Int,
    label: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(120.dp)
        ) {
            CircularProgressIndicator(
                progress = percentage / 100f,
                modifier = Modifier.size(120.dp),
                color = color,
                strokeWidth = 12.dp,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$percentage%",
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Composable
private fun StudentAttendanceList() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Present Students", "Absent Students")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelLarge
                        )
                    },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Divider( // Optional manual divider
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.primary
        )

        when (selectedTab) {
            0 -> StudentList(students = presentStudents)
            1 -> StudentList(students = absentStudents)
        }
    }
}

@Composable
private fun StudentList(students: List<Student>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(students) { student ->
            StudentListItem(student = student)
        }
    }
}

@Composable
private fun StudentListItem(student: Student) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = student.id,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    text = student.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = { /* TODO: handle options */ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Options"
                )
            }
        }
    }
}

// ---------------- Sample Data & Theme ------------------

data class Student(val id: String, val name: String)

val presentStudents = listOf(
    Student("STU001", "John Doe"),
    Student("STU002", "Jane Smith"),
    Student("STU003", "Robert Johnson"),
    Student("STU004", "Emily Davis"),
    Student("STU005", "Michael Brown")
)

val absentStudents = listOf(
    Student("STU006", "Sarah Wilson"),
    Student("STU007", "David Miller")
)

@Composable
fun TeacherDashboardTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF3F51B5),
            secondary = Color(0xFF607D8B),
            surface = Color.White,
            onSurface = Color(0xFF212121),
            surfaceVariant = Color(0xFFE0E0E0)
        ),
        content = content
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TeacherDashboardPreview() {
    TeacherDashboardTheme {
        TeacherDashboard()
    }
}
