package com.institutosaber.api.modules.auth.service;

import com.institutosaber.api.exception.BusinessException;
import com.institutosaber.api.exception.EntityNotFoundException;
import com.institutosaber.api.modules.auth.dto.*;
import com.institutosaber.api.modules.auth.entity.Rol;
import com.institutosaber.api.modules.auth.entity.Usuario;
import com.institutosaber.api.modules.auth.repository.RolRepository;
import com.institutosaber.api.modules.auth.repository.UsuarioRepository;
import com.institutosaber.api.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Override
    @Transactional
    public AuthResponseDto login(LoginRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        Usuario usuario = usuarioRepository.findByUsername(request.username())
                .orElseThrow(() -> new EntityNotFoundException("Usuario", "username", request.username()));

        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        log.info("Login exitoso para usuario: {}", request.username());

        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tipo("Bearer")
                .expiraEn(jwtTokenProvider.getExpiration())
                .userId(usuario.getId())
                .username(usuario.getUsername())
                .email(usuario.getEmail())
                .roles(roles)
                .build();
    }

    @Override
    public AuthResponseDto refreshToken(RefreshTokenRequestDto request) {
        String refreshToken = request.refreshToken();

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BusinessException("El refresh token es inválido o ha expirado.");
        }

        String tipo = jwtTokenProvider.getClaimFromToken(refreshToken, "tipo");
        if (!"refresh".equals(tipo)) {
            throw new BusinessException("El token proporcionado no es un refresh token.");
        }

        String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
        var userDetails = userDetailsService.loadUserByUsername(username);

        String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String newAccessToken = jwtTokenProvider.generateAccessTokenFromUsername(username, roles);
        String newRefreshToken = jwtTokenProvider.generateRefreshTokenFromUsername(username);

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuario", "username", username));

        return AuthResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .tipo("Bearer")
                .expiraEn(jwtTokenProvider.getExpiration())
                .userId(usuario.getId())
                .username(usuario.getUsername())
                .email(usuario.getEmail())
                .roles(userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet()))
                .build();
    }

    @Override
    public void logout(String refreshToken) {
        // En una implementación sin blacklist de tokens, el logout se maneja en el
        // cliente
        // limpiando el token del localStorage. Para mayor seguridad, se puede
        // implementar
        // una blacklist en Redis. Por ahora el endpoint retorna 200 OK.
        log.info("Logout exitoso (token limpiado en cliente)");
    }

    @Override
    public UsuarioResponseDto getPerfil(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuario", "username", username));
        return toUsuarioResponseDto(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDto registrarUsuario(RegistrarUsuarioRequestDto request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException("Ya existe un usuario con el nombre: " + request.getUsername());
        }
        if (request.getEmail() != null && usuarioRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Ya existe un usuario con el email: " + request.getEmail());
        }

        Rol rol = rolRepository.findByNombre(request.getRol())
                .orElseThrow(() -> new EntityNotFoundException("Rol", "nombre", request.getRol()));

        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .activo(true)
                .roles(Set.of(rol))
                .build();

        usuario = usuarioRepository.save(usuario);
        log.info("Nuevo usuario registrado: {} con rol: {}", request.getUsername(), request.getRol());
        return toUsuarioResponseDto(usuario);
    }

    private UsuarioResponseDto toUsuarioResponseDto(Usuario usuario) {
        Set<String> roles = usuario.getRoles().stream()
                .map(Rol::getNombre)
                .collect(Collectors.toSet());
        return new UsuarioResponseDto(
                usuario.getId(), usuario.getUsername(),
                usuario.getEmail(), usuario.getActivo(), roles);
    }
}
