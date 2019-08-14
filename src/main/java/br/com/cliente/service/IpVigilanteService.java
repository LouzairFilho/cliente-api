package br.com.cliente.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.cliente.entidade.HistoricoRequest;


@Service
public class IpVigilanteService {

	private static final String URI_IP_VIGILANTE = "https://ipvigilante.com/";

	private RestTemplate restTemplate = new RestTemplate();
	
	public HistoricoRequest setGeoLocalizacao(String ip, HistoricoRequest hr) {
		
		// consulta api ipvigilante e obtem atraves do IP a " latitude e longetude "
		String response = restTemplate.getForObject(URI_IP_VIGILANTE+ip, String.class);
		
		JSONObject jReposnse = new JSONObject(response);
		JSONObject jData = jReposnse.getJSONObject("data");
		
		String lat = jData.getString("latitude");
	    String lng = jData.getString("longitude");;
	   
	    
		hr.setLatitude(Double.parseDouble(lat));
		hr.setLongitude(Double.parseDouble(lng));
		return  hr;
	}
}
