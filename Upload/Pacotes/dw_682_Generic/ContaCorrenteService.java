package baseAPI.API.Sistema.Generic.Service;

import baseAPI.API.Sistema.Generic.Generic.GenericServiceImpl;
import baseAPI.API.Sistema.Generic.Model.ContaCorrente;
import baseAPI.API.Sistema.Generic.Repository.ContaCorrenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContaCorrenteService extends GenericServiceImpl<ContaCorrente> {

    @Autowired
    ContaCorrenteRepository repository;

    //salvar

    //editar
}
