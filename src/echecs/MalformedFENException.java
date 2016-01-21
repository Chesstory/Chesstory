/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echecs;

/**
 *
 * @author samuel
 */
@SuppressWarnings("serial")
public class MalformedFENException extends IllegalArgumentException{
    
    
    MalformedFENException(){
        super();
    }
    
    MalformedFENException(String msg){
        super(msg);
    }
    
    
}