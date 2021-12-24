package com.example.hopitalainwzen

import com.google.firebase.firestore.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class DoctorProperties(
        var doctorname :String ="",
        var domain :String? = ""
        ): Serializable {
    companion object {
        const val FIELD_DOCTOR_NAME = "doctorname"
        const val FIELD_DOMAIN = "domain"
    }
        }
