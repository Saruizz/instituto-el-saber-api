package com.institutosaber.api.modules.auth.controller;

import com.institutosaber.api.modules.auth.dto.*;
import com.institutosaber.api.modules.auth.service.AuthService;
import com.institutosaber.api.shared.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "1. Autenticación", description = "Login, refresh de tokens y gestión de sesión")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Retorna access token (15 min) y refresh token (7 días)")
    public ResponseEntity<ApiResponse<AuthResponseDto>> login(
            @Valid @RequestBody LoginRequestDto request) {
        AuthResponseDto response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.ok(response, "Login exitoso"));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Renovar token de acceso usando el refresh token")
    public ResponseEntity<ApiResponse<AuthResponseDto>> refresh(
            @Valid @RequestBody RefreshTokenRequestDto request) {
        AuthResponseDto response = authService.refreshToken(request);
        return ResponseEntity.ok(ApiResponse.ok(response, "Token renovado exitosamente"));
    }

    @PostMapping("/logout")
    @Operation(summary = "Cerrar sesión")
    public ResponseEntity<ApiResponse<Void>> logout(
            @RequestBody(required = false) RefreshTokenRequestDto request) {
        String token = request != null ? request.refreshToken() : null;
        authService.logout(token);
        return ResponseEntity.ok(ApiResponse.ok(null, "Sesión cerrada exitosamente"));
    }

    @GetMapping("/perfil")
    @Operation(summary = "Obtener perfil del usuario autenticado")
    public ResponseEntity<ApiResponse<UsuarioResponseDto>> getPerfil(Authentication authentication) {
        UsuarioResponseDto perfil = authService.getPerfil(authentication.getName());
        return ResponseEntity.ok(ApiResponse.ok(perfil));
    }

    @PostMapping("/registrar")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Registrar nuevo usuario del sistema (solo ADMIN)")
    public ResponseEntity<ApiResponse<UsuarioResponseDto>> registrar(
            @Valid @RequestBody RegistrarUsuarioRequestDto request) {
        UsuarioResponseDto usuario = authService.registrarUsuario(request);
        return ResponseEntity.status(201).body(ApiResponse.ok(usuario, "Usuario registrado exitosamente"));
    }
}
