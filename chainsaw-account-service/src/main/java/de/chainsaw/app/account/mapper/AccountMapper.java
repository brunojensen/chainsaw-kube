package de.chainsaw.app.account.mapper;

import de.chainsaw.app.account.dto.AccountDto;
import de.chainsaw.app.account.model.Account;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
@SuppressWarnings("squid:S1214")
public interface AccountMapper {

  AccountMapper MAPPER = Mappers.getMapper(AccountMapper.class);

  List<AccountDto> map(List<Account> entities);

  AccountDto map(Account entity);

  Account map(AccountDto dto);
}
