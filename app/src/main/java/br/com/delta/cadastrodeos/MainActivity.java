package br.com.delta.cadastrodeos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.delta.cadastrodeos.br.com.delta.model.Cliente;
import br.com.delta.cadastrodeos.dao.ClienteDAO;
import retrofiit.RetrofitInializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button botaoSalvar = findViewById(R.id.formulario_salvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText campoNome = (EditText) findViewById(R.id.formulario_nome);
                EditText campoEndereco = (EditText) findViewById(R.id.formulario_endereco);
                EditText campoTelefone = (EditText) findViewById(R.id.formulario_telefone);
                EditText campoEmail = (EditText) findViewById(R.id.formulario_email);
                EditText campoAparelho = (EditText) findViewById(R.id.formulario_aparelho);
                EditText campoMarca = (EditText) findViewById(R.id.formulario_marca);
                EditText campoModelo = (EditText) findViewById(R.id.formulario_modelo);
                EditText campoValor = (EditText) findViewById(R.id.formulario_valor);

                Cliente cliente = new Cliente();
                cliente.setNome(campoNome.getText().toString());
                cliente.setEndereco(campoEndereco.getText().toString());
                cliente.setTelefone(campoTelefone.getText().toString());
                cliente.setEmail(campoEmail.getText().toString());
                cliente.setAparelho(campoAparelho.getText().toString());
                cliente.setMarca(campoMarca.getText().toString());
                cliente.setModelo(campoModelo.getText().toString());
                cliente.setValor(new Double(campoValor.getText().toString()));

                ClienteDAO clienteDAO = new ClienteDAO(MainActivity.this);
                clienteDAO.insere(cliente);
                System.out.println(cliente.getUuid() + "  --- id ");
                // new InsereClienteTask(cliente).execute();
                Call call = new RetrofitInializador().getClienteService().insere(cliente);

                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        Log.i("onResponse", "requisicao com sucesso");
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Log.e("onFailure", "requisicao falhou");
                    }
                });
                Toast.makeText(MainActivity.this, "Botao clicado! " + cliente.getUuid(), Toast.LENGTH_SHORT).show();
                Intent intentVaiProFormulario = new Intent(MainActivity.this, ListaClientes.class);
                startActivity(intentVaiProFormulario);
            }
        });

        Button botaoCancelar = findViewById(R.id.formulario_cancelar);
        botaoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentVaiProFormulario = new Intent(MainActivity.this, ListaClientes.class);
                startActivity(intentVaiProFormulario);
            }});


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
