package com.example.contentprovidertaker

import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.example.contentprovidertaker.adapter.UserDataAdapter
import com.example.contentprovidertaker.objects.UserData

class MainActivity : AppCompatActivity() {
    private lateinit var loginText: EditText
    private lateinit var passwordText: EditText
    private lateinit var listView: ListView
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        button.setOnClickListener { fetchUserData(); closeKeyboard(); clearFields() }
    }

    private fun fetchUserData() {
        val selectionArgs = arrayOf(
            loginText.text.toString(),
            passwordText.text.toString()
        )
        val userData = mutableListOf<UserData>()
        val cursor =
            contentResolver.query(contentUri, projection, selectionClause, selectionArgs, null)

        cursor?.use { crs ->
            if (crs.moveToFirst()) {
                do {
                    val login = crs.getString(0)
                    val password = crs.getString(1)
                    val createdBy = crs.getString(2)
                    val id = crs.getString(3).toLong()
                    userData.add(UserData(id, login, password, createdBy))
                } while (crs.moveToNext())
                listView.adapter = UserDataAdapter(this, userData as ArrayList<UserData>)
            } else {
                Toast.makeText(this, "Nie znaleziono żadnych danych logowania", Toast.LENGTH_SHORT)
                    .show()
                userData.clear()
                listView.adapter = UserDataAdapter(this, userData as ArrayList<UserData>)
            }
        } ?: Toast.makeText(this, "Nie ma usera o podanych danych", Toast.LENGTH_SHORT).show()
    }

    private fun closeKeyboard() {
        currentFocus?.let {
            val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(it.windowToken, 0)
            it.clearFocus()
        }
    }

    private fun clearFields() {
        loginText.text.clear()
        passwordText.text.clear()
    }

    private fun initUI() {
        loginText = findViewById(R.id.loginText)
        passwordText = findViewById(R.id.passwordText)
        listView = findViewById(R.id.listView)
        button = findViewById(R.id.button)
    }

    private companion object {
        private const val providerName = "com.example.bamfinalproject.authorities.LOGIN_PASSWORD"
        private const val tableName = "userData"
        val contentUri: Uri = Uri.parse("content://$providerName/$tableName")

        val projection = arrayOf("*")
        const val selectionClause = "login = ? AND password = ?"
    }
}