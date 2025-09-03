package co.com.crediya.api.mapper;

import co.com.crediya.api.dto.SaveUserDto;
import co.com.crediya.api.dto.UserByEmailDto;
import co.com.crediya.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    User toModel(SaveUserDto saveUserDto);

    UserByEmailDto toUserByEmail(User user);
}
