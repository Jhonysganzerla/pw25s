package br.edu.utfpr.pb.pw25s.aula2.repository;

import br.edu.utfpr.pb.pw25s.aula2.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	
	// Select * from Categoria where descricao like '%am%'
	List<Categoria> findByDescricaoLike(String descricao);
	
}
