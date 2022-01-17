package com.example.naviagionviewappb.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.naviagionviewappb.MainActivity;
import com.example.naviagionviewappb.R;
import com.example.naviagionviewappb.modelo.Usuario;
import com.example.naviagionviewappb.servicio.RetrofitHelper;
import com.example.naviagionviewappb.servicio.UsuarioService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {

    EditText editTextUser;
    EditText editTextPassword;
    Button buttonLogin;

    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextUser = view.findViewById(R.id.editTextUser);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        buttonLogin = view.findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    DoLogin();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void DoLogin() throws Exception {
        Call<List<Usuario>> call = RetrofitHelper.getService(UsuarioService.class).getUsuarios();
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    List<Usuario> usuarios = response.body();
                    for (Usuario user : usuarios) {
                        if(user.getUsuario().equals(editTextUser.getText().toString()) &&
                        user.getClave().equals(editTextPassword.getText().toString()))
                        {
                            ((MainActivity)getActivity()).configureUser(user);
                            return;
                        }
                    }
                    Toast.makeText(getActivity().getApplicationContext(), "Usuario o contraseña incorrecta.", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Error al realizar la petición. " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

}