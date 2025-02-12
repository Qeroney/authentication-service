package auth.api.user.internal.mapper;

import auth.api.user.internal.dto.CreateUserInternalDto;
import auth.api.user.internal.dto.UpdateInternalDto;
import auth.api.user.internal.dto.UserInternalDto;
import auth.model.User;
import auth.service.user.argument.CreateUserArgument;
import auth.service.user.argument.UpdateUserArgument;
import org.mapstruct.Mapper;
import ru.vadim.user.Role;

@Mapper
public interface UserInternalMapper {

    UserInternalDto toUserInternalDto(User user);

    CreateUserArgument toCreateUserArgument(CreateUserInternalDto dto, Role role);

    UpdateUserArgument toUpdateUserArgument(UpdateInternalDto dto, Role role);
}
