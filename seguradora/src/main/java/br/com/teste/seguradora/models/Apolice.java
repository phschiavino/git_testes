package br.com.teste.seguradora.models;

import java.util.Date;
import java.util.Random;

import org.bson.types.ObjectId;

public class Apolice {

	private ObjectId id;

	Random random = new Random();
	int nApolice = random.nextInt(50) * 132;

	private Date iniVig;

	private Date fimVig;

	private String placa;

	private int valor;

	private String status;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Apolice criarId() {
		setId(new ObjectId());
		return this;
	}

	public int getNApolice() {
		return nApolice;
	}

	public int getnApolice() {
		return nApolice;
	}

	public void setnApolice(int nApolice) {
		this.nApolice = nApolice;
	}

	public Date getIniVig() {
		return iniVig;
	}

	public void setIniVig(Date iniVig) {
		this.iniVig = iniVig;
	}

	public Date getFimVig() {
		return fimVig;
	}

	public void setFimVig(Date fimVig) {
		this.fimVig = fimVig;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
