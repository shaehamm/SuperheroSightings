/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.exceptions;

/**
 *
 * @author codedchai
 */
public class NullOrganizationDataException extends Exception {
    
    public NullOrganizationDataException(String message) {
        super(message);
    }
    
    public NullOrganizationDataException(String message, Throwable innerException) {
        super(message, innerException);
    }
    
}
