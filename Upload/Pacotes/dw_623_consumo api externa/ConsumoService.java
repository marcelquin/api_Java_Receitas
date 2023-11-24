package baseAPI.API.ConsumoAPI;

import baseAPI.API.ConsumoAPI.DTO.CepResultDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Service
public class ConsumoService {
    //@PathVariable("cep") 
    public CepResultDTO consultaCep (String cep)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CepResultDTO> resp =
                restTemplate
                        .getForEntity(
                                String.format("https://viacep.com.br/ws/%s/json", cep),
                                CepResultDTO.class);
        return resp.getBody();
    }
}
