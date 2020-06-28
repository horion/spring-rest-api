package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.DetalhesTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.controller.form.UpdateTopicoForm;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepositroy;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    private final TopicoRepositroy topicoRepositroy;
    private final CursoRepository cursoRepository;

    public TopicosController(TopicoRepositroy topicoRepositroy, CursoRepository cursoRepository) {
        this.topicoRepositroy = topicoRepositroy;
        this.cursoRepository = cursoRepository;
    }


    @GetMapping
    @Cacheable(value = "listaDeTopicos")
    public Page<TopicoDto> listTopicos(@RequestParam(required = false) String nomeCurso,
                                       @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Topico> topicoPage;
        if(nomeCurso == null)
            topicoPage = topicoRepositroy.findAll(pageable);
        else
            topicoPage = topicoRepositroy.findByCursoNome(nomeCurso,pageable);

        return TopicoDto.converter(topicoPage);
    }

    @PostMapping
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<TopicoDto> create(@RequestBody  @Valid TopicoForm form, UriComponentsBuilder uriComponentsBuilder){
        Topico topico =  form.converter(cursoRepository);
        topicoRepositroy.save(topico);
        URI uri =  uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesTopicoDto> detail(@PathVariable Long id){
        Optional<Topico> topico = topicoRepositroy.findById(id);
        return topico.map(value -> ResponseEntity.ok(new DetalhesTopicoDto(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<TopicoDto> update(@RequestBody @Valid UpdateTopicoForm form, @PathVariable Long id){
        Optional<Topico> topico = topicoRepositroy.findById(id);
        if (!topico.isPresent()){
            return ResponseEntity.notFound().build();
        }
        TopicoDto body = new TopicoDto(form.update(topicoRepositroy, topico.get()));
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity delete(@PathVariable Long id){
        Optional<Topico> topico = topicoRepositroy.findById(id);
        if (!topico.isPresent()){
            return ResponseEntity.notFound().build();
        }
        topicoRepositroy.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
