package de.chainsaw.app.bank.controller;

import de.chainsaw.app.bank.exception.InternalServerException;
import de.chainsaw.app.bank.mapper.BankMapper;
import de.chainsaw.app.bank.dto.BankDto;
import de.chainsaw.app.bank.model.IBAN;
import de.chainsaw.app.bank.service.foreign.AccountForeignService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
public class BankController {

  private static final Logger LOGGER = LoggerFactory.getLogger(BankController.class);

  private AccountForeignService accountForeignService;

  @Autowired
  public BankController(AccountForeignService accountForeignService) {
    this.accountForeignService = accountForeignService;
  }

  @ApiOperation(
      value = "get bank account by id",
      nickname = "findById"
  )
  @GetMapping("/bank/{id}")
  public BankDto findById(@PathVariable("id") Long id) {
    final BankDto bankDto = BankMapper.MAPPER.map(accountForeignService.findById(id));
    bankDto.setIban(IBAN.resolve(id));
    return bankDto;
  }

  @ExceptionHandler({
      InternalServerException.class,
  })
  public ResponseEntity<Object> handleInternalServiceErrorException(
      Exception ex, WebRequest request) {
    LOGGER.error("Error on service call.", ex);
    return new ResponseEntity<>(
        "Access denied message here", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
