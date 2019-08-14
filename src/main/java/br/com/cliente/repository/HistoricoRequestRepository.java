package br.com.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.cliente.entidade.Cliente;
import br.com.cliente.entidade.HistoricoRequest;

public interface HistoricoRequestRepository extends JpaRepository<HistoricoRequest, Integer> {

	HistoricoRequest findByCliente(Cliente cliente);
	
	@Query("DELETE FROM HistoricoRequest WHERE cliente = :cliente")
	void deleteByCliente(@Param("cliente") Integer cliente);
}
