package br.com.edu.ifg.formosa.certificado.modelo;

import java.util.List;

public class CertificadoParticipante implements Comparable<CertificadoParticipante>{
	
	private String cpf;
	private String nome;
	private List<AtividadeParticipante> atividades;
	private int cargaTotal;
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<AtividadeParticipante> getAtividades() {
		return atividades;
	}
	public void setAtividades(List<AtividadeParticipante> atividades) {
		this.atividades = atividades;
	}
	public int getCargaTotal() {
		return cargaTotal;
	}
	public void setCargaTotal(int cargaTotal) {
		this.cargaTotal = cargaTotal;
	}
	@Override
	public int compareTo(CertificadoParticipante o) {
		return this.nome.compareTo(o.getNome());
	}
}
