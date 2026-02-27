package com.institutosaber.api.modules.auth.service;

import com.institutosaber.api.modules.auth.dto.*;

public interface AuthService {
    AuthResponseDto login(LoginRequestDto request);

    AuthResponseDto refreshToken(RefreshTokenRequestDto request);

    void logout(String refreshToken);

    UsuarioResponseDto getPerfil(String username);

    UsuarioResponseDto registrarUsuario(RegistrarUsuarioRequestDto request);
}
