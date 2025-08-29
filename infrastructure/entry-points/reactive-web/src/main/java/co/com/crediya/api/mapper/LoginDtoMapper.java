package co.com.crediya.api.mapper;

import co.com.crediya.api.dto.LoginDto;
import co.com.crediya.api.dto.SaveUserDto;
import co.com.crediya.model.login.Login;
import co.com.crediya.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginDtoMapper {
    Login toModel(LoginDto loginDto);
}
