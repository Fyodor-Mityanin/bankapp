package ru.yandex.practicum.bankapp.accounts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bankapp.accounts.repository.AccountRepository;

@Service
@RequiredArgsConstructor
public class AccountUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return accountRepository
                .findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + login));
    }
}
