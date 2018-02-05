package pe.com.sample;

import com.jfoenix.controls.JFXDecorator;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import pe.com.sample.bean.enums.ScreenEnum;
import pe.com.sample.util.FXMLFactoryUtils;

public class MainApp extends Application{

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage){
        JFXDecorator decorator = new JFXDecorator(stage, FXMLFactoryUtils.getView(ScreenEnum.MAIN));
        decorator.setCustomMaximize(true);
        decorator.setText("Company");
        MaterialDesignIconView iconApp = new MaterialDesignIconView(MaterialDesignIcon.ACCESS_POINT);
        iconApp.setSize("2em");
        iconApp.setFill(Color.WHITE);
        decorator.setGraphic(iconApp);

        Rectangle2D bounds = Screen.getScreens().get(0).getBounds();
        double width = bounds.getWidth() / 2.5;
        double height = bounds.getHeight() / 1.35;

        Scene scene = new Scene(decorator, width, height);
        final ObservableList<String> stylesheets = scene.getStylesheets();
        stylesheets.addAll(MainApp.class.getResource("/css/jfoenix-fonts.css").toExternalForm(),
                MainApp.class.getResource("/css/jfoenix-design.css").toExternalForm(),
                MainApp.class.getResource("/css/style-main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
