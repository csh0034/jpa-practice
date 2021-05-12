package study.rawjpa.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AccountRepositoryTests {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void saveAccount() {
        Account account = new Account("test@naver.com");
        Account saveAccount = accountRepository.saveAndFlush(account);
        assertThat(saveAccount.getId()).isNotNull();
    }

    @Test
    public void saveAccountAndFind() {
        Account account = new Account("test@naver.com");
        Account saveAccount = accountRepository.saveAndFlush(account);
        Account findAccount = accountRepository.findById(saveAccount.getId()).get();
        assertThat(findAccount.getId()).isNotNull();
    }

}