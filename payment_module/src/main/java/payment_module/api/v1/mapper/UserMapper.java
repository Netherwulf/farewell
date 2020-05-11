package payment_module.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import payment_module.models.User;

@Mapper
public interface UserMapper {
    UserlMapper INSTANCE = Mappers.getMapper(UserMapper.class);
