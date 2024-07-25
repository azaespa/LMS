package xaltius.azanespaul.LMS.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import xaltius.azanespaul.LMS.system.Result;
import xaltius.azanespaul.LMS.users.converter.UsersToUsersDtoConverter;
import xaltius.azanespaul.LMS.users.dto.UsersDto;

import java.util.List;

@RestController
public class UsersController {

    @Autowired
    private UsersService usersService;

    private final UsersToUsersDtoConverter usersToUsersDtoConverter;

    public UsersController(UsersToUsersDtoConverter usersToUsersDtoConverter) {
        this.usersToUsersDtoConverter = usersToUsersDtoConverter;
    }

    @PostMapping("/api/users")
    public Result saveUser(@RequestBody Users users){
        Users savedUsers = usersService.saveUser(users);
        UsersDto usersDto = this.usersToUsersDtoConverter.convert(savedUsers);

        return new Result("Save One Success", HttpStatus.OK.value(), usersDto);
    }

    @GetMapping("/api/users")
    public Result getAllUsers(){
        List<Users> usersList = usersService.findAllUsers();
        List<UsersDto> usersDtos = usersList.stream().map(this.usersToUsersDtoConverter::convert).toList();

        return new Result("Find All Success", HttpStatus.OK.value(), usersDtos);
    }

    @GetMapping("/api/users/{id}")
    public Result getUsersById(@PathVariable Long id) {
        Users users = usersService.findUsersById(id);
        UsersDto usersDto = this.usersToUsersDtoConverter.convert(users);

        return new Result("Find One Success", HttpStatus.OK.value(), usersDto);
    }
}
