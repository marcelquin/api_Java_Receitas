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

    @Enumerated(EnumType.STRING)
    private Acao acao;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "backup_pacote_id")
    private Pacote pacote;

    private String arquivoDeletado;

    private String arquivoBackup;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataAcao;


}
