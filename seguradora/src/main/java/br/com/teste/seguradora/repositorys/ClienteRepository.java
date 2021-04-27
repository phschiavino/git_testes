package br.com.teste.seguradora.repositorys;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import br.com.teste.seguradora.codecs.ClienteCodec;
import br.com.teste.seguradora.models.Cliente;

@Repository
public class ClienteRepository {

	private MongoClient mongClient;
	private MongoDatabase bancaDeDados;

	private void criarConexao() {
		Codec<Document> codec = MongoClient.getDefaultCodecRegistry().get(Document.class);

		ClienteCodec clienteCodec = new ClienteCodec(codec);

		CodecRegistry registro = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
				CodecRegistries.fromCodecs(clienteCodec));

		MongoClientOptions opcoes = MongoClientOptions.builder().codecRegistry(registro).build();

		this.mongClient = new MongoClient("localhost:27017", opcoes);
		this.bancaDeDados = mongClient.getDatabase("seguradora");

	}

	public void salvar(Cliente cliente) {

		criarConexao();
		MongoCollection<Cliente> clientes = this.bancaDeDados.getCollection("clientes", Cliente.class);
		if (cliente.getId() == null) {
			clientes.insertOne(cliente);
		} else {
			clientes.updateOne(Filters.eq("_id", cliente.getId()), new Document("$set", cliente));
		}

		fecharConexao();
	}

	public List<Cliente> obterTodosClientes() {
		criarConexao();
		MongoCollection<Cliente> clientes = this.bancaDeDados.getCollection("clientes", Cliente.class);

		MongoCursor<Cliente> resultados = clientes.find().iterator();

		List<Cliente> clientesEncontrados = popularClientes(resultados);
		fecharConexao();

		return clientesEncontrados;

	}

	private void fecharConexao() {
		this.mongClient.close();
	}

	private List<Cliente> popularClientes(MongoCursor<Cliente> resultados) {
		List<Cliente> clientes = new ArrayList<>();
		while (resultados.hasNext()) {
			clientes.add(resultados.next());
		}
		return clientes;
	}

}
