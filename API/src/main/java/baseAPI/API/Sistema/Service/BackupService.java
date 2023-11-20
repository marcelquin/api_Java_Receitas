package baseAPI.API.Sistema.Service;

import baseAPI.API.Sistema.Model.Backup;
import baseAPI.API.Sistema.Model.Pacote;
import baseAPI.API.Sistema.Model.Usuario;
import baseAPI.API.Sistema.Repository.BackupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Service
public class BackupService {

    @Autowired
    BackupRepository backupRepository;

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


}
