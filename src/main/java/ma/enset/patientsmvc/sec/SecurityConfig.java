package ma.enset.patientsmvc.sec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration// first class to be inst   anciate to config the app
@EnableWebSecurity // activate the web security
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // how to get user and roles  ( in memory , bd or annuaire )
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // des password crypté  sans cryptage en note "{noop}1234" sinon
        PasswordEncoder passwordEncoder=passwordEncoder();
        String pwdEncoded =passwordEncoder.encode("1234");

        // in this tp in momery all user are stored in the memory
        auth.inMemoryAuthentication()
                .withUser("user1").password(pwdEncoded).roles("USER")
                .and()
                .withUser("user2").password(pwdEncoded).roles("USER")
                .and()
                .withUser("admin").password(pwdEncoded).roles("USER","ADMIN");
    }

    // set the autorisation accordin,g to role
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // authentication form we can get the default or a specific
        // http.formLogin().loginPage("/login");
        http.formLogin();
        // aauthorisations
        http.authorizeRequests().anyRequest().authenticated();// all request needs authentication
    }
    @Bean // start in the beggining and placed it in your context it become spring bean
        // if this needed in other pacages we use only autowired ( in other class)
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
