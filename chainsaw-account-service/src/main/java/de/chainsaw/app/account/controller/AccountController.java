package de.chainsaw.app.account.controller;

import static de.chainsaw.app.account.mapper.AccountMapper.MAPPER;

import de.chainsaw.app.account.dto.AccountDto;
import de.chainsaw.app.account.service.AccountService;
import de.chainsaw.app.account.exception.BadRequestException;
import de.chainsaw.app.account.exception.NotFoundException;
import de.chainsaw.app.account.model.Account;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
public class AccountController {

  private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

  private AccountService service;

  @Autowired
  public AccountController(AccountService service) {
    this.service = service;
  }

  @ApiOperation(
      value = "get all accounts",
      nickname = "findAll"
  )
  @GetMapping(value = "/accounts")
  public List<AccountDto> findAll() {
    return MAPPER.map(service.findAll());
  }

  @ApiOperation(
      value = "create account with given id",
      nickname = "create"
  )
  @PostMapping("/account/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void create(
      @PathVariable("id") Long id,
      @RequestBody @Valid AccountDto payload) {
    final Account account = MAPPER.map(payload);
    account.setId(id);
    service.save(account);
  }

  @ApiOperation(
      value = "get account by id",
      nickname = "findById"
  )
  @GetMapping("/account/{id}")
  public AccountDto findById(
      @PathVariable("id") Long id) {
    return MAPPER.map(service.findById(id));
  }


  @ExceptionHandler({
      BadRequestException.class,
      MethodArgumentNotValidException.class
  })
  public ResponseEntity<Object> handleBadRequestException(
      Exception ex, WebRequest request) {
    LOGGER.error("Error on service call.", ex);
    return new ResponseEntity<>(
        "Access denied message here", new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({
      NotFoundException.class,
  })
  public ResponseEntity<Object> handleNotFoundException(
      Exception ex, WebRequest request) {
    LOGGER.error("Error on service call.", ex);
    return new ResponseEntity<>(
        "Access denied message here", new HttpHeaders(), HttpStatus.NOT_FOUND);
  }

}
