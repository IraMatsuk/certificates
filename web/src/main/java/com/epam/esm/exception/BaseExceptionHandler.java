package com.epam.esm.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class BaseExceptionHandler {
    private static final String NOT_FOUND_MESSAGE = "Requested resource not found (";
    private static final String NO_DATA_FOUND_MESSAGE = "No data was provided (";
    private static final String REQUEST_FAILED = "Request has failed (";
    private static final String CLOSING_BRACE = ")";
    private static final String BAD_REQUEST_MESSAGE = "provided data was incorrect)";
    private static final String MISSING_REQUEST_PARAMETERS_MESSAGE = "required parameters are missing)";

    /**
     * Resource not found response entity.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler({NoHandlerFoundException.class, HttpClientErrorException.NotFound.class})
    public ResponseEntity<ResponseErrorEntity> resourceNotFound(NoHandlerFoundException exception) {
        return new ResponseEntity<>(new ResponseErrorEntity(NOT_FOUND.value(), HttpClientErrorException.NotFound.class,
                NOT_FOUND_MESSAGE + exception.getMessage() + CLOSING_BRACE), NOT_FOUND);
    }

    /**
     * Data not found response entity.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ResponseErrorEntity> dataNotFound(NoDataFoundException exception) {
        return new ResponseEntity<>(new ResponseErrorEntity(NO_CONTENT.value(), exception.getResourceClass(),
                NO_DATA_FOUND_MESSAGE + exception.getParameters() + CLOSING_BRACE), NO_CONTENT);
    }

    /**
     * Request failed response error entity.
     *
     * @param exception the exception
     * @return the response error entity
     */
    @ExceptionHandler({BadRequestException.class, HttpClientErrorException.BadRequest.class})
    public ResponseEntity<ResponseErrorEntity> requestFailed(BadRequestException exception) {
        return new ResponseEntity<>(new ResponseErrorEntity(BAD_REQUEST.value(), exception.getResourceClass(),
                REQUEST_FAILED + BAD_REQUEST_MESSAGE), BAD_REQUEST);
    }

    /**
     * Request not valid response entity.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class,
            MismatchedInputException.class, JsonParseException.class})
    public ResponseEntity<ResponseErrorEntity> requestNotValid(Exception exception) {
        return new ResponseEntity<>(new ResponseErrorEntity(BAD_REQUEST.value(),
                exception.getClass(), REQUEST_FAILED + BAD_REQUEST_MESSAGE,
                exception.getMessage()), BAD_REQUEST);
    }

    /**
     * Request missing parameters response entity.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseErrorEntity> requestMissingParameters(MissingRequestValueException exception) {
        return new ResponseEntity<>(new ResponseErrorEntity(BAD_REQUEST.value(),
                MissingServletRequestParameterException.class, REQUEST_FAILED +
                MISSING_REQUEST_PARAMETERS_MESSAGE, exception.getMessage()), BAD_REQUEST);
    }
}