package baseAPI.API.Sistema.AbstractFactory.Model;

import baseAPI.API.Sistema.AbstractFactory.Factory.ContaBanco;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ContaCorrente implements ContaBanco
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String Sobrenome;

    private Long cpf;

    private int agencia;

    private int conta;

    private Double saldo;

    @Override
    public Double Saque(Double valor) {
        return this.saldo = saldo - valor;
    }

    @Override
    public Double Deposito(Double valor) {
        return this.saldo = saldo + valor;
    }

    public double SaldoInicial(){
        this.saldo = 250.0;
        return this.saldo;
    }
}
