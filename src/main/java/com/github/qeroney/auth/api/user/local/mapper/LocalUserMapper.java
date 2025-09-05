package com.github.qeroney.auth.api.user.local.mapper;

import com.github.qeroney.auth.action.user.argument.CompleteRegistrationTelegramUserActionArgument;
import com.github.qeroney.auth.api.user.local.dto.CompleteRegistrationTelegramUserDto;
import com.github.qeroney.auth.api.user.local.dto.UserDto;
import com.github.qeroney.auth.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface LocalUserMapper {

    UserDto toDto(User user);

    CompleteRegistrationTelegramUserActionArgument toArgument(CompleteRegistrationTelegramUserDto dto);
}
