package ma.enset.patientsmvc.repositories;

import ma.enset.patientsmvc.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient,Long> {

      Page <Patient> findByNomContains(String kw, Pageable pageable);


}