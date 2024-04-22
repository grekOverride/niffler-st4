package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.db.model.*;
import guru.qa.niffler.db.repository.UserRepository;
import guru.qa.niffler.jupiter.extension.UserRepositoryExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@ExtendWith(UserRepositoryExtension.class)
public class LoginHw4Test extends BaseWebTest {

    private UserRepository userRepository;

    private UserAuthEntity userAuth;
    private UserEntity user;

    @BeforeEach
    void createUser() {
        userAuth = new UserAuthEntity();
        userAuth.setUsername("valentin_2");
        userAuth.setPassword("12345");
        userAuth.setEnabled(true);
        userAuth.setAccountNonExpired(true);
        userAuth.setAccountNonLocked(true);
        userAuth.setCredentialsNonExpired(true);
        userAuth.setAuthorities(Arrays.stream(Authority.values())
                .map(e -> {
                    AuthorityEntity ae = new AuthorityEntity();
                    ae.setAuthority(e);
                    return ae;
                }).toList()
        );

        user = new UserEntity();
        user.setUsername("valentin_2");
        user.setCurrency(CurrencyValues.RUB);
        userRepository.createInAuth(userAuth);
        userRepository.createInUserdata(user);

    }

    @AfterEach
    void removeUser() {
        userRepository.deleteInAuthById(userAuth.getId());
        userRepository.deleteInUserdataById(user.getId());
    }

    @Test
    void testForHw4() {
        //тест для проверки update users methods
        user.setFirstname("firstName");
        user.setSurname("surName");
        user.setCurrency(CurrencyValues.EUR);
        //user.setPhoto();

        userAuth.setPassword("123456");
        userAuth.setEnabled(false);
        userAuth.setAccountNonExpired(false);
        userAuth.setAccountNonLocked(false);
        userAuth.setCredentialsNonExpired(false);

        AuthorityEntity ae = new AuthorityEntity();
        ae.setAuthority(Authority.read);
        userAuth.setAuthorities(List.of(ae));

        userRepository.updateInAuth(userAuth);
        userRepository.updateInUserdata(user);
    }
}
