package be.vdab.contacts;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 */
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

    Panel leftBottomPanel = new Panel();
    Panel rightBottomPanel = new Panel();
    VerticalLayout leftPanelLayout = new VerticalLayout();
    VerticalLayout rightPanelLayout = new VerticalLayout();

    Label rightPanelText = new Label("");




    @Override
    protected void init(VaadinRequest vaadinRequest) {

        setContent(layout);

        layout.setSizeFull();


        leftButton.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        middleButton.setStyleName(ValoTheme.BUTTON_BORDERLESS); // == "borderless"
        rightButton.setStyleName(ValoTheme.BUTTON_BORDERLESS);

        topBar.addComponent(leftButton);
        topBar.addComponent(fillerLabel);
        topBar.addComponent(middleButton);
        topBar.addComponent(rightButton);

        topBar.setWidth(100, Unit.PERCENTAGE);
        topBar.setExpandRatio(fillerLabel, 1f);

        leftBottomPanel.setCaption("Menu");
        rightBottomPanel.setCaption("Content");

        rightPanelLayout.addComponent(rightPanelText);
        leftBottomPanel.setContent(leftPanelLayout);
        rightBottomPanel.setContent(rightPanelLayout);

        bottom.setSizeFull();
        bottom.addComponent(leftBottomPanel);
        bottom.addComponent(rightBottomPanel);

        bottom.setExpandRatio(rightBottomPanel, 0.85f);
        bottom.setExpandRatio(leftBottomPanel, 0.15f); // 1f == 1 bij float , rest moet wel sowieso f hebben

        leftButton.addClickListener(event -> rightPanelText.setCaption("Thank you for clicking the left button"));
        middleButton.addClickListener(event -> rightPanelText.setCaption("Thank you for clicking the middle button"));
        rightButton.addClickListener(event -> rightPanelText.setCaption("Thank you for clicking the right button"));

        layout.addComponent(topBar);
        layout.addComponent(bottom);
        layout.setExpandRatio(bottom,1); // probeert dan de bottom 100% van de ruimte te geven ,
        // of zoveel mogelijk ervan sinds de topbar toch nog nog minimum de grote van de buttons nodig heeft.

    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
