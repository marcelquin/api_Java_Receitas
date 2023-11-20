package baseAPI.API.Sistema.Model;


import baseAPI.API.Sistema.Enum.Acao;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Backup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    private Acao acao;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "backup_usuario_id")
    private Usuario usuario;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "backup_comentario_id")
    private Comentario comentario;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "backup_pacote_id")
    private Pacote pacote;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataAcao;


}
