package br.com.caelum.tubaina;

import java.util.ArrayList;
import java.util.List;

public class TubainaSyntaxErrorsException extends TubainaException {
    
    List<TubainaException> exceptions;
    
    public TubainaSyntaxErrorsException() {
        super();
        exceptions = new ArrayList<TubainaException>();
    }
    
    public TubainaSyntaxErrorsException(String message, List<TubainaException> exceptions) {
        super(message);
        this.exceptions = new ArrayList<TubainaException>();
        for (TubainaException exception : exceptions) {
            addError(exception);
        }
    }
    
    public TubainaSyntaxErrorsException(String message, TubainaException e) {
        super(message);
        this.exceptions = new ArrayList<TubainaException>();
        exceptions.add(e);
    }

    public void addError(TubainaException e) {
        exceptions.add(e);
    }
    
    public void addErrors(List<TubainaException> exceptions) {
        for (TubainaException exception : exceptions) {
            this.addError(exception);
        }
    }
    
    @Override
    public void printStackTrace() {
        super.printStackTrace();
        for (Exception e : exceptions) {
            e.printStackTrace();
        }
    }
    
    

}
