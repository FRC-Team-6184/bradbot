package frc.robot.options;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GameControllerOption {
    private final SendableChooser<String> option = new SendableChooser<>();
    public static final String XBOX = "XBox";
    public static final String PS5 = "Playstation 5";
    public static final String PS4 = "Playstation 4";

    public GameControllerOption() {
        option.setDefaultOption(XBOX, XBOX);
        option.addOption(PS5, PS5);
        option.addOption(PS4, PS4);
        SmartDashboard.putData("Controller Type", option);
    }

    public String getSelected() {
        return option.getSelected();
    }
}
