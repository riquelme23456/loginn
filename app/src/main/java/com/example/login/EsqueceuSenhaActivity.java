package com.example.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EsqueceuSenhaActivity extends AppCompatActivity {

    private EditText edtEmailRecuperacao;
    private Button btnEnviarRecuperacao;
    private TextView txtVoltarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_esqueceu_senha);

        vincularViews();

        configurarCliques();
    }

    private void vincularViews() {

        edtEmailRecuperacao =
                findViewById(R.id.edtEmailRecuperacao);

        btnEnviarRecuperacao =
                findViewById(R.id.btnEnviarRecuperacao);

        txtVoltarLogin =
                findViewById(R.id.txtVoltarLogin);
    }

    private void configurarCliques() {

        // ENVIAR RECUPERAÇÃO

        btnEnviarRecuperacao.setOnClickListener(v -> {

            enviarRecuperacao();

        });


        // VOLTAR PARA LOGIN

        txtVoltarLogin.setOnClickListener(v -> {

            finish();

        });
    }

    private void enviarRecuperacao() {

        String email =
                edtEmailRecuperacao
                        .getText()
                        .toString()
                        .trim();


        // E-MAIL VAZIO

        if (TextUtils.isEmpty(email)) {

            edtEmailRecuperacao.setError(
                    "Digite seu e-mail"
            );

            edtEmailRecuperacao.requestFocus();

            return;
        }


        // E-MAIL INVÁLIDO

        if (!Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()) {

            edtEmailRecuperacao.setError(
                    "Digite um e-mail válido"
            );

            edtEmailRecuperacao.requestFocus();

            return;
        }


        /*
         * Por enquanto não existe backend.
         *
         * Futuramente aqui você fará uma chamada
         * para sua API Spring Boot.
         */

        Toast.makeText(
                this,
                "Instruções de recuperação serão enviadas para "
                        + email,
                Toast.LENGTH_LONG
        ).show();
    }
}
