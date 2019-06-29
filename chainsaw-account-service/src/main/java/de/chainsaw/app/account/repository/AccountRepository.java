package de.chainsaw.app.account.repository;

import de.chainsaw.app.account.model.Account;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface AccountRepository extends Repository<Account, Long> {

  List<Account> findAll();

  Optional<Account> findById(Long id);

  void save(Account account);
}
