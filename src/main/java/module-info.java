module l2dsi2.firas.miniprojetfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;


    opens l2dsi2.firas.miniprojetfx to javafx.fxml;
    opens l2dsi2.firas.miniprojetfx.Controller to javafx.fxml;

    exports l2dsi2.firas.miniprojetfx;
    exports l2dsi2.firas.miniprojetfx.Controller;

}