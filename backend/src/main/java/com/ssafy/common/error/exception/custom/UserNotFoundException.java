package com.ssafy.common.error.exception.custom;

import com.ssafy.common.error.exception.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(String email) {
        super(email + " user is not found");
    }
}
