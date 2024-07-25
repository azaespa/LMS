package xaltius.azanespaul.LMS.users.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import xaltius.azanespaul.LMS.users.Users;
import xaltius.azanespaul.LMS.users.dto.UsersDto;

@Component
public class UsersToUsersDtoConverter implements Converter<Users, UsersDto> {
    @Override
    public UsersDto convert(Users source) {
        UsersDto usersDto = new UsersDto(source.getId(), source.getName());

        return usersDto;
    }
}
