package br.com.cliente.repository;

import javax.websocket.server.PathParam;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.cliente.entidade.Cliente;

public interface ClienteRepository extends PagingAndSortingRepository<Cliente, Integer>{

	@Query("SELECT c FROM Cliente c where c.id = :id")
	Cliente buscarPorId(@Param("id") Integer id);
	
}
