package com.samnn.filluserinformation

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private val myFormat = "dd/MM/yyyy"

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstName = findViewById<EditText>(R.id.first_name)
        val lastName = findViewById<EditText>(R.id.last_name)

        val gender = findViewById<RadioGroup>(R.id.gender)

        val birthday = findViewById<EditText>(R.id.birthday)
        val birthdaySelect = findViewById<Button>(R.id.select)

        val address = findViewById<EditText>(R.id.address)

        val email = findViewById<EditText>(R.id.email)

        val terms = findViewById<CheckBox>(R.id.terms)

        val register = findViewById<Button>(R.id.register)

        val cal = Calendar.getInstance()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // mention the format you need
                val sdf = SimpleDateFormat(myFormat)
                birthday.setText(sdf.format(cal.time))

            }

        val datePicker = DatePickerDialog(
            this@MainActivity,
            dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )

        birthdaySelect.setOnClickListener {
            datePicker.show()
        }

        register.setOnClickListener {
            validator(firstName, lastName, gender, birthday, address, email, terms)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun validator(
        firstName: EditText,
        lastName: EditText,
        gender: RadioGroup,
        birthday: EditText,
        address: EditText,
        email: EditText,
        terms: CheckBox
    ) {
        //alert("${firstName.text} ${lastName.text} ${gender.id} ${birthday.text} ${address.text} ${email.text} ${terms.id}")
        //Check first name
        val firstNameRegex = Regex("^[a-zA-Z0-9]+$")
        if (!firstNameRegex.matches(firstName.text)) {
            alert("Your first name is invalid.")
            return
        }
        //Check last name
        //Nothing for check

        //Check gender
        val selectedGender = gender.checkedRadioButtonId
        if (selectedGender < 0) {
            alert("Please select your gender.")
            return
        }
        //Check birthday
        try {
            val date = SimpleDateFormat(myFormat).parse(birthday.text.toString())
        }
        catch (e: Exception) {
            alert("Your birthday input needs to be $myFormat.")
            return
        }
        //Check address
        //Nothing for check

        //Check email
        val emailRegex = Regex("^[\\w\\-.]+@([\\w\\-]+\\.)+[\\w\\-]{2,4}\$")
        if (!emailRegex.matches(email.text)) {
            alert("Email is not valid.")
            return
        }

        //Check terms of use
        if (!terms.isChecked) {
            alert("Please agree our Terms of Services.")
            return
        }

        alert("Register successfully!")
    }

    private fun alert(text: String) {
        val toast = Toast.makeText(this@MainActivity, text, Toast.LENGTH_LONG)
        toast.show()
    }
}