package xaltius.azanespaul.LMS.users;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @Mock
    UsersRepository usersRepository;

    @InjectMocks
    UsersService usersService;

    List<Users> usersList;

    @BeforeEach
    void setUp() {
        Users u1 = new Users();
        u1.setId(1L);
        u1.setName("Rakesh Kumar");

        Users u2 = new Users();
        u2.setId(2L);
        u2.setName("Paul Azanes");

        Users u3 = new Users();
        u3.setId(3L);
        u3.setName("Jimbo Delfin");

        this.usersList = new ArrayList<>();
        this.usersList.add(u1);
        this.usersList.add(u2);
        this.usersList.add(u3);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testSaveUserSuccess() {
        // Given

        // When

        // Then
    }

    @Test
    void testFindAllUsersSuccess() {
        // Given
        BDDMockito.given(usersRepository.findAll()).willReturn(this.usersList);

        // When
        List<Users> actualUsersList = usersService.findAllUsers();

        // Then
        Assertions.assertThat(actualUsersList.size()).isEqualTo(this.usersList.size());
        Mockito.verify(usersRepository, Mockito.times(1)).findAll();
    }
}