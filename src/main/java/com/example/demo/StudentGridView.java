/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

import com.vaadin.data.Binder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.components.grid.HeaderCell;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.themes.ValoTheme;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Chaudhary
 */

@UIScope
@SpringComponent

public class StudentGridView extends HorizontalLayout {

    private FormLayout form;
    private TextField name;
    private TextField id;
    private Button save;
    private Button clear;
    private Student student;
    private Grid<Student> grid;
    private List<Student> list = new ArrayList<>();

    @Autowired(required = true)
    private StudentRepo studentRepo;
    private Binder<Student> binder;

    public StudentGridView() {

        binder = new Binder<>(Student.class);

        form = new FormLayout();
        form.setCaption("<h3>Enter Customer Detail...</h3>");
        form.setCaptionAsHtml(true);
        form.setSizeUndefined();

        name = new TextField("Name :");
        name.setRequiredIndicatorVisible(true);
        name.setIcon(VaadinIcons.USER);
        name.setWidth("50%");
        name.setPlaceholder("Name...");

        id = new TextField();
        id.setRequiredIndicatorVisible(true);
        id.setCaption("Id :");
        id.setWidth("50%");
        id.setIcon(VaadinIcons.PHONE);
        id.setPlaceholder("Id...");
        
        
        
        
        save = new Button("Save");
        save.setStyleName(ValoTheme.BUTTON_SMALL);
        save.setDescription("This Button saves and Update Customer Detail");
        save.addClickListener((event) -> {
            student=new Student(Long.parseLong(id.getValue()), name.getValue());
            list.add(student);
            studentRepo.save(student);

        });

        clear = new Button("Clear");
        clear.setStyleName(ValoTheme.BUTTON_SMALL);
        clear.setDescription("Clear all Field to their Default Values(Empty)");
        clear.addClickListener(e -> {
            clear();

        });

        form.addComponents(id, name, new HorizontalLayout(clear, save));
        form.setMargin(true);
//        form.setSizeFull();

        grid = new Grid(Student.class);
        grid.setCaption("<h3>Enter Customer Detail...</h3>");
        grid.setCaptionAsHtml(true);
        grid.setItems(list);
        grid.setStyleName(ValoTheme.LAYOUT_WELL);
        grid.setWidth(this.getWidth(), Unit.PERCENTAGE);

        HeaderRow filterRow = grid.appendHeaderRow();
        HeaderCell filternamecell = filterRow.getCell("name");
        TextField filter = new TextField();
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.setPlaceholder("Filter name...");
        filternamecell.setComponent(filter);
//        grid.setHeight();
        List names = new ArrayList();
        filter.addValueChangeListener(e -> {

            names.clear();
            for (int i = 0; i < list.size(); i++) {

                names.add(list.get(i).getName());
            }
            Notification.show(names.toString());

        });

        addComponents(grid, form);
        setSizeFull();
        setExpandRatio(grid,1);

    }

    ///Clear all Field to their Default values mean Empty....
    void clear() {
        name.setValue("");
        id.setValue("");

    }

}
