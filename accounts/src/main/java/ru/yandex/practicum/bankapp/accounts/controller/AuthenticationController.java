package ru.yandex.practicum.bankapp.accounts.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.bankapp.accounts.entity.Account;
import ru.yandex.practicum.bankapp.accounts.service.AccountService;

@Slf4j
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final AccountService accountService;
    private final SecurityContextRepository securityContextRepository;

    @PostMapping("/login")
    public void login(
            @RequestBody LoginRequest request,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse
    ) {
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(request.login(), request.password());
        Authentication auth = authenticationManager.authenticate(authReq);
        setToContext(auth, httpRequest, httpResponse);
   }

    @PostMapping("/register")
    public AccountResponse register(
            @RequestBody AccountRequest request,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse
    ) {
        if (accountService.existsByLogin(request.login())) {
            throw new RuntimeException("Username already exists");
        }
        AccountResponse account = accountService.create(request);
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(request.login(), request.password());
        Authentication auth = authenticationManager.authenticate(authReq);
        setToContext(auth, httpRequest, httpResponse);
        return account;
    }

    @PostMapping("/change-password")
    public void changePassword(
            @RequestBody ChangePasswordRequest request,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse,
            Authentication authentication
    ) {
        Account account = (Account) authentication.getPrincipal();
        if (!request.password.equals(request.confirmPassword)) {
            throw new RuntimeException("Passwords not the same");
        }
        accountService.changePassword(account.getId(), request.password);
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(account.getLogin(), request.password());
        Authentication auth = authenticationManager.authenticate(authReq);
        setToContext(auth, httpRequest, httpResponse);
    }

    @GetMapping("/me")
    public MeResponse me(Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        return new MeResponse(account.getId(), account.getUsername());
    }

    private void setToContext(
            Authentication authentication,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse
    ) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        securityContextRepository.saveContext(context, httpRequest, httpResponse);
        log.info("Session ID: {}", httpRequest.getSession().getId());
    }

    public record MeResponse(Long id, String login) {
    }

    public record LoginRequest(String login, String password) {
    }

    public record ChangePasswordRequest(String password, String confirmPassword) {
    }
}
