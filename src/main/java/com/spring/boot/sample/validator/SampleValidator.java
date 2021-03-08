package com.spring.boot.sample.validator;

import com.spring.boot.sample.SampleVO;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SampleValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return SampleVO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SampleVO sampleVO = (SampleVO) target;

        if (ObjectUtils.isEmpty(sampleVO.getEmail())) {
            errors.rejectValue("email","email.empty");
        }
    }
}
