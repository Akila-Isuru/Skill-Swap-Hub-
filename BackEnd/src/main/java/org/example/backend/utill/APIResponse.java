package org.example.backend.utill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class APIResponse<T> {
    private int code;
    private String message;
    private T data;
}