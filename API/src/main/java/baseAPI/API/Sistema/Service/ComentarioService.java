package baseAPI.API.Sistema.Service;

import baseAPI.API.Sistema.DTO.ComentarioDTO;
import baseAPI.API.Sistema.DTO.UsuarioDTO;
import baseAPI.API.Sistema.Enum.Acao;
import baseAPI.API.Sistema.Model.Backup;
import baseAPI.API.Sistema.Model.Comentario;
import baseAPI.API.Sistema.Model.Pacote;
import baseAPI.API.Sistema.Model.Usuario;
import baseAPI.API.Sistema.Repository.BackupRepository;
import baseAPI.API.Sistema.Repository.ComentarioRepository;
import baseAPI.API.Sistema.Repository.PacoteRepository;
import baseAPI.API.Sistema.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Service
public class ComentarioService {

    @Autowired
    ComentarioRepository comentarioRepository;

    @Autowired
    BackupRepository backupRepository;

    @Autowired
    PacoteRepository pacoteRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public ResponseEntity<List<Comentario>> listar()
    {
        try{
            return new ResponseEntity<>(comentarioRepository.findAll(), OK);
        }catch (Exception e)
        {
            System.out.println("Ops algo deu errado!");
            e.getStackTrace();
        }
        return null;
    }

    public ResponseEntity<Comentario> buscarPorId(Long id)
    {
        try{
            if(comentarioRepository.existsById(id))
            {
                Optional<Comentario> entidade = comentarioRepository.findById(id);
                return new ResponseEntity<Comentario>(entidade.get(), ACCEPTED);
            }else {
                ResponseEntity responseEntity = new ResponseEntity<Usuario>(BAD_GATEWAY);
                return responseEntity;
            }
        }catch (Exception e)
        {
            System.out.println("Ops algo deu errado!");
            e.getStackTrace();
        }
        return null;
    }

    public ResponseEntity<ComentarioDTO> novoComentario(Long idPacote,ComentarioDTO comentarioDTO)
    {
        try{
            if(pacoteRepository.existsById(idPacote))
            {
                Pacote pacote = pacoteRepository.findById(idPacote).get();
                Backup backup = new Backup();
                if(usuarioRepository.existsById(comentarioDTO.getIdUsuario()))
                {
                    Usuario usuario = usuarioRepository.findById(comentarioDTO.getIdUsuario()).get();
                    Comentario comentario = new Comentario();
                    comentario.setTexto(comentarioDTO.getTexto());
                    comentario.setUsuario(usuario);
                    comentario.setDataComentario(LocalDateTime.now().minus(4,ChronoUnit.HOURS));
                    usuario.getComentarios().add(comentario);
                    pacote.getComentarios().add(comentario);
                    backup.setAcao(Acao.PACOTE_COMENTADO);
                    backup.setDataAcao(LocalDateTime.now().minus(4,ChronoUnit.HOURS));
                    backup.setUsuario(usuario);
                    backup.setPacote(pacote);
                    backup.setComentario(comentario);
                   // usuario.getBackups().add(backup);
                    comentarioRepository.save(comentario);
                    usuarioRepository.save(usuario);
                    pacoteRepository.save(pacote);
                    backupRepository.save(backup);
                    return new ResponseEntity<>(CREATED);
                }

            }
            else
            {
                return new ResponseEntity<>(BAD_REQUEST);
            }
        }catch (Exception e)
        {
            System.out.println("Ops algo deu errado!");
            e.getStackTrace();
        }
        return null;
    }

    public ResponseEntity<ComentarioDTO> editarComentario(Long idComentario, String texto)
    {
        try{
            if(comentarioRepository.existsById(idComentario))
            {
                Comentario comentario = comentarioRepository.findById(idComentario).get();
                comentario.setTexto(texto);
                comentarioRepository.save(comentario);
                return new ResponseEntity<>(OK);
            }
            else
            {
                return new ResponseEntity<>(BAD_REQUEST);
            }
        }catch (Exception e)
        {
            System.out.println("Ops algo deu errado!");
            e.getStackTrace();
        }
        return null;
    }


    public ResponseEntity<ComentarioDTO> deletar(Long id)
    {
        try{
            if(comentarioRepository.existsById(id))
            {
                Comentario comentario = comentarioRepository.findById(id).get();
                Backup backup = new Backup();
                backup.setAcao(Acao.COMENTARIO_EXCLUIDO);
                backup.setComentario(comentario);
                backup.setDataAcao(LocalDateTime.now().minus(4, ChronoUnit.HOURS));
                backupRepository.save(backup);
                ResponseEntity<ComentarioDTO> tResponseEntity = new ResponseEntity<>(OK);
                return tResponseEntity;

            }else {
                return new ResponseEntity<>(BAD_REQUEST);
            }
        }catch (Exception e)
        {
            System.out.println("Ops algo deu errado!");
            e.getStackTrace();
        }
        return null;
    }
}
