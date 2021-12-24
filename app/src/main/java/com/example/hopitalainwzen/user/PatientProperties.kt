package com.example.hopitalainwzen

import com.google.firebase.firestore.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class PatientProperties(
        var name: String?="",
        var email: String?="",
        var phone: String?="",
        var sickness: String?="",
        var notes: String?=""
): Serializable {
    companion object {
        const val FIELD_EMAIL="email"
        const val FIELD_PATIENT_NAME = "name"
        const val FIELD_PHONE = "phone"
        const val FIELD_SICKNESS = "sickness"
        const val FIELD_NOTES = "notes"
    }
}
