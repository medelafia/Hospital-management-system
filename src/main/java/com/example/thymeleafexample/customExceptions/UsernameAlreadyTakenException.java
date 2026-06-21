package com.example.thymeleafexample.customExceptions;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Builder @Getter
@Setter
public class UsernameAlreadyTakenException extends RuntimeException {
  private String message ;
}
