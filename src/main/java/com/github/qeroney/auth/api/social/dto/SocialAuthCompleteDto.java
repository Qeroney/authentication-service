package com.github.qeroney.auth.api.social.dto;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public record SocialAuthCompleteDto(
        @NotNull
        UUID code
) {}

