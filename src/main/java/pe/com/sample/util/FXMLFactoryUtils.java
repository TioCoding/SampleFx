package pe.com.sample.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import pe.com.sample.bean.enums.ScreenEnum;

import java.util.Hashtable;

public class FXMLFactoryUtils {

    private static Hashtable<ScreenEnum,Parent> instances = new Hashtable<>();

    public static Parent getView(ScreenEnum screenEnum){
        try{
            Parent instance = instances.get(screenEnum);
            if(instance==null){
                instance = FXMLLoader.load(FXMLFactoryUtils.class.getResource(screenEnum.getPath()));
                instances.put(screenEnum,instance);
            }
            return instance;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
