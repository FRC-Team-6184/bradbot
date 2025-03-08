package frc.robot.options;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DrivetrainOption {
    private final SendableChooser<String> option = new SendableChooser<>();

    public DrivetrainOption() {
        option.setDefaultOption("Tank Drive", "tankDrive");
        option.addOption("Arcade Drive", "arcadeDrive");
        option.addOption("Curvature Drive", "curvatureDrive");
        SmartDashboard.putData("Drivetrains", option);
    }

    public String getSelected() {
        return option.getSelected();
    }
}
