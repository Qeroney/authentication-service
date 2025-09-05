package com.github.qeroney.auth.api.user.external.mapper;

import com.github.qeroney.auth.action.user.argument.AuthenticationUserActionArgument;
import com.github.qeroney.auth.action.user.argument.CompleteRegistrationActionArgument;
import com.github.qeroney.auth.action.user.argument.UpdatePasswordActionArgument;
import com.github.qeroney.auth.api.user.external.dto.AuthenticationUserExternalDto;
import com.github.qeroney.auth.api.user.external.dto.CompleteRegistrationExternalDto;
import com.github.qeroney.auth.api.user.external.dto.UpdatePasswordByCodeExternalDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserExternalMapper {

    UpdatePasswordActionArgument toUpdatePasswordArgument(UpdatePasswordByCodeExternalDto dto);

    CompleteRegistrationActionArgument toCompleteRegistrationArgument(CompleteRegistrationExternalDto dto);

    AuthenticationUserActionArgument toAuthenticationArgument(AuthenticationUserExternalDto dto);
}
