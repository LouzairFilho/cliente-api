package br.com.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cliente.entidade.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
