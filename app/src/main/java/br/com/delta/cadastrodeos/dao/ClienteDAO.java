package br.com.delta.cadastrodeos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.delta.cadastrodeos.br.com.delta.model.Cliente;

public class ClienteDAO extends SQLiteOpenHelper {
    public ClienteDAO(Context context) {
        super(context, "Agenda", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Cliente (uuid CHAR(36) PRIMARY KEY, nome TEXT NOT NULL," +
                " endereco TEXT, telefone TEXT, email TEXT,aparelho TEXT,marca TEXT,modelo TEXT, valor REAL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Cliente";
        db.execSQL(sql);
        onCreate(db);
        switch (newVersion) {
            case 1:

                break;
            case 3:

                db.execSQL(sql);
                onCreate(db);
                break;
        }
    }

    public Cliente insere(Cliente cliente) {
        SQLiteDatabase db = getWritableDatabase();
        if (cliente.getUuid() == null) {
            cliente.setUuid(geraUUID());
        } else {

        }
        ContentValues dados = new ContentValues();
        dados.put("uuid", cliente.getUuid());
        dados.put("nome", cliente.getNome());
        dados.put("endereco", cliente.getEndereco());
        dados.put("telefone", cliente.getTelefone());
        dados.put("email", cliente.getEmail());
        dados.put("aparelho", cliente.getAparelho());
        dados.put("marca", cliente.getMarca());
        dados.put("modelo", cliente.getModelo());
        dados.put("valor", cliente.getValor());
        db.insert("Cliente", null, dados);


        db.close();
        return cliente;
    }

    public List<Cliente> buscaClientes() {
        String sql = "SELECT * FROM Cliente;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Cliente> clientes = new ArrayList<Cliente>();
        while (c.moveToNext()) {
            Cliente cliente = new Cliente();
            cliente.setUuid(c.getString(c.getColumnIndex("uuid")));
            cliente.setNome(c.getString(c.getColumnIndex("nome")));
            cliente.setEndereco(c.getString(c.getColumnIndex("endereco")));
            cliente.setTelefone(c.getString(c.getColumnIndex("telefone")));
            cliente.setEmail(c.getString(c.getColumnIndex("email")));
            cliente.setAparelho(c.getString(c.getColumnIndex("aparelho")));
            cliente.setMarca(c.getString(c.getColumnIndex("marca")));
            cliente.setModelo(c.getString(c.getColumnIndex("modelo")));
            cliente.setValor(c.getDouble(c.getColumnIndex("valor")));
            clientes.add(cliente);

        }
        c.close();

        return clientes;

    }

    public void sincroniza(List<Cliente> clientes) {
        for (Cliente aluno : clientes) {
            if (existe(aluno)) {
                altera(aluno);
            } else {
                insere(aluno);
            }
        }
    }

    private ContentValues pegaDadosCliente(Cliente cliente) {
        ContentValues dados = new ContentValues();
     
        dados.put("uuid", cliente.getUuid());
        dados.put("nome", cliente.getNome());
        dados.put("endereco", cliente.getEndereco());
        dados.put("telefone", cliente.getTelefone());
        dados.put("email", cliente.getEmail());
        dados.put("aparelho", cliente.getAparelho());
        dados.put("marca", cliente.getMarca());
        dados.put("modelo", cliente.getModelo());
        dados.put("valor", cliente.getValor());return dados;
    }

    public void altera(Cliente cliente)  {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosCliente(cliente);

        String[] params ={cliente.getId().toString()};

        System.out.println("||||||||||||||||||||||||");
        System.out.println(cliente.getNome());
        db.update("Cliente", dados, "uuid = ?", params);
    }
    private boolean existe(Cliente cliente) {
        SQLiteDatabase db = getReadableDatabase();
        String existe = "SELECT uuid FROM cliente WHERE uuid = ? LIMIT 1";
        Cursor cursor = db.rawQuery(existe, new String[]{cliente.getUuid()});
        int quantidade = cursor.getCount();
        return quantidade > 0;
    }

    private String geraUUID() {
        return UUID.randomUUID().toString();
    }
}
