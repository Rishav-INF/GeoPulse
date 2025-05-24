package com.example.geopulse

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime

object KtorClient {

    private val client = HttpClient(Android) {
        install(ContentNegotiation) { json() }
    }

    @Serializable
    data class Teacher_login(
        val teacherId: Int,
        val name: String,
        @Serializable(with = LocalDateTimeSerializer::class)
        val createdAt: LocalDateTime = LocalDateTime.now()
    )
    @Serializable
    data class Student_login(
        val studentId: Int,
        val studentEmail: String,
        val name: String,
        val classId: Int,
        val className: String, // Renamed `class` to `className` because `class` is a reserved keyword
        @Serializable(with = LocalDateTimeSerializer::class)
        val createdAt: LocalDateTime = LocalDateTime.now()// Use the current timestamp as default
    )

    @Serializable
    data class IdPass(val id: String,val pass: String)

    object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
        override val descriptor = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: LocalDateTime) {
            encoder.encodeString(value.toString())
        }

        override fun deserialize(decoder: Decoder): LocalDateTime {
            return LocalDateTime.parse(decoder.decodeString())
        }
    }

    private const val BASE_URL = "http://10.0.2.2:8082" // Use 10.0.2.2 for Emulator



    suspend fun StudentLogin(studentData: String): String {
        return try {
            Log.d("StudentLogin", "Sending data: $studentData")

            val response: HttpResponse = client.post("$BASE_URL/matchPassandId_Student") {
                contentType(ContentType.Application.Json)
                setBody("$studentData") // send JSON string: "12345"
            }

            val result = response.bodyAsText()
            Log.d("StudentLogin", "Response: $result")

            if (response.status.isSuccess()) result else "not"
        } catch (e: Exception) {
            Log.e("StudentLogin", "Error during login", e)
            "not"
        }
    }

    suspend fun TeacherLogin(teacherData: String): String {
        return try {
            Log.d("TeacherLogin", "Sending data: \"$teacherData\"")

            val response: HttpResponse = client.post("$BASE_URL/matchPassandId_Teacher") {
                contentType(ContentType.Application.Json)
                setBody("\"$teacherData\"") // send JSON string: "12345"
            }

            val result = response.bodyAsText()
            Log.d("TeacherLogin", "Response: $result")

            if (response.status.isSuccess()) result else "not"
        } catch (e: Exception) {
            Log.e("TeacherLogin", "Error during login", e)
            "not"
        }
    }

    suspend fun signUpTeacher(IdPass:IdPass):Boolean{
        return try{
            val response:HttpResponse = client.post("$BASE_URL/signupTeacher")
            true
        }catch (e:Exception){
            false
        }
    }

    suspend fun signUpStudent(IdPass:IdPass):Boolean{
        return try{
            val response:HttpResponse = client.post("$BASE_URL/signupStudent")
            true
        }catch (e:Exception){
            false
        }
    }

    suspend fun getStudentsFromServer(teacherId: String): String {
        return try {
            Log.d("GetMondayStudents", "Requesting students for teacherId: $teacherId")

            val response: HttpResponse = client.get("$BASE_URL/students/$teacherId") {
                contentType(ContentType.Application.Json)
            }

            val result = response.bodyAsText()
            Log.d("GetMondayStudents", "Response: $result")

            if (response.status.isSuccess()) result else "not"
        } catch (e: Exception) {
            Log.e("GetMondayStudents", "Error fetching students", e)
            "not"
        }
    }




}