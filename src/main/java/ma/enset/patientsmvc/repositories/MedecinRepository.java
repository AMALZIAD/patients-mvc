package ma.enset.patientsmvc.repositories;

import ma.enset.patientsmvc.entities.Medecin;
import ma.enset.patientsmvc.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedecinRepository extends JpaRepository<Medecin,Long> {
    Page<Medecin> findByNomContains(String kw, Pageable pageable);
}
