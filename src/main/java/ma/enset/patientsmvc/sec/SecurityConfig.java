package ma.enset.patientsmvc.sec;
import ma.enset.patientsmvc.sec.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration// first class to be inst   anciate to config the app
@EnableWebSecurity // activate the web security
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
   private DataSource dataSource;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder ;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // how to get user and roles  ( in memory , bd or annuaire )
        // des password crypté  sans cryptage en note "{noop}1234" sinon
     /*  PasswordEncoder passwordEncoder1=passwordEncoder();
         String pwdEncoded =passwordEncoder1.encode("1234");
            System.out.println(pwdEncoded);
        // in this tp in momery all user are stored in the memory
        auth.inMemoryAuthentication()
                .withUser("user1").password(pwdEncoded).roles("USER")
                .and()
                .withUser("user2").password(pwdEncoded).roles("USER")
                .and()
                .withUser("admin").password(pwdEncoded).roles("USER","ADMIN");*/

      /*  auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username as principal,password as credential ,active from users where username=?")
                .authoritiesByUsernameQuery("select username as principal, role as role from user_role where username=?")
                .rolePrefix("ROLE_")
                .passwordEncoder(passwordEncoder1);*/


        auth.userDetailsService(userDetailsService );


    }

    // set the autorisation accordin,g to role
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // authentication form we can get the default or a specific
        // http.formLogin().loginPage("/login");
        http.formLogin();
        // aauthorisations
        // get this page without authentication
        http.authorizeRequests().antMatchers("/").permitAll();
        // some operation are authorized only for admin like
       /* http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/user/**").hasRole("USER");*/
        http.authorizeRequests().antMatchers("/admin/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/user/**").hasAuthority("USER");
        http.authorizeRequests().antMatchers("/webjars/**").permitAll();

        http.authorizeRequests().anyRequest().authenticated();// all request needs authentication
        http.exceptionHandling().accessDeniedPage("/403");

        // using mysql
    }


}
