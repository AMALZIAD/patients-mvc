package ma.enset.patientsmvc;

import ma.enset.patientsmvc.entities.Patient;
import ma.enset.patientsmvc.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class PatientsMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientsMvcApplication.class, args);
    }
    //@Bean
    CommandLineRunner  commandLineRunner(PatientRepository patientrepository){
        return args ->{
            patientrepository.save(new Patient(null,"Amal",new Date(),true,10));
            patientrepository.save(new Patient(null,"sara",new Date(),false,20));
            patientrepository.save(new Patient(null,"lina",new Date(),false,60));
            patientrepository.save(new Patient(null,"dina",new Date(),true,70));
            patientrepository.findAll().forEach(patient -> {
                System.out.println(patient.getNom());
            });
        };
    }

}
