package hello;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;

@Route
public class MainView extends HorizontalLayout {

    private final StudentRepository repo;

    private final StudentEditor editor;

    final Grid<Student> grid;

    final TextField filter;

    private final Button addNewBtn;

    public MainView(StudentRepository repo, StudentEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>(Student.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("New student", VaadinIcon.PLUS.create());

        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        VerticalLayout menu = new VerticalLayout(actions, editor, grid);
        add(menu);
        grid.setHeightByRows(true);

        grid.setColumns("id", "firstName", "lastName", "email");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);
        grid.getColumnByKey("firstName").setWidth("250px").setFlexGrow(0);
        grid.getColumnByKey("lastName").setWidth("250px").setFlexGrow(0);
        grid.setWidth("900px");
        filter.setPlaceholder("Filter by last name");

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listStudents(e.getValue()));

        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editStudent(e.getValue());
        });

        addNewBtn.addClickListener(e -> editor.editStudent(new Student("", "", "")));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listStudents(filter.getValue());
        });

        listStudents(null);
    }


    void listStudents(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        } else {
            grid.setItems(repo.findByLastNameStartsWithIgnoreCase(filterText));
        }
    }

}