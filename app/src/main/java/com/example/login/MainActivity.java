package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtSenha;

    private Button btnEntrar;
    private Button btnGoogle;

    private TextView txtEsqueceuSenha;
    private TextView txtCadastro;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        vincularViews();

        configurarCliques();
    }

    private void vincularViews() {

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);

        btnEntrar = findViewById(R.id.btnEntrar);
        btnGoogle = findViewById(R.id.btnGoogle);

        txtEsqueceuSenha =
                findViewById(R.id.txtEsqueceuSenha);

        txtCadastro =
                findViewById(R.id.txtCadastro);
    }

    private void configurarCliques() {

        btnEntrar.setOnClickListener(v -> {

            tentarLogin();

        });

        btnGoogle.setOnClickListener(v -> {

            Toast.makeText(
                    MainActivity.this,
                    "Login com Google será configurado em breve.",
                    Toast.LENGTH_SHORT
            ).show();

        });

        txtEsqueceuSenha.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    EsqueceuSenhaActivity.class
            );

            startActivity(intent);

        });




        txtCadastro.setOnClickListener(v -> {

            Toast.makeText(
                    MainActivity.this,
                    getString(
                            R.string.msg_abrir_cadastro
                    ),
                    Toast.LENGTH_SHORT
            ).show();

        });
    }


    private void tentarLogin() {

        String email =
                edtEmail
                        .getText()
                        .toString()
                        .trim();

        String senha =
                edtSenha
                        .getText()
                        .toString()
                        .trim();

        if (TextUtils.isEmpty(email)) {

            edtEmail.setError(
                    getString(
                            R.string.erro_email_vazio
                    )
            );

            edtEmail.requestFocus();

            return;
        }


        if (!Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()) {

            edtEmail.setError(
                    getString(
                            R.string.erro_email_invalido
                    )
            );

            edtEmail.requestFocus();

            return;
        }

        if (TextUtils.isEmpty(senha)) {

            edtSenha.setError(
                    getString(
                            R.string.erro_senha_vazia
                    )
            );

            edtSenha.requestFocus();

            return;
        }



        if (senha.length() < 6) {

            edtSenha.setError(
                    getString(
                            R.string.erro_senha_curta
                    )
            );

            edtSenha.requestFocus();

            return;
        }
        Toast.makeText(
                MainActivity.this,
                getString(
                        R.string.msg_login_valido
                ),
                Toast.LENGTH_SHORT
        ).show();
    }
}