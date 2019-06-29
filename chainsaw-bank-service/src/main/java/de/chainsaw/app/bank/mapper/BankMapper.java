package de.chainsaw.app.bank.mapper;

import de.chainsaw.app.bank.dto.AccountDto;
import de.chainsaw.app.bank.dto.BankDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
@SuppressWarnings("squid:S1214")
public interface BankMapper {

  BankMapper MAPPER = Mappers.getMapper(BankMapper.class);

  BankDto map(AccountDto accountDto);

}
