package com.example.buysell.Annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneConstraintValidator implements ConstraintValidator<Phone, String> {
    @Override
    public boolean isValid(String phoneField, ConstraintValidatorContext cxt) {
        if (phoneField == null)
            return false;
        return (phoneField.matches("[0-9()-\\.]*") && phoneField.length()>=10) ||
                (phoneField.matches("[0-9()-\\.]*") && phoneField.length()==11) ;
    }
}
