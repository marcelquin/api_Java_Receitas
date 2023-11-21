package baseAPI.API.Sistema.Service;

import baseAPI.API.Sistema.DTO.UsuarioDTO;
import baseAPI.API.Sistema.Enum.Acao;
import baseAPI.API.Sistema.Model.Backup;
import baseAPI.API.Sistema.Model.Usuario;
import baseAPI.API.Sistema.Repository.BackupRepository;
import baseAPI.API.Sistema.Repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    BackupRepository backupRepository;

    private static String caminhoImagem = "D:\\PROJETOS JAVA\\PROJETOS\\api_Java_Receitas\\Upload\\Usuario\\";

    public ResponseEntity<List<Usuario>> listar()
    {
        try{
            return new ResponseEntity<>(usuarioRepository.findAll(), OK);
        }catch (Exception e)
        {
            System.out.println("Ops algo deu errado!");
            e.getStackTrace();
        }
        return null;
    }

    public ResponseEntity<Usuario> buscarPorId(Long id)
    {
        try{
            if(usuarioRepository.existsById(id))
            {
                Optional<Usuario> entidade = usuarioRepository.findById(id);
                return new ResponseEntity<Usuario>(entidade.get(), ACCEPTED);
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

    public ResponseEntity<UsuarioDTO> salvar(UsuarioDTO usuarioDTO, MultipartFile file) throws SQLException, IOException
    {
        try{
            if(usuarioDTO != null)
            {
                Usuario usuario = new Usuario();
                usuario.setNome(usuarioDTO.getNome());
                usuario.setSobrenome(usuarioDTO.getSobrenome());
                usuario.setTelefone(usuarioDTO.getTelefone());
                usuario.setEmail(usuarioDTO.getEmail());
                usuario.setDataNascimento(usuarioDTO.getDataNascimento());
                usuario.setDataCriacao(LocalDateTime.now().minus(4, ChronoUnit.HOURS));
                int dig = (int) (101 + Math.random() * 899);
                if(!file.isEmpty())
                {
                    byte[] bytes = file.getBytes();
                    Path caminho = Paths.get(caminhoImagem+dig+"_"+file.getOriginalFilename());
                    Files.write(caminho, bytes);
                    usuario.setImagem(dig+"_"+file.getOriginalFilename());
                }

                Backup backup = new Backup();
                backup.setAcao(Acao.USUARIO_CRIADO);
                backup.setUsuario(usuario);
                backup.setDataAcao(LocalDateTime.now().minus(4,ChronoUnit.HOURS));
                System.out.println("..");
                //usuario.getBackups().add(backup);
                System.out.println("..");
                usuarioRepository.save(usuario);
                backupRepository.save(backup);
                return new ResponseEntity<>(CREATED);
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

    public ResponseEntity<UsuarioDTO> editar(Long id, String nome, String sorenome)
    {
        try{
            if(usuarioRepository.existsById(id))
            {
                Usuario usuario = usuarioRepository.findById(id).get();
                Backup backup = new Backup();
                if(nome != null)
                {
                    usuario.setNome(nome);
                }
                if(sorenome != null)
                {
                    usuario.setSobrenome(sorenome);
                }
                backup.setAcao(Acao.USUARIO_EDITADO);
                backup.setUsuario(usuario);
                backup.setDataAcao(LocalDateTime.now().minus(4,ChronoUnit.HOURS));
                //usuario.getBackups().add(backup);
                backupRepository.save(backup);
                usuarioRepository.save(usuario);
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

    public ResponseEntity<UsuarioDTO> alterarFoto(Long id, MultipartFile file) throws SQLException, IOException
    {
        try{
            if(usuarioRepository.existsById(id))
            {
                Usuario usuario = usuarioRepository.findById(id).get();
                Backup backup = new Backup();
                String filename = caminhoImagem+usuario.getImagem();
                removeArquivo(filename);//
                int dig = (int) (101 + Math.random() * 899);
                if(!file.isEmpty()){

                    byte[] bytes = file.getBytes();
                    Path caminho = Paths.get(caminhoImagem+dig+"_"+file.getOriginalFilename());
                    Files.write(caminho, bytes);
                    usuario.setImagem(dig+"_"+file.getOriginalFilename());
                }
                backup.setAcao(Acao.USUARIO_EDITADO);
                backup.setUsuario(usuario);
                backup.setDataAcao(LocalDateTime.now().minus(4,ChronoUnit.HOURS));
                //usuario.getBackups().add(backup);
                backupRepository.save(backup);
                usuarioRepository.save(usuario);
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

    public void removeArquivo (String fileName)
    {
        File file = new File(fileName);
        file.delete();
        System.out.println("deletou");
    }

    public ResponseEntity<UsuarioDTO> deletar(Long id)
    {
        try{
            if(usuarioRepository.existsById(id))
            {
                Usuario usuario = usuarioRepository.findById(id).get();
                usuarioRepository.deleteById(id);
                ResponseEntity<UsuarioDTO> tResponseEntity = new ResponseEntity<>(OK);
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
