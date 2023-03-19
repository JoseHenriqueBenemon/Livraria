package com.minsait.livraria.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.minsait.livraria.entity.Livro;
import com.minsait.livraria.entity.Mensagem;
import com.minsait.livraria.service.LivroService;

@RestController
@RequestMapping("/api/v1/livraria/livros")
public class LivroController {
	@Autowired
	private LivroService livroService;
	
	@Autowired
	public LivroController(LivroService livroService) {		
		this.livroService = livroService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Livro cadastraLivro(@Valid @RequestBody Livro livro) {
		return this.livroService.cadastrarLivro(livro);
		
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<?> localizaLivro(@PathVariable Long id) {
        Optional<Livro> livroOptional = livroService.localizarLivro(id);
        if (livroOptional.isPresent()) {
            return ResponseEntity.ok(livroOptional.get());
        } else {
        	Mensagem mensagem = new Mensagem("O livro requisitado não foi localizado!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagem);
        }
    }
	
	@GetMapping
    public List<Livro> listaLivros() {
        return livroService.listarLivros();
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Mensagem> deletaLivro(@PathVariable Long id) {
		livroService.deletarLivro(id);
		
		Mensagem mensagem = new Mensagem("O livro foi deletado com sucesso!");
        return ResponseEntity.status(HttpStatus.OK).body(mensagem);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizaLivro(@Valid @RequestBody Livro livroEntrada, @PathVariable Long id){   
        try {
            Livro livroAtualizadoSalvo = livroService.atualizarLivro(livroEntrada, id);
            return ResponseEntity.ok(livroAtualizadoSalvo);
        } catch (Exception e) {
        	Mensagem mensagem = new Mensagem("O livro não foi localizado!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagem);
        }
	}
	
	@GetMapping("/teste")
	public String teste() {
		return "Teste ok!";
	}
		
}
