package ma.enset.patientsmvc.sec.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.patientsmvc.sec.entities.AppRole;
import ma.enset.patientsmvc.sec.entities.AppUser;
import ma.enset.patientsmvc.sec.repositories.AppRoleRepository;
import ma.enset.patientsmvc.sec.repositories.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class SecurityServiceImpl implements SecurityService {

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser saveNewUser(String username, String password, String rePassword) {
       if(!password.equals(rePassword))throw new RuntimeException("Passwords not match");
       String hashedPWD= passwordEncoder.encode(password);
        AppUser appuser=new AppUser();
        appuser.setUserId(UUID.randomUUID().toString());
        appuser.setUsername(username);
        appuser.setPassword(hashedPWD);
        AppUser savedUser=appUserRepository.save(appuser);
        return savedUser;
    }

    @Override
    public AppRole saveNewRole(String roleName, String description) {
        AppRole approle= appRoleRepository.findByRole(roleName);
       if( approle!=null) throw new RuntimeException("The Role already "+roleName+"exists !");
       approle=new AppRole();
        approle.setRole(roleName);
        approle.setDescription(description);
       AppRole savedapprole= appRoleRepository.save(approle);
        return savedapprole;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppRole approle= appRoleRepository.findByRole(roleName);
        if( approle==null) throw new RuntimeException("The Role not found!");
        AppUser appuser= appUserRepository.findByUsername(username);
        if( appuser==null) throw new RuntimeException("The User not found!");
        appuser.getAppRoles().add(approle);
    }

    @Override
    public void removeRoleFromUser(String username, String roleName) {
        AppRole approle= appRoleRepository.findByRole(roleName);
        if( approle==null) throw new RuntimeException("The Role not found!");
        AppUser appuser= appUserRepository.findByUsername(username);
        if( appuser==null) throw new RuntimeException("The User not found!");
        appuser.getAppRoles().remove(approle);
    }

    @Override
    public AppUser loadUserByUserName(String username) {

        return appUserRepository.findByUsername(username);
    }
}
