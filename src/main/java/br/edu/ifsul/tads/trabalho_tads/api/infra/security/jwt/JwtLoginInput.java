package br.edu.ifsul.tads.trabalho_tads.api.infra.security.jwt;

import lombok.Data;

@Data
public class JwtLoginInput {
    private String username;
    private String password;
}