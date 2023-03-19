package com.minsait.livraria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minsait.livraria.entity.Livro;
import com.minsait.livraria.repository.LivroRepository;

@Service
public class LivroService {
	
	private LivroRepository livroRepository;
	
	@Autowired
	public LivroService(LivroRepository livroRepository) {		
		this.livroRepository = livroRepository;
	}

	public Livro cadastrarLivro(Livro livro) {
		return this.livroRepository.save(livro);				
	}
	
	public Optional<Livro> localizarLivro(Long id) {
        return livroRepository.findById(id);
    }
	
	public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }
	
	public void deletarLivro(Long id){
		livroRepository.deleteById(id);;
	}
	
	public Livro atualizarLivro(Livro livro, Long id) throws Exception {
		Optional<Livro> optionalLivro = livroRepository.findById(id);
        
        if (optionalLivro.isPresent()) {
            Livro livroAtualizado = optionalLivro.get();
            
            livroAtualizado.setTitulo(livro.getTitulo());
            livroAtualizado.setAno(livro.getAno());
            
            return livroRepository.save(livroAtualizado);
        } else {
        	throw new Exception();
        }
	}
}
