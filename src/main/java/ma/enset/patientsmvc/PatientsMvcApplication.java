package ma.enset.patientsmvc;

import ma.enset.patientsmvc.entities.Medecin;
import ma.enset.patientsmvc.entities.Patient;
import ma.enset.patientsmvc.repositories.MedecinRepository;
import ma.enset.patientsmvc.repositories.PatientRepository;
import ma.enset.patientsmvc.sec.services.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private Long id;
    private String nom;
    private Date datEmbauche;
    private String grade;
    private boolean status;
    //@Bean
    CommandLineRunner  commandLineRunner(MedecinRepository medecinrepository){
        return args ->{
            medecinrepository.save(new Medecin(null,"najib",new Date(),"general",true));
            medecinrepository.save(new Medecin(null,"sara",new Date(),"doctor",false));
            medecinrepository.save(new Medecin(null,"lina",new Date(),"Professeur",true));
            medecinrepository.save(new Medecin(null,"dina",new Date(),"specialiste",false));
            medecinrepository.findAll().forEach(patient -> {
                System.out.println(patient.getNom());
            });
        };
    }
    //@Bean
    CommandLineRunner saveusers(SecurityService securityService){
        return args -> {
            securityService.saveNewUser("mohamed", "1234", "1234");
            securityService.saveNewUser("yasmine", "1234", "1234");
            securityService.saveNewUser("hassan", "1234", "1234");

            securityService.saveNewRole("ADMIN", "ADMINISTRATEUR");
            securityService.saveNewRole("USER", "UTILISATEUR");

            securityService.addRoleToUser("mohamed", "USER");
            securityService.addRoleToUser("mohamed", "ADMIN");
            securityService.addRoleToUser("yasmine", "USER");
            securityService.addRoleToUser("hassan", "USER");
        };

    }

    @Bean // start in the beggining and placed it in your context it become spring bean
        // if this needed in other pacages we use only autowired ( in other class)
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
