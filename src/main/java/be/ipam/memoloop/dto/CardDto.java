package be.ipam.memoloop.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link be.ipam.memoloop.model.Card}
 */
@Value
public class CardDto implements Serializable {
    Long id;
    String word;
    String translation;
}