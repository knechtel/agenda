package br.com.delta.cadastrodeos.service;

import java.util.List;

import br.com.delta.cadastrodeos.br.com.delta.model.Cliente;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ClienteService {

    @POST("clientes")
    Call<Void>insere(@Body Cliente cliente
    );

    @GET("clientes")
    Call<List<Cliente>> lista();
}
