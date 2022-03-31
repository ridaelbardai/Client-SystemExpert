package fstm.ilisi.model.service;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    
public class CurrentDateTimeExample1 {    
  public  String doit() {    
   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
   LocalDateTime now = LocalDateTime.now();  
   return dtf.format(now);  
  }    
  
} 
