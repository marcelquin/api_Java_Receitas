package baseAPI.API.Sistema.Generic.Generic;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private Long cpf;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data_nascimento;

    private int agencia;

    private Long conta;

    private Double saldo;

    private String imagem;

}
