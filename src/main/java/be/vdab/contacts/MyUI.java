package be.vdab.contacts;

import javax.servlet.annotation.WebServlet;

import be.vdab.contacts.domain.Address;
import be.vdab.contacts.domain.Contact;
import be.vdab.contacts.domain.Gender;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Date;

@Title("Contacts")
@Theme("mytheme")
@Widgetset("be.vdab.contacts.MyAppWidgetset")
public class MyUI extends UI {

    VerticalLayout layout = new VerticalLayout();

        HorizontalLayout topBar = new HorizontalLayout();

            Button leftButton = new Button("Contacts");
            Button middleButton = new Button("Registration");
            Label fillerLabel = new Label("");
            Button rightButton = new Button("Login");

        HorizontalLayout bottom = new HorizontalLayout();

            Panel leftBottomPanel = new Panel("Menu");
                VerticalLayout leftPanelLayout = new VerticalLayout();

                    Tree leftPanelTree = new Tree();

            Panel rightBottomPanel = new Panel("Content");
                VerticalLayout rightPanelLayout = new VerticalLayout();

                    Label rightPanelText = new Label("");
                    Table rightPanelTable = new Table();
                    Button addAContact = new Button("Add");
                        Panel form = new Panel("New contact");
                            FormLayout formLayout = new FormLayout();

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        setContent(layout);

        layout.setSizeFull();

        leftButton.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        middleButton.setStyleName(ValoTheme.BUTTON_BORDERLESS); // == setStyleName("borderless")
        rightButton.setStyleName(ValoTheme.BUTTON_BORDERLESS);

        topBar.addComponent(leftButton);
        topBar.addComponent(fillerLabel);
        topBar.addComponent(middleButton);
        topBar.addComponent(rightButton);

        topBar.setWidth(100, Unit.PERCENTAGE); //Anders blijft de horizontal layout enkel net de size dat hij nodig heeft om zen compononents te tonen.
        topBar.setExpandRatio(fillerLabel, 1f);

        leftPanelLayout.addComponent(leftPanelTree);

        rightPanelLayout.addComponent(rightPanelText);
        rightPanelLayout.addComponent(rightPanelTable);

        leftBottomPanel.setContent(leftPanelLayout);
        rightBottomPanel.setContent(rightPanelLayout);

        leftBottomPanel.setSizeFull();
        rightBottomPanel.setSizeFull();

        bottom.setSizeFull();
        bottom.addComponent(leftBottomPanel);
        bottom.addComponent(rightBottomPanel);

        bottom.setExpandRatio(rightBottomPanel, 0.85f); // moet na de component te adden.
        bottom.setExpandRatio(leftBottomPanel, 0.15f); // 1f == 1 bij float , rest moet wel sowieso f hebben

        leftButton.addClickListener(event -> rightPanelText.setCaption("Thank you for clicking the left button"));
        middleButton.addClickListener(event -> rightPanelText.setCaption("Thank you for clicking the middle button"));
        rightButton.addClickListener(event -> Notification.show("Error",
                "I'm sorry, this functionality has not been included yet.", Notification.Type.ERROR_MESSAGE));

        addAContact.addClickListener(event -> rightPanelLayout.replaceComponent(rightPanelTable, form));
        addAContact.setDisableOnClick(true);
        rightPanelLayout.setComponentAlignment(rightPanelTable, Alignment.MIDDLE_CENTER);
        Contact contact1 = new Contact();
        final BeanFieldGroup<Contact> binder = new BeanFieldGroup<>(Contact.class);
        binder.setItemDataSource(contact1);
        formLayout.addComponent(binder.buildAndBind("First Name", "firstName"));
        formLayout.addComponent(binder.buildAndBind("Last Name", "lastName"));
        formLayout.addComponent(binder.buildAndBind("Gender", "gender", ComboBox.class));
        /*layout.addComponent(binder.buildAndBind("Birth Day", "birthDay"));*/
        formLayout.addComponent(binder.buildAndBind("City", "address.city"));
        binder.setBuffered(true);
        formLayout.addComponent(new Button("OK", event -> {
            formLayout.forEach(c -> {
                if (c instanceof AbstractTextField){
                    AbstractTextField a = (AbstractTextField) c;
                    a.setValidationVisible(false);
                }
            });
            try {
                binder.commit();
                binder.clear();
                rightPanelLayout.replaceComponent(form,rightPanelTable);
                addAContact.setEnabled(true);
            } catch (FieldGroup.CommitException e) {
                formLayout.forEach(c -> {
                    if (c instanceof AbstractTextField){
                        AbstractTextField a = (AbstractTextField) c;
                        a.setValidationVisible(true);
                    }
                });
            }
        }));
        form.setSizeUndefined();
        form.setContent(formLayout);
        formLayout.setMargin(true);
        rightPanelLayout.addComponent(addAContact);


        formLayout.forEach(c -> {
            if (c instanceof AbstractTextField){
                AbstractTextField a = (AbstractTextField) c; // Nu doen we voor elke component in de formlayout dit.
                a.setNullRepresentation("");
                a.setValidationVisible(false);
            }
        });



        layout.addComponent(topBar);
        layout.addComponent(bottom);
        layout.setExpandRatio(bottom,1);
        // probeert dan de bottom 100% van de ruimte te geven, of zoveel mogelijk ervan sinds de topbar toch nog nog minimum de grootte van zijn buttons nodig heeft.

    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
