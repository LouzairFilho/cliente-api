package br.com.cliente.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cliente.entidade.Cliente;
import br.com.cliente.entidade.HistoricoRequest;
import br.com.cliente.repository.HistoricoRequestRepository;

@Service
public class HistoricoRequestService {

	@Autowired
	private HistoricoRequestRepository repository;

	@Autowired
	private IpVigilanteService ipVigilanteService;

	@Autowired
	private MetaWeatherService metaWeatherService;

	public HistoricoRequest salvePorCliente(Cliente cliente, String ipOrigem) {
		HistoricoRequest historicoRequest = new HistoricoRequest();
		ipVigilanteService.setGeoLocalizacao(ipOrigem, historicoRequest);
		metaWeatherService.setTemperaturas(historicoRequest);

		historicoRequest.setCliente(cliente);
		historicoRequest.setDataCriacao(new Date());
		historicoRequest.setIpOrigem(ipOrigem);

		historicoRequest = repository.save(historicoRequest);

		return historicoRequest;
	}

	public HistoricoRequest buscarPorCliente(Integer id) throws Exception {
		HistoricoRequest historicoRequest = repository.findByCliente(new Cliente(id));

		if (historicoRequest == null) {
			throw new Exception("Nenhum registro foi encontrado com ID informado!");
		}

		return historicoRequest;
	}

}
