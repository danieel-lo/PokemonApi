package com.example.PokemonApi.exceptions;

import com.example.PokemonApi.dtos.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> manejarValidaciones(MethodArgumentNotValidException ex){
        List<String> detalles= new ArrayList<>();
        for(FieldError error: ex.getBindingResult().getFieldErrors()){
            detalles.add(error.getField() + ":" + error.getDefaultMessage());
        }
        ErrorDTO errorDto=new ErrorDTO("Error de validacion en los datos enviados",detalles, LocalDateTime.now());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> manejarNoEncontrado(ResourceNotFoundException ex) {
        ErrorDTO errorDto = new ErrorDTO(
                ex.getMessage(),
                List.of(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> manejarErrorGeneral(Exception ex){
        ErrorDTO errorDto = new ErrorDTO(
                "Ocurrio un error interno en el servidor",
                List.of(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
