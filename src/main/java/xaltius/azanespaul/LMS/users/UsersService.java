package xaltius.azanespaul.LMS.users;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public Users saveUser(Users users) {
        return usersRepository.save(users);
    }

    public List<Users> findAllUsers() {
        return usersRepository.findAll();
    }
}
