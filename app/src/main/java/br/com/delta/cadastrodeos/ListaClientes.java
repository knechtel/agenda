package br.com.delta.cadastrodeos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.delta.cadastrodeos.br.com.delta.model.Cliente;
import br.com.delta.cadastrodeos.dao.ClienteDAO;
import retrofiit.RetrofitInializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaClientes extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);
         listView = (ListView) findViewById(R.id.lista_clientes);


        ClienteDAO clienteDAO = new ClienteDAO(ListaClientes.this);
         carregaLista(clienteDAO.buscaClientes());


        Button novoCliente = (Button) findViewById (R.id.button_salvar);
        novoCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v)  {
                Intent intentVaiProFormulario = new Intent(ListaClientes.this, MainActivity.class);
                startActivity(intentVaiProFormulario);
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();


        Call<List<Cliente>> call;
        call = new RetrofitInializador().getClienteService().lista();
        call.enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                List<Cliente>clientes = response.body();
                ClienteDAO clienteDAO = new ClienteDAO(ListaClientes.this);
                clienteDAO.sincroniza(clientes);
                carregaLista(clientes);
                System.out.println(clientes+"-----------------------------------");
            }

            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                Log.e("onFailure chamado",t.getMessage());
            }
        });

    }


    void carregaLista(List<Cliente>lista){
        List<String> listClientes= new ArrayList<String>();


        for (Cliente c:lista) {
            listClientes.add(c.getNome()+ " "+c.getUuid());
        }
        listClientes.add("");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listClientes);
        listView.setAdapter(adapter);
    }
}
