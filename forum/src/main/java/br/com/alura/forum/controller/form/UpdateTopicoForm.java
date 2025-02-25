package br.com.alura.forum.controller.form;

import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepositroy;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UpdateTopicoForm {

    @NotNull
    @NotEmpty
    @Length(min = 5)
    private  String titulo;
    @NotNull
    @NotEmpty
    @Length(min = 10)
    private String mensagem;


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


    public Topico update(TopicoRepositroy topicoRepositroy, Topico topico) {
        topico.setTitulo(this.titulo);
        topico.setMensagem(this.mensagem);
        return topicoRepositroy.save(topico);
    }
}
