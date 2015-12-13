package br.com.edu.ifg.formosa.certificado.modelo;

import java.util.List;

public class CertificadoJasper {
	
	private String nome;
	private List<AtividadeJasper> atividades;
	private String cargaTotal;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<AtividadeJasper> getAtividades() {
		return atividades;
	}
	public void setAtividades(List<AtividadeJasper> atividades) {
		this.atividades = atividades;
	}
	public String getCargaTotal() {
		return cargaTotal;
	}
	public void setCargaTotal(String cargaTotal) {
		this.cargaTotal = cargaTotal;
	}
	
}
