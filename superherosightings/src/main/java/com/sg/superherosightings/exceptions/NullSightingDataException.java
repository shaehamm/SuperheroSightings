/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.exceptions;

/**
 * @author codedchai
 */
public class NullSightingDataException extends Exception {

    public NullSightingDataException(String message) {
        super(message);
    }

    public NullSightingDataException(String message, Throwable innerException) {
        super(message, innerException);
    }

}
