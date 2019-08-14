package br.com.cliente.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.cliente.entidade.HistoricoRequest;


@Service
public class MetaWeatherService {
	
	private static final String URI_META_WEATHER = "https://www.metaweather.com/api/location/";

	private RestTemplate restTemplate = new RestTemplate();
	
	public HistoricoRequest setTemperaturas(HistoricoRequest hr) {
		
		// consulta api metaweather e obtem atraves da latitude e longetude o " woeid " necessário para proxima consulta 
		String response = restTemplate.getForObject(URI_META_WEATHER+"search/?lattlong="+ hr.getLatitude().toString()+","+hr.getLongitude().toString(), String.class);
		
		JSONArray jReposnse = new JSONArray(response);
		Integer woeid = new JSONArray(response).getJSONObject(0).getInt("woeid");
		
		// consulta api metaweather e obtem atraves do " woeid " a temperatura minima e maxima
		response = restTemplate.getForObject(URI_META_WEATHER+woeid.toString(), String.class);
		
		JSONObject jReposnse1 = new JSONObject(response);
		JSONArray jArray = jReposnse1.getJSONArray("consolidated_weather");
		JSONObject jObject = jArray.getJSONObject(0);
		
		hr.setTempMax(jObject.getDouble("max_temp"));
		hr.setTempMin(jObject.getDouble("min_temp"));
		
		return hr;
	}
}
