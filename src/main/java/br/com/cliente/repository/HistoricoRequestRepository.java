package br.com.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cliente.entidade.HistoricoRequest;

public interface HistoricoRequestRepository extends JpaRepository<HistoricoRequest, Integer> {

}
