/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Muhammad Rizwan
 */
@SpringUI
public class MyUI extends UI{

    @Autowired
    private  StudentRepo studentRepo;
    @Autowired
    private StudentGridView studentGridView;
    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout vl = new VerticalLayout();
        
        vl.addComponents(studentGridView);
        setContent(vl);
    }
    
    
}
