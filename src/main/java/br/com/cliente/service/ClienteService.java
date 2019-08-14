package br.com.cliente.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cliente.entidade.Cliente;
import br.com.cliente.entidade.HistoricoRequest;
import br.com.cliente.repository.ClienteRepository;
import br.com.cliente.repository.HistoricoRequestRepository;

@Service
public class ClienteService implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4804622589117752884L;
	
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private HistoricoRequestRepository historicoRequestRepository;
	
	public Cliente	salvar(Cliente cliente) throws Exception{
		validarDadosObrigatorios(cliente);
		cliente = repository.save(cliente);
		return cliente;
	}
	
	public Cliente buscarPorId(Integer id) throws Exception {
		Cliente cliente = repository.buscarPorId(id);
		
		if (cliente == null) {
			throw new Exception("Nenhum registro foi encontrado com ID informado!");
		}
		
		return cliente;
	}
	 
	private void validarDadosObrigatorios(Cliente cliente) throws Exception {
		
		if (cliente.getIdade() == null) {
			throw new Exception("O attibuto Idade é obrigatório!");
		}
		if (cliente.getNome().equals("") ||cliente.getNome().equals(null)) {
			throw new Exception("O attibuto nome é obrigatório!");
		}
	}

	public List<Cliente> buscarTodos() {
		
		return (List<Cliente>) repository.findAll(new Sort(Direction.ASC, "id"));
	}
	
	public Page<Cliente> buscarTodos(Integer pagina, Integer tamanho) {
		Pageable paginacao = new PageRequest(pagina, tamanho, new Sort(Sort.Direction.DESC, "id"));
		return repository.findAll(paginacao);

	}
	
	@Transactional
	public void excluirPorId(Integer id) throws Exception {
		if(repository.exists(id)) {
			HistoricoRequest h = historicoRequestRepository.findByCliente(new Cliente(id));
			if(h != null) {
				
				historicoRequestRepository.delete(h);;
			}
			repository.delete(new Cliente(id));
		}else {
			throw new Exception("O id informado não existe na base de dados.");
		}
	}
}
