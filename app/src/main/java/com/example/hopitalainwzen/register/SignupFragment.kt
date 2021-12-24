package com.example.hopitalainwzen.register



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.hopitalainwzen.R

import com.example.hopitalainwzen.Login.Login.login.LoginViewModel
import com.example.hopitalainwzen.Login.Login.login.afterTextChanged


class SignupFragment : DialogFragment() {

    companion object {
        fun newInstance() = SignupFragment()
    }

    private val viewModel : LoginViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.signup_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val displayName = view.findViewById<EditText>(R.id.displayname)
        val username = view.findViewById<EditText>(R.id.username)
        val password = view.findViewById<EditText>(R.id.password)

        val signup = view.findViewById<Button>(R.id.signup)
        val loading = view.findViewById<ProgressBar>(R.id.loading)
        viewModel.loginFormState.observe(this@SignupFragment, Observer {
            val registerState = it ?: return@Observer

            // disable signup button unless  username / password / display name is valid
            signup.isEnabled = registerState.isDataValid

            if (registerState.usernameError != null) {
                username.error = getString(registerState.usernameError)
            }
            if (registerState.passwordError != null) {
                password.error = getString(registerState.passwordError)

            }


        })

        username.afterTextChanged {
            viewModel.registerDataChanged(
                    username.text.toString(),
                    password.text.toString(),
                    displayName.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                viewModel.loginDataChanged(
                        username.text.toString(),
                        password.text.toString(),
                        displayName.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        viewModel.signup(
                                username.text.toString(),
                                password.text.toString(),
                                displayName.text.toString()
                        )
                }
                false
            }

            signup.setOnClickListener {

                loading.visibility = View.VISIBLE
                viewModel.signup(username.text.toString(), password.text.toString(), displayName.text.toString())
//val intent = Intent(this,HistoryActivity::class.java)

            }
        }
    }

    override fun onStart() {

        super.onStart()
        dialog?.let {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            it.window?.setLayout(width, height)
        }
    }
}

