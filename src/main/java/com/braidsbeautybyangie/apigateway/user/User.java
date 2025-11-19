package com.braidsbeautybyangie.apigateway.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class User {
    private Long id;
    private String keycloakId;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private Boolean enabled;

    private boolean admin;
    private List<Role> roles;
    @Email
    @NotBlank
    private String email;
    public User() {
    }

    public User(Long id, String keycloakId, String password, String username, Boolean enabled, boolean admin, List<Role> roles, String email) {
        this.id = id;
        this.keycloakId = keycloakId;
        this.password = password;
        this.username = username;
        this.enabled = enabled;
        this.admin = admin;
        this.roles = roles;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    public String getKeycloakId() {
        return keycloakId;
    }
    public void setKeycloakId(String keycloakId) {
        this.keycloakId = keycloakId;
    }
}
