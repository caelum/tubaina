package br.com.caelum.tubaina;

import java.util.ArrayList;
import java.util.List;

public class TubainaSyntaxErrorsException extends RuntimeException {
    
    List<Exception> exceptions;
    
    public TubainaSyntaxErrorsException() {
        super();
        exceptions = new ArrayList<Exception>();
    }
    
    public TubainaSyntaxErrorsException(String message, List<Exception> exceptions) {
        super(message);
        this.exceptions = new ArrayList<Exception>();
        for (Exception exception : exceptions) {
            addError(exception);
        }
    }
    
    public TubainaSyntaxErrorsException(String message, TubainaSyntaxErrorsException e) {
        super(message);
        this.exceptions = new ArrayList<Exception>();
        exceptions.add(e);
    }

    public void addError(Exception t) {
        exceptions.add(t);
    }
    
    public void addErrors(List<Exception> exceptions) {
        for (Exception throwable : exceptions) {
            this.addError(throwable);
        }
    }
    
    @Override
    public void printStackTrace() {
        super.printStackTrace();
        for (Exception t : exceptions) {
            t.printStackTrace();
        }
    }
    
    

}
