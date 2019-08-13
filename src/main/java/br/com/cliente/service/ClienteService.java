package br.com.cliente.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cliente.entidade.Cliente;
import br.com.cliente.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente	salvar(Cliente cliente) {
			
		cliente = clienteRepository.save(cliente);
		return cliente;
	}
}
