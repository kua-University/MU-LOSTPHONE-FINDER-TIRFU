package com.example.try5

import com.google.firebase.database.FirebaseDatabase

class CommandSender {

    private val database = FirebaseDatabase.getInstance().reference

    fun sendCommand(userId: String, command: String) {
        // Set the command in Firebase under the user's commands node
        val commandRef = database.child("users").child(userId).child("commands")

        // Push the command to the database
        commandRef.setValue(command).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Command successfully sent
                println("Command '$command' sent to user $userId")
            } else {
                // Failed to send command
                println("Failed to send command: ${task.exception?.message}")
            }
        }
    }
}
