package br.com.teste.seguradora.codecs;

import java.util.Date;

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

import br.com.teste.seguradora.models.Apolice;

public class ApoliceCodec implements CollectibleCodec<Apolice> {
	
	private Codec<Document> codec;
	
	public ApoliceCodec(Codec<Document> codec) {
		this.codec = codec;
	}

	@Override
	public void encode(BsonWriter writer, Apolice apolice, EncoderContext encoder) {
		ObjectId id = apolice.getId();
		int nApolice = apolice.getnApolice();
		Date iniVig = apolice.getIniVig();
		Date fimVig = apolice.getFimVig();
		String placa = apolice.getPlaca();
		int valor = apolice.getValor();
		String status = apolice.getStatus();
					
		Document document = new Document();
		
		document.put("_id", id);
		document.put("nApolice", nApolice);
		document.put("iniVig", iniVig);
		document.put("fimVig", fimVig);
		document.put("placa", placa);
		document.put("valor", valor);
		document.put("status", status);
	
		codec.encode(writer, document, encoder);
		
	}

	@Override
	public Class<Apolice> getEncoderClass() {
		return Apolice.class;
	}

	@Override
	public Apolice decode(BsonReader reader, DecoderContext decoder) {
		Document document = codec.decode(reader, decoder);
		
		Apolice apolice = new Apolice();
		
		apolice.setId(document.getObjectId("_id"));
		apolice.setnApolice(document.getInteger("nApolice"));
		apolice.setIniVig(document.getDate("iniVig"));
		apolice.setFimVig(document.getDate("fimVig"));
		apolice.setPlaca(document.getString("placa"));
		apolice.setValor(document.getInteger("valor"));
		apolice.setStatus(document.getString("status"));
						
		return apolice;
	}

	@Override
	public boolean documentHasId(Apolice apolice) {
		return apolice.getId() == null;
	}

	@Override
	public Apolice generateIdIfAbsentFromDocument(Apolice apolice) {
		return documentHasId(apolice) ? apolice.criarId() : apolice;
	}

	@Override
	public BsonValue getDocumentId(Apolice apolice) {
		if (!documentHasId(apolice)) {
			throw new IllegalStateException("Esse Document nao tem id");
		}
		
		return new BsonString(apolice.getId().toHexString());
	}

}
