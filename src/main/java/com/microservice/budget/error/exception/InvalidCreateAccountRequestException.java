package com.microservice.budget.error.exception;

import com.microservice.budget.domain.Account;

/** RuntimeException unchecked si bloquea el flujo
 *
  */

public class InvalidCreateAccountRequestException extends RuntimeException{
    public InvalidCreateAccountRequestException(Account account){
        super("Invalid Request" + account);
    }
}
