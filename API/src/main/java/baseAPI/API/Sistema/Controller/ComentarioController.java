package baseAPI.API.Sistema.Controller;

import baseAPI.API.Sistema.DTO.ComentarioDTO;
import baseAPI.API.Sistema.Model.Comentario;
import baseAPI.API.Sistema.Service.ComentarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentario")
@RequiredArgsConstructor
@Tag(name = "/api/comentario", description = "Manipula dados relacionados a comentario")
public class ComentarioController {

    @Autowired
    ComentarioService comentarioService;

    @Operation(summary = "Busca Registros da tabela", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algo errado"),
    })
    @GetMapping()
    public ResponseEntity<List<Comentario>> listar()
    {return comentarioService.listar();}

    @Operation(summary = "Busca Registro por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algo errado"),
    })
    @GetMapping("/buscarComentarioPorId")
    public ResponseEntity<Comentario> buscarComentarioPorId(@RequestParam Long id)
    {return comentarioService.buscarPorId(id);}

    @Operation(summary = "Salva novo Registro", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro salvo com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algo errado"),
    })
    @PostMapping()
    public ResponseEntity<ComentarioDTO> novoComentario(@RequestParam Long idPacote,ComentarioDTO comentarioDTO)
    {return comentarioService.novoComentario(idPacote, comentarioDTO);}

    @Operation(summary = "Altera Registro", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro editado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algo errado"),
    })
    @PutMapping()
    public ResponseEntity<ComentarioDTO> editarComentario(@RequestParam Long idComentario, @RequestParam String texto)
    {return comentarioService.editarComentario(idComentario, texto);}

    @Operation(summary = "Deleta Registro por id", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro deletado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algo errado"),
    })
    @DeleteMapping()
    public ResponseEntity<ComentarioDTO> deletarcomnentario(@RequestParam Long id)
    {return comentarioService.deletar(id);}
}
