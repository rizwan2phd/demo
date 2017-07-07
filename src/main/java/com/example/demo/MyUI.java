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
    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout vl = new VerticalLayout();
        
        TextField tf = new TextField("Enter id");
        TextField tf1 = new TextField("Enter name");
        Button save = new Button("Save");
        save.addClickListener(e -> {
            studentRepo.save(new Student(Long.parseLong(tf.getValue()), tf1.getValue()));
                    });
        //studentRepo.save(new Student(1l, "rizwan"));
        Button b = new Button("count");
        
        b.addClickListener(e -> {
            Notification.show(studentRepo.count() + "");
        });
                
        Button update = new Button("update 1");
        update.addClickListener(e -> {
        studentRepo.save(new Student(1l, "Rizwan"));
        });
        vl.addComponents(tf, tf1, save, b, update);
        setContent(vl);
    }
    
    
}
