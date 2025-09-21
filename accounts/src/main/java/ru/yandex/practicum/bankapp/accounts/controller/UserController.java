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

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final AccountService accountService;
    private final SecurityContextRepository securityContextRepository;

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
    public AccountResponse me(Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        return accountService.getAccount(account);
    }

    @PostMapping("/edit")
    public void edit(
            @RequestBody AccountEditRequest request,
            Authentication authentication
    ) {
        Account account = (Account) authentication.getPrincipal();
        accountService.edit(account, request);
    }

    @GetMapping("/all")
    public List<UserResponse> fetAll() {
        return accountService.getAll();
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



    public record ChangePasswordRequest(String password, String confirmPassword) {
    }
}
