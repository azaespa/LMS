package xaltius.azanespaul.LMS.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/api/users")
    public Users saveUser(@RequestBody Users users){
        return usersService.saveUser(users);
    }

    @GetMapping("/api/users")
    public List<Users> getAllUsers(){
        return usersService.findAllUsers();
    }
}
