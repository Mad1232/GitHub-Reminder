package com.coms309.demo2.entity;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConversationKey implements Serializable {
    @NonNull
    User user;
    @NonNull
    Vet vet;
}
