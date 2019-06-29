package de.chainsaw.app.account.service;

import de.chainsaw.app.account.exception.BadRequestException;
import de.chainsaw.app.account.exception.NotFoundException;
import de.chainsaw.app.account.model.Account;
import de.chainsaw.app.account.repository.AccountRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

  private AccountRepository repository;

  @Autowired
  public AccountService(AccountRepository repository) {
    this.repository = repository;
  }

  public List<Account> findAll() {
    return repository.findAll();
  }

  public Account findById(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Account not found."));
  }

  @Transactional
  public void save(Account account) {
    beforeSave(account);
    repository.save(account);
  }

  private void beforeSave(Account account) {
    if(null == account ||
        repository.findById(account.getId()).isPresent()) {
      throw new BadRequestException("Account cannot be created.");
    }
  }
}
