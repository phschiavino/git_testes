package br.com.teste.seguradora.codecs;

import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import br.com.teste.seguradora.models.Cliente;

public class ClienteCodec implements CollectibleCodec<Cliente> {
	
	private Codec<Document> codec;
	
	public ClienteCodec(Codec<Document> codec) {
		this.codec = codec;
	}

	@Override
	public void encode(BsonWriter writer, Cliente cliente, EncoderContext encoder) {
		ObjectId id = cliente.getId();
		String nome = cliente.getNome();
		Long cpf = cliente.getCpf();
		String cidade = cliente.getCidade();
		String uf = cliente.getUf();

		Document document = new Document();
		
		document.put("_id", id);
		document.put("nome", nome);
		document.put("cpf", cpf);
		document.put("cidade", cidade);
		document.put("uf", uf);
		
		codec.encode(writer, document, encoder);
		
	}

	@Override
	public Class<Cliente> getEncoderClass() {
		return Cliente.class;
	}

	@Override
	public Cliente decode(BsonReader reader, DecoderContext decoder) {
		Document document = codec.decode(reader, decoder);
		
		Cliente cliente = new Cliente();
		
		cliente.setId(document.getObjectId("_id"));
		cliente.setNome(document.getString("nome"));
		cliente.setCpf(document.getLong("cpf"));
		cliente.setCidade(document.getString("cidade"));
		cliente.setUf(document.getString("uf"));
						
		return cliente;
	}

	@Override
	public boolean documentHasId(Cliente cliente) {
		return cliente.getId() == null;
	}

	@Override
	public Cliente generateIdIfAbsentFromDocument(Cliente cliente) {
		return documentHasId(cliente) ? cliente.criarId() : cliente;
	}

	@Override
	public BsonValue getDocumentId(Cliente cliente) {
		if (!documentHasId(cliente)) {
			throw new IllegalStateException("Esse Document nao tem id");
		}
		
		return new BsonString(cliente.getId().toHexString());
	}

}
