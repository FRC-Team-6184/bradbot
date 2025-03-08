package frc.robot.IO;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.PS5Controller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GameController {
    public static final int DRIVER = 0;

    public enum ControllerTypes {XBOX, PS5, PS4};

    private static SendableChooser<String> dropdown = new SendableChooser<String>();
    private static ControllerTypes selection;
    public static GameController controller;
    private GenericHID internalController;

    public GameController(int port) {
        internalController = new GenericHID(port);
    }

    /**
     * Must be ran before interacting with the controller. Takes selection from
     * SmartDashboard and sets up class for it.
     */
    public static void initController() {
        String selectedFromDashboard = dropdown.getSelected();

        for (ControllerTypes option : ControllerTypes.values()) {
            if (selectedFromDashboard.equals(option.toString())) {
                selection = option;
                controller = new GameController(DRIVER);
                return;
            }
        }

        // If the method gets here, something has gone wrong. Defaulting to Xbox.
        selection = ControllerTypes.XBOX;
        controller = new GameController(DRIVER);
    }

    public static void initSmartboard() {
        for (ControllerTypes option : ControllerTypes.values()) {
            dropdown.addOption(option.toString(), option.toString());
        }
        dropdown.setDefaultOption(ControllerTypes.XBOX.toString(), ControllerTypes.XBOX.toString());
        SmartDashboard.putData(dropdown);
    }

    public double getLeftX() {
        switch (selection) {
            case XBOX:
                return internalController.getRawAxis(XboxController.Axis.kLeftX.value);
            case PS5:
                return internalController.getRawAxis(PS5Controller.Axis.kLeftX.value);
            case PS4:
                return internalController.getRawAxis(PS4Controller.Axis.kLeftX.value);
            default:
                return 0;
        }

    }

    public double getLeftY() {
        switch (selection) {
            case XBOX:
                return internalController.getRawAxis(XboxController.Axis.kLeftY.value);
            case PS5:
                return internalController.getRawAxis(PS5Controller.Axis.kLeftY.value);
            case PS4:
                return internalController.getRawAxis(PS4Controller.Axis.kLeftY.value);
            default:
                return 0;
        }
    }

    public double getRightX() {
        switch (selection) {
            case XBOX:
                return internalController.getRawAxis(XboxController.Axis.kRightX.value);
            case PS5:
                return internalController.getRawAxis(PS5Controller.Axis.kRightX.value);
            case PS4:
                return internalController.getRawAxis(PS4Controller.Axis.kRightX.value);
            default:
                return 0;
        }
    }

    public double getRightY() {
        switch (selection) {
            case XBOX:
                return internalController.getRawAxis(XboxController.Axis.kRightY.value);
            case PS5:
                return internalController.getRawAxis(PS5Controller.Axis.kRightY.value);
            case PS4:
                return internalController.getRawAxis(PS4Controller.Axis.kRightY.value);
            default:
                return 0;
        }
    }

    public double getLeftTrigger() {
        switch (selection) {
            case XBOX:
                return internalController.getRawAxis(XboxController.Axis.kLeftTrigger.value);
            case PS5:
                return internalController.getRawAxis(PS5Controller.Axis.kL2.value);
            case PS4:
                return internalController.getRawAxis(PS4Controller.Axis.kL2.value);
            default:
                return 0;
        }
    }

    public double getRightTrigger() {
        switch (selection) {
            case XBOX:
                return internalController.getRawAxis(XboxController.Axis.kRightTrigger.value);
            case PS5:
                return internalController.getRawAxis(PS5Controller.Axis.kR2.value);
            case PS4:
                return internalController.getRawAxis(PS4Controller.Axis.kR2.value);
            default:
                return 0;
        }
    }

    public boolean getLeftBumper() {
        switch (selection) {
            case XBOX:
                return internalController.getRawButton(XboxController.Button.kLeftBumper.value);

            case PS5:
                return internalController.getRawButton(PS5Controller.Button.kL1.value);

            case PS4:
                return internalController.getRawButton(PS4Controller.Button.kL1.value);

            default:
                return false;
        }
    }

    public boolean getRightBumper() {
        switch (selection) {
            case XBOX:
                return internalController.getRawButton(XboxController.Button.kRightBumper.value);

            case PS5:
                return internalController.getRawButton(PS5Controller.Button.kR1.value);

            case PS4:
                return internalController.getRawButton(PS4Controller.Button.kR1.value);

            default:
                return false;
        }
    }

    public boolean getFaceButtonLeft() {
        switch (selection) {
            case XBOX:
                return internalController.getRawButton(XboxController.Button.kX.value);

            case PS5:
                return internalController.getRawButton(PS5Controller.Button.kSquare.value);

            case PS4:
                return internalController.getRawButton(PS4Controller.Button.kSquare.value);

            default:
                return false;
        }
    }

    public boolean getFaceButtonRight() {
        switch (selection) {
            case XBOX:
                return internalController.getRawButton(XboxController.Button.kB.value);

            case PS5:
                return internalController.getRawButton(PS5Controller.Button.kCircle.value);

            case PS4:
                return internalController.getRawButton(PS4Controller.Button.kCircle.value);

            default:
                return false;
        }
    }

    public boolean getFaceButtonUp() {
        switch (selection) {
            case XBOX:
                return internalController.getRawButton(XboxController.Button.kY.value);

            case PS5:
                return internalController.getRawButton(PS5Controller.Button.kTriangle.value);

            case PS4:
                return internalController.getRawButton(PS4Controller.Button.kTriangle.value);

            default:
                return false;
        }
    }

    public boolean getFaceButtonDown() {
        switch (selection) {
            case XBOX:
                return internalController.getRawButton(XboxController.Button.kA.value);

            case PS5:
                return internalController.getRawButton(PS5Controller.Button.kCross.value);

            case PS4:
                return internalController.getRawButton(PS4Controller.Button.kCross.value);

            default:
                return false;
        }
    }
}
