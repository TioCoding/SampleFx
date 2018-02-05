package pe.com.sample.bean.enums;

public enum ScreenEnum {

    MAIN("/fxml/main/Main.fxml"),
    MENU("/fxml/menu/SideMenu.fxml"),
    COMPARATION("/fxml/ui/Comparator.fxml");

    private String path;

    ScreenEnum(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
