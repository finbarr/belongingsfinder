package com.belongingsfinder.api.model.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.belongingsfinder.api.annotations.ValidBelonging;
import com.belongingsfinder.api.model.BelongingModel;

public class BelongingValidator implements ConstraintValidator<ValidBelonging, BelongingModel> {

	@Override
	public void initialize(ValidBelonging arg0) {
	}

	@Override
	public boolean isValid(BelongingModel arg0, ConstraintValidatorContext arg1) {
		switch (arg0.getType()) {
		case LOST:
			return arg0.getDescription() != null && arg0.getDescription().length() > 0;
		case FOUND:
			return arg0.getImages() != null && !arg0.getImages().isEmpty();
		case ALL:
		default:
			return false;
		}
	}

}