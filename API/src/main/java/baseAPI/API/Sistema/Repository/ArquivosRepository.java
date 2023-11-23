package baseAPI.API.Sistema.Repository;

import baseAPI.API.Sistema.Model.Arquivos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArquivosRepository extends JpaRepository<Arquivos, Long> {
}
