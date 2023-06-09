package br.edu.ifsul.tads.trabalho_tads;
import br.edu.ifsul.tads.trabalho_tads.api.infra.security.jwt.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import static junit.framework.TestCase.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrabalhoTadsApplication.class)

public class TokenJWTTest {
    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Test
    public void testToken() {
        UserDetails user = userDetailsService.loadUserByUsername("admin");
        assertNotNull(user);
        System.out.println("User lido:");
        System.out.println(user);

        //Gera token
        String jwtToken = JwtUtil.createToken(user);
        System.out.println("jwtToken gerado");
        System.out.println(jwtToken);
        assertNotNull(jwtToken);

        //Valida token
        boolean ok = JwtUtil.isTokenValid(jwtToken);
        assertTrue(ok);

        //Valida login
        String login = JwtUtil.getLogin(jwtToken);
        assertEquals("admin", login);

        //Valida roles
        List<GrantedAuthority> roles = JwtUtil.getRoles(jwtToken);
        assertNotNull(roles);
        System.out.println("Roles:");
        System.out.println(roles);
        String role = roles.get(0).getAuthority();
        assertEquals(role, "ROLE_ADMIN");
    }
}
