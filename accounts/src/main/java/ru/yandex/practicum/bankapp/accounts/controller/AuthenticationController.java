package ru.yandex.practicum.bankapp.accounts.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
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

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(request.login(), request.password());
        Authentication auth = authenticationManager.authenticate(authReq);
        setToContext(auth, httpRequest);
   }

    @PostMapping("/register")
    public AccountResponse register(@RequestBody AccountRequest request, HttpServletRequest httpRequest) {
        if (accountService.existsByLogin(request.login())) {
            throw new RuntimeException("Username already exists");
        }
        AccountResponse account = accountService.create(request);
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(request.login(), request.password());
        Authentication auth = authenticationManager.authenticate(authReq);
        setToContext(auth, httpRequest);
        return account;
    }

    @GetMapping("/me")
    public MeResponse me(Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        return new MeResponse(account.getId(), account.getUsername());
    }

    private void setToContext(Authentication authentication, HttpServletRequest httpRequest) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = httpRequest.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        log.info("Session ID: {}", session.getId());
    }

    public record MeResponse(Long id, String login) {
    }

    public record LoginRequest(String login, String password) {
    }
}
