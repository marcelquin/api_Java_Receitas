package baseAPI.API.Sistema.Generic.Generic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

public class GenericServiceImpl<T extends BaseEntity> implements GenericService<T>{

    @Autowired
    protected GenericRepository<T> repository;

    @Override
    public ResponseEntity<List<T>> Listar() {
        try{
            return new ResponseEntity<>(repository.findAll(), OK);
        }catch (Exception e)
        {
            System.out.println("Ops algo deu errado na busca");
            e.getStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<T> buscarPorId(Long id) {
        try{
            if(repository.existsById(id))
            {
                Optional<T> entidade = repository.findById(id);
                return new ResponseEntity<T>(entidade.get(), ACCEPTED);
            }else {
                ResponseEntity responseEntity = new ResponseEntity<T>(BAD_GATEWAY);
                return responseEntity;
            }
        }catch (Exception e)
        {
            System.out.println("Ops algo deu errado na busca");
            e.getStackTrace();
        }
        return null;
    }

}
