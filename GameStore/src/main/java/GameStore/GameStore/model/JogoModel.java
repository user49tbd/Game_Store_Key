package GameStore.GameStore.model;

public class JogoModel {
	private String publicadora;
	private String titulo;
	private String descricao;
	private int ano;
	private String cgen;
	private double media;
	
	public String getCgen() {
		return cgen;
	}
	public void setCgen(String cgen) {
		this.cgen = cgen;
	}
	public String getPublicadora() {
		return publicadora;
	}
	public void setPublicadora(String publicadora) {
		this.publicadora = publicadora;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public double getMedia() {
		return media;
	}
	public void setMedia(double media) {
		this.media = media;
	}
}
