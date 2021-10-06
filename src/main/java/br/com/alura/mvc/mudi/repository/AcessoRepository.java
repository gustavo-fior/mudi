package br.com.alura.mvc.mudi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.mvc.mudi.model.Acesso;

// Classe de repositório e comunicação com o banco para acessos
@Repository
public interface AcessoRepository extends JpaRepository<Acesso, Long>{

	
	
}
