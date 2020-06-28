package br.com.alura.forum.controller.dto;

import br.com.alura.forum.model.Topico;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DetalhesTopicoDto {

    private Long id;
    private  String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private String nomeAutor;
    private String status;
    private List<RespostaDto> respostaDtos;

    public DetalhesTopicoDto(Topico topico){
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
        this.nomeAutor = topico.getAutor().getNome();
        this.status = topico.getStatus().name();
        this.respostaDtos = topico.getRespostas().stream().map(RespostaDto::new).collect(Collectors.toList());
    }

    public static List<DetalhesTopicoDto> converter(List<Topico> topicoList){
        return topicoList.stream().map(DetalhesTopicoDto::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RespostaDto> getRespostaDtos() {
        return respostaDtos;
    }

    public void setRespostaDtos(List<RespostaDto> respostaDtos) {
        this.respostaDtos = respostaDtos;
    }
}
