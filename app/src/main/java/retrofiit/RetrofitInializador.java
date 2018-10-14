package retrofiit;


import br.com.delta.cadastrodeos.service.ClienteService;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitInializador {
    private final Retrofit retrofit;
    public RetrofitInializador() {
        retrofit = new Retrofit.Builder().baseUrl("http://10.0.0.103:8080/api/")
                .addConverterFactory(JacksonConverterFactory.create()).build();
    }

    public ClienteService getClienteService() {
        return retrofit.create(ClienteService.class);
    }
}