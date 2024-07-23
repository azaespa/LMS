package xaltius.azanespaul.LMS.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import xaltius.azanespaul.LMS.system.Result;

import java.util.List;

@RestController
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/api/users")
    public Result saveUser(@RequestBody Users users){
        Users savedUsers = usersService.saveUser(users);
        return new Result(HttpStatus.OK.value(), "Save One Success", savedUsers);
    }

    @GetMapping("/api/users")
    public Result getAllUsers(){
        List<Users> usersList = usersService.findAllUsers();
        return new Result(HttpStatus.OK.value(), "Find All Success", usersList);
    }

    @GetMapping("/api/users/{id}")
    public Result getUsersById(@PathVariable Long id) {
        Users users = usersService.findUsersById(id);
        return new Result(HttpStatus.OK.value(), "Find One Success", users);
    }
}
