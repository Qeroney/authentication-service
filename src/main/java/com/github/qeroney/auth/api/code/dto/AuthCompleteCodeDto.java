package com.github.qeroney.auth.api.code.dto;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public record AuthCompleteCodeDto(
        @NotNull
        UUID code
){}
