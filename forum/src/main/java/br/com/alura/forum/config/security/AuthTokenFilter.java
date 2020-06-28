package br.com.alura.forum.config.security;

import br.com.alura.forum.model.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    public AuthTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {

        String token =  getToken(httpServletRequest);
        boolean valid = tokenService.isValidToken(token);
        if(valid){
            authenticateClient(token);
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private void authenticateClient(String token) {
        Long id = tokenService.getIdUserByToken(token);
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isPresent()){
            UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(usuario.get(),null,usuario.get().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(user);
        }
    }

    private String getToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
        if(token == null || token.isEmpty() || !token.startsWith("Bearer"))
            return null;
        return token.substring(7);
    }
}
