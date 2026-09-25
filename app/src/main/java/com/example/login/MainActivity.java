package com.example.login.;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    // Views da tela
    private EditText edtEmail, edtSenha;
    private Button btnEntrar, btnGoogle;
    private TextView txtEsqueceuSenha, txtCadastro;

    // Cliente de login do Google
    private GoogleSignInClient googleSignInClient;

    // Launcher que substitui o antigo onActivityResult (forma recomendada hoje em dia)
    private final ActivityResultLauncher<Intent> googleLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                Task<GoogleSignInAccount> task =
                        GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                handleGoogleSignInResult(task);
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vincularViews();
        configurarGoogleSignIn();
        configurarCliques();
    }

    // Liga as variáveis Java com os elementos do XML (findViewById)
    private void vincularViews() {
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnGoogle = findViewById(R.id.btnGoogle);
        txtEsqueceuSenha = findViewById(R.id.txtEsqueceuSenha);
        txtCadastro = findViewById(R.id.txtCadastro);
    }

    // Configura as opções de login do Google
    // IMPORTANTE: troque "SEU_CLIENT_ID_WEB" pelo Client ID do tipo "Web application"
    // gerado no Google Cloud Console / Firebase (obrigatório para requestIdToken).
    private void configurarGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("SEU_CLIENT_ID_WEB.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void configurarCliques() {
        btnEntrar.setOnClickListener(v -> tentarLoginComEmailSenha());

        btnGoogle.setOnClickListener(v -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            googleLauncher.launch(signInIntent);
        });

        txtEsqueceuSenha.setOnClickListener(v ->
                        Toast.makeText(this, getString(R.string.msg_abrir_recuperar_senha), Toast.LENGTH_SHORT).show()
                // Aqui depois você pode trocar por:
                // startActivity(new Intent(this, EsqueceuSenhaActivity.class));
        );

        txtCadastro.setOnClickListener(v ->
                        Toast.makeText(this, getString(R.string.msg_abrir_cadastro), Toast.LENGTH_SHORT).show()
                // startActivity(new Intent(this, CadastroActivity.class));
        );
    }

    // Validação simples do formulário de e-mail e senha
    private void tentarLoginComEmailSenha() {
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            edtEmail.setError(getString(R.string.erro_email_vazio));
            edtEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError(getString(R.string.erro_email_invalido));
            edtEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(senha)) {
            edtSenha.setError(getString(R.string.erro_senha_vazia));
            edtSenha.requestFocus();
            return;
        }

        if (senha.length() < 6) {
            edtSenha.setError(getString(R.string.erro_senha_curta));
            edtSenha.requestFocus();
            return;
        }

        // Aqui entra a chamada para o seu backend (Spring Boot) para autenticar o usuário
        Toast.makeText(this, getString(R.string.msg_login_valido), Toast.LENGTH_SHORT).show();
    }

    // Trata o resultado do login com Google
    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String nome = account.getDisplayName();
            String email = account.getEmail();
            String idToken = account.getIdToken(); // envie este token para o backend validar

            Toast.makeText(this, getString(R.string.msg_bem_vindo, nome), Toast.LENGTH_SHORT).show();

            // Aqui você envia o idToken para o seu backend Spring Boot,
            // que valida o token com o Google e cria/retorna o usuário logado.

        } catch (ApiException e) {
            Toast.makeText(this, getString(R.string.msg_falha_google, e.getStatusCode()), Toast.LENGTH_SHORT).show();
        }
    }
}