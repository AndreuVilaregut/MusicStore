package com.dam.andreu.ui.formRorL.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.dam.andreu.R
import com.dam.andreu.entitats.User
import com.dam.andreu.entitats.TipusUsuari
import com.dam.andreu.singleton.AppSingleton

class Registrarse : Fragment() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var workerCheckBox: CheckBox

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_register, container, false)

        usernameEditText = rootView.findViewById(R.id.usernameEditText)
        passwordEditText = rootView.findViewById(R.id.passwordEditText)
        confirmPasswordEditText = rootView.findViewById(R.id.confirmPasswordEditText)
        registerButton = rootView.findViewById(R.id.registerButton)
        workerCheckBox = rootView.findViewById(R.id.treballador)

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (password != confirmPassword) {
                Toast.makeText(requireContext(), "Les contrasenyes no coincideixen", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (username.isNotBlank() && password.isNotBlank()) {
                if (workerCheckBox.isChecked) {
                    showAdminLoginDialog(username, password)
                } else {
                    val newUser = User(
                        id = (AppSingleton.getInstance().loadUsersFromCsv(requireContext()).size + 1),
                        nom = username,
                        contrasenya = password,
                        tipus = TipusUsuari.CLIENT
                    )

                    val users = AppSingleton.getInstance().loadUsersFromCsv(requireContext()).toMutableList()
                    users.add(newUser)
                    AppSingleton.getInstance().saveUsersToCsv(requireContext(), users)

                    AppSingleton.getInstance().setUser(newUser)

                    Toast.makeText(requireContext(), "Usuari registrat correctament!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Omple tots els camps", Toast.LENGTH_SHORT).show()
            }
        }

        return rootView
    }


    private var isDialogVisible: Boolean = false

    private fun showAdminLoginDialog(username: String, password: String) {
        if (isDialogVisible) return
        isDialogVisible = true

        val a = AlertDialog.Builder(requireContext())
        a.setTitle("Autenticació Admin")
        a.setMessage("Introdueix el nom d'usuari i la contrasenya d'admin per registrar el treballador.")

        val scrollView = ScrollView(requireContext())
        val linearLayout = LinearLayout(requireContext())
        linearLayout.orientation = LinearLayout.VERTICAL

        val usernameInput = EditText(requireContext())
        usernameInput.hint = "Nom d'usuari"
        val passwordInput = EditText(requireContext())
        passwordInput.hint = "Contrasenya"
        passwordInput.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD

        linearLayout.addView(usernameInput)
        linearLayout.addView(passwordInput)

        scrollView.addView(linearLayout)

        a.setView(scrollView)

        a.setPositiveButton("OK") { dialog, _ ->
            isDialogVisible = false
            val enteredUsername = usernameInput.text.toString()
            val enteredPassword = passwordInput.text.toString()

            if (enteredUsername == "admin" && enteredPassword == "admin") {
                val newUser = User(
                    id = (AppSingleton.getInstance().loadUsersFromCsv(requireContext()).size + 1),
                    nom = username,
                    contrasenya = password,
                    tipus = TipusUsuari.TREBALLADOR
                )

                val users = AppSingleton.getInstance().loadUsersFromCsv(requireContext()).toMutableList()
                users.add(newUser)
                AppSingleton.getInstance().saveUsersToCsv(requireContext(), users)

                AppSingleton.getInstance().setUser(newUser)

                Toast.makeText(requireContext(), "Usuari treballador registrat correctament!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Credencials incorrectes. No es pot registrar com a treballador.", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        a.setNegativeButton("Cancel·lar") { dialog, _ ->
            isDialogVisible = false
            dialog.dismiss()
        }

        a.setOnDismissListener {
            isDialogVisible = false
        }

        a.create().show()
    }
}
