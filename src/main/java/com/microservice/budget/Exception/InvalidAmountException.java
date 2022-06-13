package com.microservice.budget.Exception;
/*
Throwable
 heredan: Error y Exception
                    ->RuntimeException  (Arroaja de la operaciona normal de la vm)
                       -> InvalidAmountException
 operacion Normal? -> caso contraio tendria que llena de catchs en.
  todo ya que seria molestoso
 */
public class InvalidAmountException extends RuntimeException{
}
