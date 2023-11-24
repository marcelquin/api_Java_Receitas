package baseAPI.API.Sistema.Service;

import baseAPI.API.Sistema.Enum.Acao;
import baseAPI.API.Sistema.Model.Backup;
import baseAPI.API.Sistema.Model.Pacote;
import baseAPI.API.Sistema.Model.Usuario;
import baseAPI.API.Sistema.Repository.BackupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static java.nio.file.Paths.get;
import static org.apache.tomcat.util.http.fileupload.FileUploadBase.CONTENT_DISPOSITION;
import static org.springframework.http.HttpStatus.*;

@Service
public class BackupService {

    @Autowired
    BackupRepository backupRepository;

    private static String caminhoImagembackup = "D:\\PROJETOS JAVA\\PROJETOS\\api_Java_Receitas\\Upload\\Backup\\";


    public ResponseEntity<List<Backup>> listar()
    {
        try{
            return new ResponseEntity<>(backupRepository.findAll(), OK);
        }catch (Exception e)
        {
            System.out.println("Ops algo deu errado!");
            e.getStackTrace();
        }
        return null;
    }

    public ResponseEntity<Backup> buscarPorId(Long id)
    {
        try{
            if(backupRepository.existsById(id))
            {
                Optional<Backup> entidade = backupRepository.findById(id);
                return new ResponseEntity<Backup>(entidade.get(), ACCEPTED);
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


    public ResponseEntity<Resource> downloadFiles(Long idbackup) throws IOException
    {
        if(idbackup != null)
        {
            if(backupRepository.existsById(idbackup))
            {
                Backup backup = backupRepository.findById(idbackup).get();
                if(backup != null)
                {
                    String filename = backup.getArquivoDeletado();
                    Path filePath = get(caminhoImagembackup).toAbsolutePath().normalize().resolve(filename);
                    if (!Files.exists(filePath)) {
                        throw new FileNotFoundException(filename + " was not found on the server");
                    }
                    Backup backup2 = new Backup();
                    backup2.setAcao(Acao.BACKUP_DOWNLOAD);
                    backup2.setUsuario(backup.getUsuario());
                    backup2.setPacote(backup.getPacote());
                    backup2.setArquivoDeletado(backup.getArquivoDeletado());
                    backup2.setDataAcao(LocalDateTime.now().minus(4, ChronoUnit.HOURS));
                    backupRepository.save(backup2);
                    Resource resource = new UrlResource(filePath.toUri());
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add("File-Name", filename);
                    httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
                    return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                            .headers(httpHeaders).body(resource);
                }

            }
        }
        return new ResponseEntity<>(BAD_REQUEST);
    }

}
