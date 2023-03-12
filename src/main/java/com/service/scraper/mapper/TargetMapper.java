package com.service.scraper.mapper;

import java.sql.Timestamp;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.service.scraper.dto.TargetDTO;
import com.service.scraper.entity.TargetEntity;
import com.service.scraper.util.Constant;

@Mapper(componentModel = "spring", imports = { Timestamp.class, UUID.class, Constant.class})
public interface TargetMapper {

    @Mapping(target = "createdAt", expression = "java(new Timestamp(System.currentTimeMillis()))")
    @Mapping(target = "updatedAt", expression = "java(new Timestamp(System.currentTimeMillis()))")
    @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
    @Mapping(target = "state", constant = "ACTIVE")
    TargetEntity toNewEntity(TargetDTO targetDTO);

    @Mapping(target = "updatedAt", expression = "java(new Timestamp(System.currentTimeMillis()))")
    @Mapping(target = "state", source = "state")
    TargetEntity updateEntity(@MappingTarget TargetEntity targetEntity, String state);
}
