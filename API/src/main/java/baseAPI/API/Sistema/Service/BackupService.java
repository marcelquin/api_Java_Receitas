package baseAPI.API.Sistema.Service;

import baseAPI.API.Sistema.Enum.Acao;
import baseAPI.API.Sistema.Model.Backup;
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
                ResponseEntity responseEntity = new ResponseEntity<Backup>(BAD_GATEWAY);
                return responseEntity;
            }
        }catch (Exception e)
        {
            System.out.println("Ops algo deu errado!");
            e.getStackTrace();
        }
        return null;
    }


}
