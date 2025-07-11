package com.apple.auth_facade.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
public class AuthorizationRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Action is required")
    @Pattern(regexp = "read|write", message = "Action must be either 'read' or 'write'")
    private String action;

    @NotBlank(message = "Resource is required")
    private String resource;
}