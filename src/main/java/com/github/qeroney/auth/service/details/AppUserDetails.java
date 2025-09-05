package com.github.qeroney.auth.service.details;

import com.github.qeroney.auth.model.UserType;
import io.github.qeroney.security.core.model.CustomUserDetailsImpl;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AppUserDetails extends CustomUserDetailsImpl {
     String firstName;
     String middleName;
     String lastName;
     UserType userType;
}