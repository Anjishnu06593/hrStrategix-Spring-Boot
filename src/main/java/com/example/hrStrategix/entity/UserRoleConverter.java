package com.example.hrStrategix.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class UserRoleConverter implements AttributeConverter<UserRole, String> {
    @Override
    public String convertToDatabaseColumn(UserRole attribute) {
        if (attribute == null) return null;
        return attribute.name().toLowerCase();
    }

    @Override
    public UserRole convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            // Map DB 'admin' -> UserRole.ADMIN by upper-casing
            return UserRole.valueOf(dbData.toUpperCase());
        } catch (IllegalArgumentException ex) {
            // Optional: fallback or throw a clearer exception
            throw new IllegalArgumentException("Unknown role value in DB: " + dbData, ex);
        }
    }
}
