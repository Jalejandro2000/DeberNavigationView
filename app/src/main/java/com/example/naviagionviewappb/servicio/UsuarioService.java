package com.example.naviagionviewappb.servicio;

import com.example.naviagionviewappb.modelo.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UsuarioService {
    @GET("Usuarios")
    Call<List<Usuario>> getUsuarios();

}
