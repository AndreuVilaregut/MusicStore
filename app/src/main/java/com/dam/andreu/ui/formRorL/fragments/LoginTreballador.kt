package com.dam.andreu.ui.formRorL.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.dam.andreu.R
import com.dam.andreu.singleton.AppSingleton
import com.dam.andreu.ui.treballador.RecycleView1

class LoginTreballador : Fragment() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private var workerId: Int? = null
    private var isDialogVisible: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_login_treballador, container, false)

        usernameEditText = rootView.findViewById(R.id.usernameEditText)
        passwordEditText = rootView.findViewById(R.id.passwordEditText)
        loginButton = rootView.findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (workerId != null && verifyLogin(workerId!!, username, password)) {
                Toast.makeText(requireContext(), "Login correcte!", Toast.LENGTH_SHORT).show()

                val intent = Intent(requireContext(), RecycleView1::class.java)
                startActivity(intent)
                requireActivity().finish()
            } else {
                Toast.makeText(requireContext(), "ID, nom d'usuari o contrasenya incorrectes", Toast.LENGTH_SHORT).show()
            }
        }
        return rootView
    }

    override fun onResume() {
        super.onResume()
        workerId = null
        if (!isDialogVisible) {
            showWorkerIdDialog()
        }
    }

    private fun showWorkerIdDialog() {
        isDialogVisible = true

        val b = AlertDialog.Builder(requireContext())
        b.setTitle("Verificar treballador")
        b.setMessage("Introdueix la teva ID per accedir.")

        val idInput = EditText(requireContext())
        idInput.hint = "ID de treballador"
        idInput.inputType = android.text.InputType.TYPE_CLASS_NUMBER

        b.setView(idInput)

        b.setPositiveButton("Verificar") { dialog, _ ->
            isDialogVisible = false
            val enteredId = idInput.text.toString()

            if (verifyWorkerId(enteredId)) {
                workerId = enteredId.toInt()
                Toast.makeText(requireContext(), "Identitat verificada!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "ID no vàlida", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.viewPager, LoginClient())
                    .addToBackStack(null)
                    .commit()
            }
            dialog.dismiss()
        }

        b.setNegativeButton("Cancel·lar") { dialog, _ ->
            isDialogVisible = false
            dialog.dismiss()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.viewPager, LoginClient())
                .addToBackStack(null)
                .commit()
        }

        b.setOnDismissListener {
            isDialogVisible = false
        }

        b.create().show()
    }

    private fun verifyWorkerId(id: String): Boolean {
        val users = AppSingleton.getInstance().loadUsersFromCsv(requireContext())
        return users.any { it.id.toString() == id }
    }

    private fun verifyLogin(workerId: Int, username: String, password: String): Boolean {
        val users = AppSingleton.getInstance().loadUsersFromCsv(requireContext())

        val user = users.find {
            it.id == workerId && it.nom == username && it.contrasenya == password
        }

        return user != null
    }
}

