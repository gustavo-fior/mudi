package br.com.alura.mvc.mudi.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.alura.mvc.mudi.model.Pedido;
import br.com.alura.mvc.mudi.model.StatusPedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

	// Isso torna essa consulta "cacheavel", para não precisarmos ficar consultando o banco
	@Cacheable("home")
	List<Pedido> findByStatusOrderByIdDesc(StatusPedido status, Pageable pageable);

	@Query("SELECT p FROM Pedido p JOIN p.user WHERE p.user.username = :username")
	List<Pedido> findByUsuario(String username);

	@Query("SELECT p FROM Pedido p JOIN p.user WHERE p.status = :status AND p.user.username = :username")
	List<Pedido> findByStatusEUsuario(StatusPedido status, String username);
	
}
