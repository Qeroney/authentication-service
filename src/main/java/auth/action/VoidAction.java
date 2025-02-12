package auth.action;

import lombok.NonNull;

public interface VoidAction<ArgumentT> {
    void execute(@NonNull ArgumentT var1);
}


