/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Moieen
 */
public class DataUtil {
    List<Student> list;
    public DataUtil(){
        
    }
    
        //Return list of student from database
     public List<Student> getUpdate(StudentRepo studentRepo){
                list=studentRepo.findAll();
                
        return list;
    }
    
    public List<Student> findAll(int offset, int limit){
        
            
        
        return   list.subList(offset, (offset + limit));
        
    }
    
    public int count() {
        return list.size();
    }
    
}
