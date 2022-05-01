package ma.enset.patientsmvc.entities;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import java.util.Date;
@Entity // as table in sql
@Data   // add gitter and sitters
// construteur sans et avec all params
@NoArgsConstructor @AllArgsConstructor @ToString
public class Patient {
    // primary key autoincrement
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    @NonNull
    @Size(min = 4,max = 50)
    private String nom;
    // specifier le type sur la table sql
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateNaissance;
    private boolean malade;
    @DecimalMin("100")
    private int score;
}
