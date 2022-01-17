package com.example.naviagionviewappb.servicio;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    public static final String USUARIOS_BASE_URL = "https://my-json-server.typicode.com/Jalejandro2000/serviciosjsonf/";

    private static Retrofit usuarioRetrofit;



    private static Retrofit CreateRetrofit(String baseUrl) {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    public static <TService> TService getService(Class<TService> service) throws Exception {
        if (service == UsuarioService.class) {
            if (usuarioRetrofit == null) {
                usuarioRetrofit = CreateRetrofit(USUARIOS_BASE_URL);
            }
            return usuarioRetrofit.create(service);
        }

        throw new Exception("Not implemented:" + service.getCanonicalName());
    }


}