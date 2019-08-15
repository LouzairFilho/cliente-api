package br.com.cliente.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cliente.entidade.Cliente;
import br.com.cliente.entidade.HistoricoRequest;
import br.com.cliente.service.ClienteService;
import br.com.cliente.service.HistoricoRequestService;
import br.com.cliente.util.RetornoApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/cliente-api/v1/clientes")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cliente-api/v1/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	HistoricoRequestService historicoRequestService;

	@ApiOperation(value = "Realiza o cadastro de um novo Cliente",notes = "Realiza o cadastro de um novo ClienteS")
	@PostMapping(value = "/novo")
	public ResponseEntity<RetornoApi> novo(HttpServletRequest request, @RequestBody Cliente cliente) {
		String excecao = "Erro ao processar requisição: ";
		try {
			String ipOrigem = request.getHeader("X-FORWARDED-FOR");
			if (ipOrigem == null) {
				ipOrigem = request.getRemoteAddr();
			}
			
			cliente = clienteService.salvar(cliente);
			historicoRequestService.salvePorCliente(cliente, ipOrigem);
			
			return new ResponseEntity<RetornoApi>(new RetornoApi(cliente, 200, "Cliente salvo com sucesso"), HttpStatus.OK);
		} catch (Exception e) {
			excecao = excecao + e.getMessage();
			return new ResponseEntity<RetornoApi>(new RetornoApi(null, HttpStatus.BAD_REQUEST.value(), excecao), HttpStatus.BAD_REQUEST);
		}

	}
	
	@PutMapping(value = "/atualiza")
	public ResponseEntity<RetornoApi> atualizar(@RequestBody Cliente cliente) {
		String excecao = "Erro ao processar requisição: ";
		try {
			
			cliente = clienteService.salvar(cliente);
			
			return new ResponseEntity<>(new RetornoApi(cliente, 200, "Cliente atulizado com sucesso"), HttpStatus.OK);
		} catch (Exception e) {
			excecao = excecao + e.getMessage();
			return new ResponseEntity<RetornoApi>(new RetornoApi(null, HttpStatus.BAD_REQUEST.value(), excecao), HttpStatus.BAD_REQUEST);
		}

	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<RetornoApi> buscarPorId(@PathVariable("id") Integer id) {
		try {
			
			Cliente cliente = clienteService.buscarPorId(id);
			
			return new ResponseEntity<RetornoApi>(new RetornoApi(cliente, 200, "Solicitação realizada com sucesso"), HttpStatus.OK);
		} catch (Exception e) {
			String excecao = "Erro ao processar requisição: " + e.getMessage();
			return new ResponseEntity<RetornoApi>(new RetornoApi(null, HttpStatus.BAD_REQUEST.value(), excecao), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping(value = "/{id}/historico")
	public ResponseEntity<RetornoApi> buscarPorHistorico(@PathVariable("id") Integer id) {
		try {
			
			HistoricoRequest historicoRequest= historicoRequestService.buscarPorCliente(id);
			
			return new ResponseEntity<RetornoApi>(new RetornoApi(historicoRequest, 200, "Solicitação realizada com sucesso"), HttpStatus.OK);
		} catch (Exception e) {
			String excecao = "Erro ao processar requisição: " + e.getMessage();
			return new ResponseEntity<RetornoApi>(new RetornoApi(null, HttpStatus.BAD_REQUEST.value(), excecao), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping()
	public ResponseEntity<RetornoApi> buscarTodos() {
		try {
			
			List<Cliente> clientes= clienteService.buscarTodos();
			
			return new ResponseEntity<RetornoApi>(new RetornoApi(clientes, 200, "Solicitação realizada com sucesso"), HttpStatus.OK);
		} catch (Exception e) {
			String excecao = "Erro ao processar requisição: " + e.getMessage();
			return new ResponseEntity<RetornoApi>(new RetornoApi(null, HttpStatus.BAD_REQUEST.value(), excecao), HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/paginado")
	public ResponseEntity<RetornoApi> buscarTodos(Integer pagina, Integer tamanho) {
		try {
			
			Page<Cliente> clientes= clienteService.buscarTodos(pagina, tamanho);
			
			return new ResponseEntity<RetornoApi>(new RetornoApi(clientes, 200, "Solicitação realizada com sucesso"), HttpStatus.OK);
		} catch (Exception e) {
			String excecao = "Erro ao processar requisição: " + e.getMessage();
			return new ResponseEntity<RetornoApi>(new RetornoApi(null, HttpStatus.BAD_REQUEST.value(), excecao), HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/excluirPorId/{id}")
	public ResponseEntity<RetornoApi> excluirPorId(@PathVariable("id") Integer id){
		try {
			
			clienteService.excluirPorId(id);;
			
			return new ResponseEntity<RetornoApi>(new RetornoApi(null, 200, "Cliente excluido com sucesso"), HttpStatus.OK);
		} catch (Exception e) {
			String excecao = "Erro ao processar requisição: " + e.getMessage();
			return new ResponseEntity<RetornoApi>(new RetornoApi(null, HttpStatus.BAD_REQUEST.value(), excecao), HttpStatus.BAD_REQUEST);
		}
	}
	
}
