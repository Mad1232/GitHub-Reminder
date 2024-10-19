package com.coms309.demo2.entity;
import java.io.Serializable;

import lombok.Data;
import lombok.NonNull;

@Data
public class ConversationKey implements Serializable {
    @NonNull
    User user;
    @NonNull
    Vet vet;
}
