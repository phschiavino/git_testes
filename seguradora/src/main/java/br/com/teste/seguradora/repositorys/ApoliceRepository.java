package br.com.teste.seguradora.repositorys;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import br.com.teste.seguradora.codecs.ApoliceCodec;
import br.com.teste.seguradora.models.Apolice;

@Repository
public class ApoliceRepository {

	private MongoClient mongClient;
	private MongoDatabase bancaDeDados;

	private void criarConexao() {
		Codec<Document> codec = MongoClient.getDefaultCodecRegistry().get(Document.class);

		ApoliceCodec apoliceCodec = new ApoliceCodec(codec);

		CodecRegistry registro = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
				CodecRegistries.fromCodecs(apoliceCodec));

		MongoClientOptions opcoes = MongoClientOptions.builder().codecRegistry(registro).build();

		this.mongClient = new MongoClient("localhost:27017", opcoes);
		this.bancaDeDados = mongClient.getDatabase("seguradora");

	}

	public void salvar(Apolice apolice) {
		criarConexao();
		MongoCollection<Apolice> apolices = this.bancaDeDados.getCollection("apolices", Apolice.class);
		if (apolice.getId() == null) {
			apolices.insertOne(apolice);
		} else {
			apolices.updateOne(Filters.eq("_id", apolice.getId()), new Document("$set", apolice));
		}

		fecharConexao();

	}

	public List<Apolice> obterTodosApolice() {
		criarConexao();
		MongoCollection<Apolice> apolices = this.bancaDeDados.getCollection("apolices", Apolice.class);

		MongoCursor<Apolice> resultados = apolices.find().iterator();

		List<Apolice> apolicesEncontrados = popularApolices(resultados);
		fecharConexao();

		return apolicesEncontrados;
	}

	public Apolice obterApolicePor(String id) {
		criarConexao();
		MongoCollection<Apolice> apolices = this.bancaDeDados.getCollection("apolices", Apolice.class);
		Apolice apolice = apolices.find(Filters.eq("_id", new ObjectId(id))).first();

		return apolice;
	}

	public List<Apolice> pesquisarPor(int nApolice) {
		criarConexao();
		MongoCollection<Apolice> apoliceCollection = this.bancaDeDados.getCollection("apolices", Apolice.class);
		MongoCursor<Apolice> resultados = apoliceCollection.find(Filters.eq("nApolice", nApolice), Apolice.class)
				.iterator();
		List<Apolice> apolices = popularApolices(resultados);

		fecharConexao();

		return apolices;
	}

	private void fecharConexao() {
		this.mongClient.close();
	}

	private List<Apolice> popularApolices(MongoCursor<Apolice> resultados) {
		List<Apolice> apolices = new ArrayList<>();
		while (resultados.hasNext()) {
			apolices.add(resultados.next());
		}
		return apolices;
	}

	public void excluir(Apolice apolice) {
		criarConexao();
		MongoCollection<Apolice> apolices = this.bancaDeDados.getCollection("apolices", Apolice.class);

			apolices.deleteOne(Filters.eq(apolice.getId()));
			
			fecharConexao();
	}

}
