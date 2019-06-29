package de.chainsaw.app.account.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import de.chainsaw.app.account.exception.BadRequestException;
import de.chainsaw.app.account.exception.NotFoundException;
import de.chainsaw.app.account.model.Account;
import de.chainsaw.app.account.repository.AccountRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@SuppressWarnings("squid:S00100")
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

  @Mock
  private AccountRepository repository;

  @InjectMocks
  private AccountService service;

  @Test
  public void testFindAll() {

    when(repository.findAll())
        .thenReturn(Arrays.asList(new Account()));

    final List<Account> accountList = service.findAll();

    verify(repository).findAll();
    verifyNoMoreInteractions(repository);

    assertThat(accountList).isNotNull();
    assertThat(accountList.size()).isEqualTo(1);
  }

  @Test
  public void testFindById_ReturnOne() {
    when(repository.findById(anyLong()))
        .thenReturn(Optional.of(new Account()));

    final Account account = service.findById(1L);

    verify(repository).findById(eq(1L));
    verifyNoMoreInteractions(repository);

    assertThat(account).isNotNull();
  }

  @Test
  public void testFindById_throwsNotFoundException() {
    when(repository.findById(anyLong()))
        .thenReturn(Optional.empty());

    assertThatThrownBy(
        () -> service.findById(1L)
    ).isInstanceOf(NotFoundException.class);

    verify(repository).findById(eq(1L));
    verifyNoMoreInteractions(repository);
  }

  @Test
  public void testFindByNull_throwsNotFoundException() {
    when(repository.findById(any()))
        .thenReturn(Optional.empty());

    assertThatThrownBy(
        () -> service.findById(null)
    ).isInstanceOf(NotFoundException.class);

  }

  @Test
  public void testSaveNull_throwsBadRequestException() {

    assertThatThrownBy(
        () -> service.save(null)
    ).isInstanceOf(BadRequestException.class);

  }

  @Test
  public void testSaveEmptyAccount_throwsBadRequestException() {

    when(repository.findById(any()))
        .thenReturn(Optional.of(new Account()));

    assertThatThrownBy(
        () -> service.save(new Account())
    ).isInstanceOf(BadRequestException.class);

    verify(repository).findById(eq(null));
  }

  @Test
  public void testSaveAccount_Success() {

    when(repository.findById(any()))
        .thenReturn(Optional.empty());

    service.save(new Account());

    verify(repository).findById(any());
    verify(repository).save(any(Account.class));
    verifyNoMoreInteractions(repository);
  }
}
