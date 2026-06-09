package be.ipam.memoloop.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for creating a new Card (without id, which is auto-generated)
 */
@Value
public class CreateCardDto implements Serializable {
    String word;
    String translation;
}

