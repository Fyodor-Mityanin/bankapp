package ru.yandex.practicum.bankapp.accounts.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.bankapp.accounts.entity.Account;
import ru.yandex.practicum.bankapp.accounts.service.AccountService;

@RestController
@RequestMapping("/accounts/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final AccountService accountService;

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        UsernamePasswordAuthenticationToken authReq =
                new UsernamePasswordAuthenticationToken(request.login(), request.password());
        Authentication auth = authenticationManager.authenticate(authReq);
        SecurityContextHolder.getContext().setAuthentication(auth);

        HttpSession session = httpRequest.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    }

    @PostMapping("/register")
    public AccountResponse register(@RequestBody AccountRequest request) {
        if (accountService.existsByLogin(request.login())) {
            throw new RuntimeException("Username already exists");
        }
        return accountService.create(request);
    }

    @GetMapping("/me")
    public MeResponse me(Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        return new MeResponse(account.getId(), account.getUsername());
    }

    public record MeResponse(Long id, String login) {}
    public record LoginRequest(String login, String password) {}
}
