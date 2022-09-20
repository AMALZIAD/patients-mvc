package ma.enset.patientsmvc.sec.services;

import ma.enset.patientsmvc.entities.Medecin;
import ma.enset.patientsmvc.sec.entities.AppUser;
import ma.enset.patientsmvc.sec.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private  SecurityService securityService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser= securityService.loadUserByUserName(username);
       /* Collection <GrantedAuthority> authorities =new ArrayList<>();
        appUser.getAppRoles().forEach(appRole -> {
            authorities.add(new SimpleGrantedAuthority(appRole.getRole()));
        });
*/
        Collection <GrantedAuthority> authorities1 =appUser
                .getAppRoles()
                .stream()
                .map(appRole -> new SimpleGrantedAuthority(appRole.getRole()))
                .collect(Collectors.toList());

        User user =new User(appUser.getUsername(), appUser.getPassword(), authorities1);
        return user;
    }
}
