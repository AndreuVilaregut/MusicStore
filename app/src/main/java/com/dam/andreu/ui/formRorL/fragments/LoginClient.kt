package com.dam.andreu.ui.formRorL.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dam.andreu.R
import com.dam.andreu.entitats.TipusUsuari
import com.dam.andreu.entitats.User
import com.dam.andreu.singleton.AppSingleton
import com.dam.andreu.ui.RecycleView

class LoginClient : Fragment() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    private var userType: TipusUsuari = TipusUsuari.CLIENT

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_login_client, container, false)

        usernameEditText = rootView.findViewById(R.id.usernameEditText)
        passwordEditText = rootView.findViewById(R.id.passwordEditText)
        loginButton = rootView.findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            val user = verifyLogin(username, password)
            if (user != null) {
                AppSingleton.getInstance().setUser(user)
                Toast.makeText(
                    requireContext(),
                    "Login correcte! Usuari desat al Singleton: ${user.nom}",
                    Toast.LENGTH_SHORT
                ).show()

                val intent = Intent(requireContext(), RecycleView::class.java)
                startActivity(intent)
                activity?.finish()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Nom d'usuari o contrasenya incorrectes",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return rootView
    }

    private fun verifyLogin(username: String, password: String): User? {
        val users = AppSingleton.getInstance().loadUsersFromCsv(requireContext())

        val filteredUsers = users.filter { user ->
            when (userType) {
                TipusUsuari.CLIENT -> user.tipus == TipusUsuari.CLIENT
                TipusUsuari.TREBALLADOR -> user.tipus == TipusUsuari.TREBALLADOR
            }
        }

        return filteredUsers.find { it.nom == username && it.contrasenya == password }
    }

    fun setUserType(type: TipusUsuari) {
        userType = type
    }
}
