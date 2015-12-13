package br.com.edu.ifg.formosa.certificado.modelo;

import java.sql.Time;

public class AtividadeParticipante {
	
	private String nomeAtivid;
	private Time cargaAtivid;
	
	public String getNomeAtivid() {
		return nomeAtivid;
	}
	public void setNomeAtivid(String nomeAtivid) {
		this.nomeAtivid = nomeAtivid;
	}
	public Time getCargaAtivid() {
		return cargaAtivid;
	}
	public void setCargaAtivid(Time cargaAtivid) {
		this.cargaAtivid = cargaAtivid;
	}

}
