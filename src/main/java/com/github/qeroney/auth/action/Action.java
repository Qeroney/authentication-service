package com.github.qeroney.auth.action;

import lombok.NonNull;

public interface Action<ArgumentT, ReturnedT> {
    ReturnedT execute(@NonNull ArgumentT var1);
}
