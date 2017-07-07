/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringComponent;
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
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Chaudhary
 */
@UIScope
@SpringComponent

public class StudentGridView extends HorizontalLayout {

    private FormLayout form;
    private TextField name;
    private TextField email;
    private Button save;
    private Button clear;
    private Student student;
    private Grid<Student> grid;
    private List<Student> list;
    

    private Binder<Student> binder;

    @Autowired(required = true)
    private StudentRepo studentRepo;

    

    public StudentGridView() {
        grid = new Grid<>();
        name = new TextField("Name :");
        email = new TextField();
        
        
        grid.setDataProvider(
        
                (sortOrders, offset, limit) ->
                        
            studentRepo.findAll(new PageRequest(offset/limit, limit)).getContent().stream(),
                
            () -> (int)studentRepo.count()
        );
//      DataProvider<Student, Void> dataProvider= DataProvider.fromCallbacks(
//                
//                query -> {
//                    // The index of the first item to load
//                    int offset = query.getOffset();
//
//                    // The number of items to load
//                    int limit = query.getLimit();
//                        
//                   return studentRepo.findAll(new PageRequest(offset, limit)).getContent().stream();
//                    
//                  },
//                  // Second callback fetches the number of items for a query
//                  query -> studentRepo.count()
//                );
//        
//        grid.setDataProvider(dataProvider);

        student = new Student();
        binder = new Binder<>(Student.class);
        binder.bindInstanceFields(this);
        binder.readBean(student);

        form = new FormLayout();
        form.setCaption("<h3>Enter Customer Detail...</h3>");
        form.setCaptionAsHtml(true);

        name.setRequiredIndicatorVisible(true);
        name.setIcon(VaadinIcons.USER);
//        name.setWidth("50%");
        name.setPlaceholder("Name...");

        email.setRequiredIndicatorVisible(true);
        email.setCaption("Email :");
//        email.setWidth("50%");
        email.setIcon(VaadinIcons.MAILBOX);
        email.setPlaceholder("Email...");

        save = new Button("Save");
        save.setStyleName(ValoTheme.BUTTON_SMALL);
        save.setDescription("This Button saves and Update Customer Detail");

        save.addClickListener((event) -> {

            try {
                binder.writeBean(student);

            } catch (ValidationException ex) {
                ///TODo something...
            }

            studentRepo.save(student);
            clear();

        });

        clear = new Button("Clear");
        clear.setStyleName(ValoTheme.BUTTON_SMALL);
        clear.setDescription("Clear all Field to their Default Values(Empty)");
        clear.addClickListener(e -> {
            clear();
            Notification.show("Clear Done", Notification.Type.TRAY_NOTIFICATION);
        });

        form.addComponents(name, email, new HorizontalLayout(clear, save));

        grid.setCaption("<h3>Enter Customer Detail...</h3>");
        grid.setCaptionAsHtml(true);
        grid.setStyleName(ValoTheme.LAYOUT_WELL);
//        grid.setWidth(this.getWidth(), Unit.PERCENTAGE);
        TextField editname = new TextField();
        grid.getEditor().setEnabled(true);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addColumn(Student::getId)
                .setCaption("Id")
                .setId("id");
        grid.addColumn(Student::getName)
                .setEditorComponent(new TextField(), Student::setName)
                .setCaption("Name")
                .setId("name");
        grid.addColumn(Student::getEmail)
                .setEditorComponent(editname, Student::setEmail)
                .setCaption("Email")
                .setId("email");

        grid.getEditor().addSaveListener(e -> {
            student = e.getBean();
            studentRepo.save(student);
        });
//        grid.setWidth("50%");

        HeaderRow filterRow = grid.appendHeaderRow();
        HeaderCell filternamecell = filterRow.getCell("name");
        TextField filter = new TextField();
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.setPlaceholder("Filter name...");
        filternamecell.setComponent(filter);
        filter.addValueChangeListener(e -> {

        });

        addComponents(grid, form);
        setSizeFull();

    }

    ///Clear all Field to their Default values mean Empty....
    void clear() {
        name.setValue("");
        email.setValue("");

    }

}
