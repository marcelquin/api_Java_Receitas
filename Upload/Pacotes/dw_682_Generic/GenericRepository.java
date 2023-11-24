package baseAPI.API.Sistema.Generic.Generic;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GenericRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

}
