package com.tinyauction.validator;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.tinyauction.dto.ListingDto;

@Component
public class ListingDtoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		System.out.println("clazz = " + clazz.toString()+" equals = "+ ListingDto.class.equals(clazz));
		return ListingDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		System.out.println("Perfoming validation!!!");
		
		
		ListingDto listing = (ListingDto) target;
		if(Objects.isNull(listing.getListingName()))
			errors.rejectValue("listingName","listing.listingName");
		if(listing.getStartPrice()!=null) {
			Pattern pattern = Pattern.compile("^[0-9]+|([0-9]+\\.[0-9]{1,2})");
			Matcher matcher = pattern.matcher(listing.getStartPrice());
			if(!matcher.matches())
				errors.rejectValue("startPrice","listing.startPrice.invalid");
		}else {
			errors.rejectValue("startPrice","listing.startPrice.empty");
		}
		
		

	}

}
