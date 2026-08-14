package com.guymontag.eventapi.util.validator;

import com.guymontag.eventapi.exception.ApiException;
import com.guymontag.eventapi.dto.EventDTOInput;

import java.util.function.Predicate;
import java.util.function.Supplier;

public record EventConstraint(Predicate<EventDTOInput> rule, Supplier<ApiException> exception) {
}
