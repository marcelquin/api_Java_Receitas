package baseAPI.API.ConsumoAPI.Controller;

import baseAPI.API.ConsumoAPI.ConsumoService;
import baseAPI.API.ConsumoAPI.DTO.CepResultDTO;
import baseAPI.API.ConsumoAPI.Model.Endereco;
import baseAPI.API.ConsumoAPI.Repository.EnderecoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static javax.security.auth.callback.ConfirmationCallback.OK;

@RestController
@RequestMapping("consulta-cep")
public class ConsultaCepAPI {

    @Autowired
    ConsumoService consumoService;

    @Operation(summary = "teste de consumo de api cep salvando no banco", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Salvo realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o upload de arquivo"),
    })
    @GetMapping("{cep}")
    public CepResultDTO consultaCep (@PathVariable("cep") String cep)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CepResultDTO> resp =
            restTemplate
                .getForEntity(
                    String.format("https://viacep.com.br/ws/%s/json", cep),
                    CepResultDTO.class);
        return resp.getBody();
    }

    @Operation(summary = "teste de consumo de api cep salvando no banco", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Salvo realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o upload de arquivo"),
    })
    @GetMapping("/consultaCep2/{cep}")
    public CepResultDTO consultaCep2 (@PathVariable("cep") String cep)
    { return consumoService.consultaCep(cep);}



}
