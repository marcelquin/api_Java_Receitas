package baseAPI.API.Sistema.Controller;

import baseAPI.API.Sistema.DTO.PacoteDTO;
import baseAPI.API.Sistema.Model.Pacote;
import baseAPI.API.Sistema.Service.PacoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/pacote")
@RequiredArgsConstructor
@Tag(name = "/api/pacote", description = "Manipula dados relacionados a pacote")
public class PacoteController {

    @Autowired
    PacoteService pacoteService;


    @Operation(summary = "Busca Registros da tabela", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algo errado"),
    })
    @GetMapping()
    public ResponseEntity<List<Pacote>> listarpacotes()
    {return pacoteService.listar();}

    @Operation(summary = "Busca Registro por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algo errado"),
    })
    @GetMapping("/buscarPacotePorId")
    public ResponseEntity<Pacote> buscarPacotePorId(@RequestParam Long id)
    {return pacoteService.buscarPorId(id);}

    @Operation(summary = "Busca e realiza o download dos arquivos em zip", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Download realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algo errado"),
    })
    @GetMapping("/downloadFiles")
    public ResponseEntity<Resource> downloadFiles(@RequestParam String codigo) throws IOException
    {return pacoteService.downloadFiles(codigo);}

    @Operation(summary = "Salva novo Registro", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro salvo com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algo errado"),
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PacoteDTO> novoPacote(PacoteDTO pacoteDTO,@RequestPart MultipartFile[] files) throws IOException
    {return pacoteService.salvar(pacoteDTO, files);}


    @Operation(summary = "Altera Registro", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro editado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algo errado"),
    })
    @PutMapping(value = "/AdicionarArquivos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PacoteDTO> AdicionarArquivos(@RequestParam Long idpacote,@RequestPart MultipartFile[] files) throws IOException
    {return pacoteService.AdicionarArquivos(idpacote, files);}

    @Operation(summary = "Altera Registro", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro editado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algo errado"),
    })
    @PutMapping(value = "/AlterarArquivos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PacoteDTO> AlterarArquivos(@RequestParam Long idpacote, @RequestPart MultipartFile[] files) throws IOException
    { return pacoteService.AlterarArquivos(idpacote, files);}

    @Operation(summary = "Deleta Registro por id", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro deletado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algo errado"),
    })
    @DeleteMapping()
    public ResponseEntity<PacoteDTO> deletarPacote(@RequestParam Long id)
    {return pacoteService.deletar(id);}


}
