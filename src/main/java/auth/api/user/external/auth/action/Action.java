package auth.api.user.external.auth.action;

import lombok.NonNull;

public interface Action<ArgumentT, ReturnedT> {
    ReturnedT execute(@NonNull ArgumentT var1);
}
