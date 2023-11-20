package baseAPI.API.Sistema.Controller;

import baseAPI.API.Sistema.DTO.UsuarioDTO;
import baseAPI.API.Sistema.Model.Usuario;
import baseAPI.API.Sistema.Service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
@Tag(name = "/api/usuario", description = "Manipula dados relacionados a usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Operation(summary = "Busca Registros da tabela", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o upload de arquivo"),
    })
    @GetMapping()
    public ResponseEntity<List<Usuario>> listar()
    {return usuarioService.listar();}

    @Operation(summary = "Busca Registro por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o upload de arquivo"),
    })
    @GetMapping("/buscarUsuarioPorId")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@RequestParam Long id)
    {return usuarioService.buscarPorId(id);}

    @Operation(summary = "Salva novo Registro", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro salvo com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algo errado"),
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UsuarioDTO> novoUsuario(UsuarioDTO usuarioDTO, @RequestPart MultipartFile file) throws SQLException, IOException
    {return usuarioService.salvar(usuarioDTO, file);}

    @Operation(summary = "Altera Registro", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro editado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algo errado"),
    })
    @PutMapping()
    public ResponseEntity<UsuarioDTO> editarUsuario(@RequestParam Long id, @RequestParam String nome, @RequestParam String sorenome)
    {return usuarioService.editar(id, nome, sorenome);}

    @Operation(summary = "Altera Registro", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro editado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algo errado"),
    })
    @PutMapping(value = "/alterarFoto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UsuarioDTO> alterarFoto(@RequestParam Long id, @RequestPart MultipartFile file) throws SQLException, IOException
    {return usuarioService.alterarFoto(id, file);}

    @Operation(summary = "Deleta Registro por id", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro deletado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Ops algo errado"),
    })
    @DeleteMapping()
    public ResponseEntity<UsuarioDTO> deletarUsuario(@RequestParam Long id)
    {return usuarioService.deletar(id);}


}
