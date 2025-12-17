package com.example.projectCommunity.models.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter class that serializes the objects containing the notification metadata
 * in order to store it as text id DB, and deserializes the metadata when retrieved from the DB.
 * */
@Converter
public class NotificationMetadataConverter
        implements AttributeConverter<NotificationMetadata, String> {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Converts the {@link NotificationMetadata} object into string before
     * storing it in DB.
     *
     * @param attribute the object containing the metadata to be stored
     * @return the string to be stored in DB
     * */
    @Override
    public String convertToDatabaseColumn(NotificationMetadata attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts the string stored in DB into {@link NotificationMetadata} when the notification is retrieved.
     *
     * @param dbData the string to be converted
     * @return the {@link NotificationMetadata} object containing the metadata information
     * */
    @Override
    public NotificationMetadata convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData, NotificationMetadata.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

