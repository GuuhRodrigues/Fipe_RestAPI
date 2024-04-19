package com.example.RestAPI.service;
import com.example.RestAPI.model.FipeEntity;
import com.example.RestAPI.repository.FipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Service
public class FipeService {

    @Autowired
    private FipeRepository fipeRepository;

    public String consultarURL(String apiUrl){
        String dados = "";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            dados = responseEntity.getBody();
        } else {
            dados = "Falha ao obter dados. Código de status: " + responseEntity.getStatusCode();
        }
        inserirDados(dados);
        return dados;
    }
    public String consultarMarcas() {
        return consultarURL("https://parallelum.com.br/fipe/api/v1/carros/marcas");
    }
    public String consultarModelos(int id) {
        return consultarURL("https://parallelum.com.br/fipe/api/v1/carros/marcas/"+id+"/modelos");
    }
    public String consultarAnos(int marca, int modelo) {
        return consultarURL("https://parallelum.com.br/fipe/api/v1/carros/marcas/"+marca+"/modelos/"+modelo+"/anos");
    }
    public String consultarValor(int marca, int modelo, String ano) {
        return consultarURL("https://parallelum.com.br/fipe/api/v1/carros/marcas/"+marca+"/modelos/"+modelo+"/anos/"+ano);
    }
    public void inserirDados(String dados) {
        try {
            JSONArray jsonArray = new JSONArray(dados);
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    String codigo = jsonObj.getString("codigo");
                    String nome = jsonObj.getString("nome");
                    FipeEntity fipe = new FipeEntity();
                    fipe.setId(codigo);
                    fipe.setModelo(nome);
                    fipeRepository.save(fipe);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
