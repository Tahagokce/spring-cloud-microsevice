package com.verseup.accountservice.modal.error;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ErrorFields implements Serializable {
    private String objectName;
    private String fieldName;
    private String message;
    private String passedArgument;
}
