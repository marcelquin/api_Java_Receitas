package baseAPI.API.Sistema.DTO;

import jakarta.persistence.JoinColumn;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioDTO {

    private String nome;

    private String sobrenome;

    private Long telefone;

    private String email;

    @JoinColumn(name = "data_Nascimento")
    private LocalDate dataNascimento;

}
