package baseAPI.API.Sistema.Repository;

import baseAPI.API.Sistema.Model.Pacote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacoteRepository extends JpaRepository<Pacote, Long> {

    public Pacote findBycodigoDownload(String codigoDownload);
}
