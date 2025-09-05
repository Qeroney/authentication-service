package com.github.qeroney.auth.api.user.internal.mapper;

import com.github.qeroney.auth.api.user.internal.dto.UpdateInternalDto;
import com.github.qeroney.auth.api.user.internal.dto.UserInternalDto;
import com.github.qeroney.auth.model.User;
import com.github.qeroney.auth.service.user.argument.UpdateUserArgument;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper
public interface UserInternalMapper {

    UserInternalDto toUserInternalDto(User user);

    UpdateUserArgument toUpdateUserArgument(UpdateInternalDto dto, Set<String> authorities);
}
