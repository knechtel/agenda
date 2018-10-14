package br.com.delta.cadastrodeos.converter;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;
import br.com.delta.cadastrodeos.br.com.delta.model.Cliente;

/**
 * Created by renan on 15/01/16.
 */
public class ClienteConverter {
    public String converteParaJSON(List<Cliente> alunos) {
        JSONStringer js = new JSONStringer();

        try {
            js.object().key("list").array().object().key("aluno").array();
            for (Cliente aluno : alunos) {
                js.object();
                js.key("nome").value(aluno.getNome());
                js.key("endereco").value(aluno.getEndereco());
                js.key("telefone").value(aluno.getTelefone());
                js.key("email").value(aluno.getEmail());
                js.key("marca").value(aluno.getMarca());
                js.key("modelo").value(aluno.getTelefone());
                js.key("valor").value(aluno.getValor());

                js.endObject();
            }
            js.endArray().endObject().endArray().endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return js.toString();
    }

    public String converteParaJSONCompleto(Cliente aluno) {
        JSONStringer js = new JSONStringer();

        try {
            js.object()
                    .key("nome").value(aluno.getNome())
                    .key("endereco").value(aluno.getEndereco())
                    .key("telefone").value(aluno.getTelefone())
                    .key("email").value(aluno.getEmail())
                    .key("marca").value(aluno.getMarca())
                    .key("modelo").value(aluno.getModelo())
                    .key("valor").value(aluno.getValor())
                    .endObject();
            return js.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
