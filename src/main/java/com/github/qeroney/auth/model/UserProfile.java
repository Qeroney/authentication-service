package com.github.qeroney.auth.model;

import lombok.*;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class UserProfile {

    String firstName;

    String middleName;

    String lastName;
}
