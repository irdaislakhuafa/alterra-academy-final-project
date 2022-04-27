package com.irdaislakhuafa.alterraacademyfinalproject.services.utils;

import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.BaseEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogMessage {
    public static void logSave(BaseEntity entity) {
        log.info("Saving new " + entity.getClass().getSimpleName());
    }

    public static void logSuccessSave(BaseEntity entity) {
        log.info("Success saving new " + entity.getClass().getSimpleName());
    }

    public static void logUpdate(BaseEntity entity) {
        log.info("Updating " + entity.getClass().getSimpleName() + " with id: " + entity.getId());
    }

    public static void logSuccessUpdate(BaseEntity entity) {
        log.info("Success updating " + entity.getClass().getSimpleName() + " with id: " + entity.getId());
    }

    public static void logPrepareFindById(BaseEntity entity) {
        log.info("Preparing get " + entity.getClass().getSimpleName() + " with id: " + entity.getId());
    }

    public static void logEntityNotFound(BaseEntity entity) {
        log.info(entity.getClass().getSimpleName() + " with id: " + entity.getId() + " not found");
    }

    public static void logEntityFound(BaseEntity entity) {
        log.info("Found " + entity.getClass().getSimpleName() + " with id: " + entity.getId());
    }

    public static void logFindAll(BaseEntity entity) {
        log.info("Find all " + entity.getClass().getSimpleName());
    }

    public static void logSuccessFindAll(BaseEntity entity) {
        log.info("Success get all " + entity.getClass().getSimpleName());
    }

    public static void logSuccessDelete(BaseEntity entity) {
        log.info("Success delete address id: " + entity.getId());
    }

    public static void logMapDtoToEntity(BaseEntity entity) {
        log.info("Mapping " + entity.getClass().getSimpleName() + " dto to entity");
    }

    public static void logSuccessMapDtoToEntity(BaseEntity entity) {
        log.info("Success mapping " + entity.getClass().getSimpleName() + " dto to entity");
    }
}
