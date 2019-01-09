package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "TEMP_REVISAOENDERECOTRES")
@Table(name = "TEMP_REVISAOENDERECOTRES")
public class Temp {
	
	@Column(name = "A1_COD")
	private String cod;
	
	@Column(name = "A1_LOJA")
	private String loja;
	
	@Column(name = "A1_CEP")
	private String cep;
	
	@Column(name = "A1_END")
	private String endAntigo;
	
	@Column(name = "ENDERECO_NAOABREVIADO")
	private String endNaoAbreviado;
	
	@Column(name = "NOVO_ENDERECO")
	private String endNovo;
	
	@Column(name = "NOVO_BAIRRO")
	private String bairroNovo;
	
	@Column(name = "NOVA_CIDADE")
	private String cidadeNova;
	
	@Column(name = "NUM_FINAL")
	private String numFinal;
	
	@Id
	@Column(name = "R_E_C_N_O_")
	private String recno;
	
	public String getCod() {
		return cod;
	}
	public void setCod(String cod) {
		this.cod = cod;
	}
	public String getLoja() {
		return loja;
	}
	public void setLoja(String loja) {
		this.loja = loja;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getEndAntigo() {
		return endAntigo;
	}
	public void setEndAntigo(String endAntigo) {
		this.endAntigo = endAntigo;
	}
	public String getEndNovo() {
		return endNovo;
	}
	public void setEndNovo(String endNovo) {
		this.endNovo = endNovo;
	}
	public String getEndNaoAbreviado() {
		return endNaoAbreviado;
	}
	public void setEndNaoAbreviado(String endNaoAbreviado) {
		this.endNaoAbreviado = endNaoAbreviado;
	}
	public String getBairroNovo() {
		return bairroNovo;
	}
	public void setBairroNovo(String bairroNovo) {
		this.bairroNovo = bairroNovo;
	}
	public String getCidadeNova() {
		return cidadeNova;
	}
	public void setCidadeNova(String cidadeNova) {
		this.cidadeNova = cidadeNova;
	}
	public String getNumFinal() {
		return numFinal;
	}
	public void setNumFinal(String numFinal) {
		this.numFinal = numFinal;
	}
	public String getRecno() {
		return recno;
	}
	public void setRecno(String recno) {
		this.recno = recno;
	}
	
	
}
