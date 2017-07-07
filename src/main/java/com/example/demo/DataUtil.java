/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author Moieen
 */
public class DataUtil {
    
    public DataUtil(){
        
    }
    public static <T> void addAll(Collection<T> collection, Iterator<T> iterator) {
    while (iterator.hasNext()) {
        collection.add(iterator.next());
    }
}
    
}
