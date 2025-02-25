package br.com.alura.forum.repository;

import br.com.alura.forum.model.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TopicoRepositroy extends JpaRepository<Topico,Long> {

    Page<Topico> findByCursoNome(String nomeCurso, Pageable page);
}
