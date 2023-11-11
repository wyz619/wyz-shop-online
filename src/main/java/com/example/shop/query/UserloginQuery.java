package com.example.shop.query;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserloginQuery {
    @NotBlank(message = "code不能为空")
    private String code;
}
