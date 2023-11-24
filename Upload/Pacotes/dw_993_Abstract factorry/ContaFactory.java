package baseAPI.API.Sistema.AbstractFactory.Factory;

import baseAPI.API.Sistema.AbstractFactory.Enum.TipoConta;
import baseAPI.API.Sistema.AbstractFactory.Model.ContaCorrente;
import baseAPI.API.Sistema.AbstractFactory.Model.ContaEmpresarial;
import baseAPI.API.Sistema.AbstractFactory.Model.ContaPoupanca;
import baseAPI.API.Sistema.AbstractFactory.Model.ContaSalario;
import org.springframework.stereotype.Component;

@Component
public class ContaFactory implements AbstractFactoryConta{


    @Override
    public ContaBanco criar(int tipo) {
        if(tipo == 1) {
            return new ContaCorrente();
        }
        else if(tipo == 2) {
            return new ContaPoupanca();
        }
        else if(tipo == 3) {
            return new ContaSalario();
        }
        else if(tipo == 4) {
            return new ContaEmpresarial();
        }
        return null;
    }
}
