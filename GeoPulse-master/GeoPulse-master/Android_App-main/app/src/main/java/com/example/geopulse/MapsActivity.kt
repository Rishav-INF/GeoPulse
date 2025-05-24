package com.example.geopulse

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

class MapsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userid = intent.getStringExtra("userid") ?: "20023001"
        val userType = intent.getStringExtra("usertype") ?: "student"
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MapLayout(fusedLocationProviderClient)
                }
            }
        }
    }
}

@Composable
fun MapLayout(fusedLocationProviderClient: FusedLocationProviderClient) {
    val context = LocalContext.current
    var bluetoothConnected by remember { mutableStateOf(false) }
    var sessionDuration by remember { mutableStateOf(0L) }

    // Request Bluetooth and location permissions before starting scan
    val permissionsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allPermissionsGranted = permissions.all { it.value }
        if (allPermissionsGranted) {
            startBeaconScanLoop(
                context = context,
                targetUUID = "a134d0b2-1da2-1ba7-c94c-e8e00c9f7a2d"
            ) { isDetected ->
                bluetoothConnected = isDetected
            }
        } else {
            Toast.makeText(context, "Permissions required for Bluetooth scanning", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        val requiredPermissions = mutableListOf<String>().apply {
            add(Manifest.permission.ACCESS_FINE_LOCATION)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                add(Manifest.permission.BLUETOOTH_SCAN)
                add(Manifest.permission.BLUETOOTH_CONNECT)
            }
        }.toTypedArray()

        if (requiredPermissions.all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }) {
            startBeaconScanLoop(
                context = context,
                targetUUID = "a134d0b2-1da2-1ba7-c94c-e8e00c9f7a2d"
            ) { isDetected ->
                bluetoothConnected = isDetected
            }
        } else {
            permissionsLauncher.launch(requiredPermissions)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        BluetoothStatusCard(connected = bluetoothConnected)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(16.dp)
        ) {
            MapsScreen(fusedLocationProviderClient)
        }

        StudentDetailsCard(
            name = "John Smith",
            studentId = "2023001",
            sessionDuration = sessionDuration
        )
    }
}

@Composable
fun BluetoothStatusCard(connected: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (connected) Color(0xFF4CAF50) else Color(0xFFF44336)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (connected) "Bluetooth Connected" else "Bluetooth Not Connected",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun StudentDetailsCard(name: String, studentId: String, sessionDuration: Long) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DetailRow("Name", name)
            DetailRow("Student ID", studentId)
            DetailRow("Session Duration", formatDuration(sessionDuration))
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Medium)
        Text(text = value)
    }
}

fun formatDuration(seconds: Long): String {
    return String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60)
}

@Composable
fun MapsScreen(fusedLocationProviderClient: FusedLocationProviderClient) {
    val context = LocalContext.current
    val currentLocation = remember { mutableStateOf<LatLng?>(null) }
    val cameraPositionState = rememberCameraPositionState()
    val fixedLocation = LatLng(23.654729, 86.473369)
    val radiusInMeters = 10.0f
    val fixedLocationMarkerState = remember { MarkerState(position = fixedLocation) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
            startLocationUpdates(
                fusedLocationProviderClient,
                currentLocation,
                cameraPositionState,
                fixedLocation,
                radiusInMeters,
                context
            )
        } else {
            Toast.makeText(context, "Location permission required", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startLocationUpdates(
                fusedLocationProviderClient,
                currentLocation,
                cameraPositionState,
                fixedLocation,
                radiusInMeters,
                context
            )
        } else {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            isMyLocationEnabled = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    ) {
        Marker(
            state = fixedLocationMarkerState,
            title = "Fixed Location"
        )

        Circle(
            center = fixedLocation,
            radius = radiusInMeters.toDouble(),
            fillColor = Color(0x40FF0000),
            strokeColor = Color(0xFFFF0000),
            strokeWidth = 5f
        )
    }
}

private fun startLocationUpdates(
    fusedLocationProviderClient: FusedLocationProviderClient,
    currentLocation: MutableState<LatLng?>,
    cameraPositionState: CameraPositionState,
    fixedLocation: LatLng,
    radiusInMeters: Float,
    context: Context
) {
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }

    val locationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY,
        10000L
    ).build()

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            result.lastLocation?.let { location ->
                currentLocation.value = LatLng(location.latitude, location.longitude)
                checkProximityToFixedLocation(location, fixedLocation, radiusInMeters, context)
                cameraPositionState.position = CameraPosition.fromLatLngZoom(
                    currentLocation.value ?: LatLng(0.0, 0.0),
                    17f
                )
            }
        }
    }

    try {
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    } catch (securityException: SecurityException) {
        Toast.makeText(context, "Location permission not granted", Toast.LENGTH_SHORT).show()
    }
}

fun checkProximityToFixedLocation(
    location: Location,
    fixedLocation: LatLng,
    radiusInMeters: Float,
    context: Context
) {
    val distance = FloatArray(1)
    Location.distanceBetween(
        location.latitude, location.longitude,
        fixedLocation.latitude, fixedLocation.longitude,
        distance
    )

    if (distance[0] < radiusInMeters) {
        sendNotification("You are within $radiusInMeters meters of the fixed location", context)
    } else {
        sendNotification("You are outside the $radiusInMeters meter radius of the fixed location", context)
    }
}


private fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            "location_channel", // Channel ID
            "Geopulse Notifications", // Channel name
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Notifications for location updates"
        }
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}


fun sendNotification(message: String, context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            NOTIFICATION_PERMISSION_REQUEST_CODE
        )
        return
    }

    // Ensure notification channel exists (for Android 8 and above)
    createNotificationChannel(context)

    createAndSendNotification(message, context)
}

private fun createAndSendNotification(message: String, context: Context) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val notificationBuilder = NotificationCompat.Builder(context, "location_channel")
        .setSmallIcon(R.drawable.logo) // Replace with your own icon
        .setContentTitle("Geopulse Notification")
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)

    try {
        notificationManager.notify(1001, notificationBuilder.build())
    } catch (e: Exception) {
        Log.e("NotificationError", "Failed to send notification", e)
        Toast.makeText(context, "Failed to send notification", Toast.LENGTH_SHORT).show()
    }
}


private const val NOTIFICATION_PERMISSION_REQUEST_CODE = 1

fun startBeaconScanLoop(
    context: Context,
    targetUUID: String,
    onStatusChanged: (Boolean) -> Unit
) {
    val handler = Handler(Looper.getMainLooper())
    val runnable = object : Runnable {
        override fun run() {
            scanForBeaconUUID(context, targetUUID) { isDetected ->
                onStatusChanged(isDetected)
                handler.postDelayed(this, 10000) // Rescan every 10 seconds
            }
        }
    }
    handler.post(runnable)
}

fun scanForBeaconUUID(
    context: Context,
    targetUUID: String,
    onBeaconDetected: (Boolean) -> Unit
) {
    // Check permissions
    val hasPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.BLUETOOTH_SCAN
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    } else {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    if (!hasPermissions) {
        onBeaconDetected(false)
        return
    }

    val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled) {
        onBeaconDetected(false)
        return
    }

    try {
        val bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
        var beaconFound = false

        val scanCallback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                super.onScanResult(callbackType, result)
                val scanRecord = result.scanRecord?.bytes
                if (scanRecord != null && containsUUID(scanRecord, targetUUID)) {
                    beaconFound = true
                    try {
                        bluetoothLeScanner.stopScan(this)
                    } catch (e: SecurityException) {
                        // Handle permission error
                    }
                    onBeaconDetected(true)
                }
            }

            override fun onScanFailed(errorCode: Int) {
                onBeaconDetected(false)
            }
        }

        try {
            bluetoothLeScanner.startScan(scanCallback)
        } catch (e: SecurityException) {
            onBeaconDetected(false)
            return
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if (!beaconFound) {
                try {
                    bluetoothLeScanner.stopScan(scanCallback)
                } catch (e: SecurityException) {
                    // Handle permission error
                }
                onBeaconDetected(false)
            }
        }, 10000)
    } catch (e: SecurityException) {
        onBeaconDetected(false)
    }
}

fun containsUUID(scanRecord: ByteArray, targetUUID: String): Boolean {
    val cleanUUID = targetUUID.replace("-", "").lowercase()
    val hexString = scanRecord.joinToString("") { "%02x".format(it) }
    return hexString.contains(cleanUUID)
}