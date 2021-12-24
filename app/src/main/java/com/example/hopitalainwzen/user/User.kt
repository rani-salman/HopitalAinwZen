package com.example.hopitalainwzen

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties
import java.io.Serializable


@IgnoreExtraProperties
    data class User(
        var doctorProperties: DoctorProperties? = DoctorProperties("",""),
        var patientProperties: PatientProperties? = PatientProperties("","","","",""),
        var token: String? = "",
        var name: String? = "",
        var type: String?= "",
        var userId:String?="") :Serializable {
    companion object {
            const val FIELD_TYPE="type"
            const val FIELD_DOCTOR_NAME = "name"
            const val FIELD_DOMAIN = "domain"
            const val FIELD_ID = "userId"
        }
        }
