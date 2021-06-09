package com.vivek.oauthgithub.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;


@Data
@Builder
public class User {
    private String username;
    private String password;
    private Set<Role> roles;
    private boolean enabled;
}
