package baseAPI.API.Sistema.Generic.Generic;


import org.springframework.http.ResponseEntity;


import java.util.List;

public interface GenericService<T  extends  BaseEntity> {

    public ResponseEntity<List<T>> Listar();

    public ResponseEntity<T> buscarPorId(Long id);




}
