package auth.api.user.external.auth.api.user.external.mapper;

import auth.api.user.external.auth.action.user.argument.UpdatePasswordActionArgument;
import auth.api.user.external.auth.api.user.external.dto.UpdatePasswordByCodeExternalDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserExternalMapper {

    UpdatePasswordActionArgument toUpdatePasswordArgument(UpdatePasswordByCodeExternalDto dto);
}
