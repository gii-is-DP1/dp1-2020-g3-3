package org.springframework.samples.aerolineasAAAFC.deprecated;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.samples.aerolineasAAAFC.model.User;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> { 
  
  @Override
  public void initialize(PasswordMatches constraintAnnotation) {       
  }
  @Override
  public boolean isValid(Object obj, ConstraintValidatorContext context){   
      User user = (User) obj;
      return false; //user.getPassword().equals(user.getMatchingPassword());    
  }

}
