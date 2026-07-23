package com.guymontag.eventapi.util.validator;

import com.guymontag.eventapi.exception.ApiException;
import com.guymontag.eventapi.model.dto.EventDTO;

import java.util.function.Predicate;
import java.util.function.Supplier;

public record EventConstraint(Predicate<EventDTO> rule, Supplier<ApiException> exception) {
}
