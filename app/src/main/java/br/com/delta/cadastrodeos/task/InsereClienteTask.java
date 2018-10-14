package br.com.delta.cadastrodeos.task;

import android.os.AsyncTask;


import br.com.delta.cadastrodeos.web.WebClient;
import br.com.delta.cadastrodeos.br.com.delta.model.Cliente;
import br.com.delta.cadastrodeos.converter.ClienteConverter;

public class InsereClienteTask extends AsyncTask {
    private final Cliente cliente;

    public InsereClienteTask(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        String json = new ClienteConverter().converteParaJSONCompleto(cliente);
        new WebClient().insere(json);
        return null;
    }
}
