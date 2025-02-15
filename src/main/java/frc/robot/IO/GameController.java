package frc.robot.IO;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.PS5Controller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GameController {
    public static final int DRIVER = 0;

    public enum SelectedController {
        XBOX,
        PS5,
        PS4
    }

    public enum XboxControllerButton {
        /** A button. */
        kA(1),
        /** B button. */
        kB(2),
        /** X button. */
        kX(3),
        /** Y button. */
        kY(4),
        /** Left bumper button. */
        kLeftBumper(5),
        /** Right bumper button. */
        kRightBumper(6),
        /** Back button. */
        kBack(7),
        /** Start button. */
        kStart(8),
        /** Left stick button. */
        kLeftStick(9),
        /** Right stick button. */
        kRightStick(10);

        /** Button value. */
        public final int value;

        XboxControllerButton(int value) {
            this.value = value;
        }
    }

    public enum XboxControllerAxis {
        /** Left X axis. */
        kLeftX(0),
        /** Right X axis. */
        kRightX(4),
        /** Left Y axis. */
        kLeftY(1),
        /** Right Y axis. */
        kRightY(5),
        /** Left trigger. */
        kLeftTrigger(2),
        /** Right trigger. */
        kRightTrigger(3);

        /** Axis value. */
        public final int value;

        XboxControllerAxis(int value) {
            this.value = value;
        }
    }

    public enum PS5ControllerButton {
        /** Square button. */
        kSquare(1),
        /** Cross button. */
        kCross(2),
        /** Circle button. */
        kCircle(3),
        /** Triangle button. */
        kTriangle(4),
        /** Left trigger 1 button. */
        kL1(5),
        /** Right trigger 1 button. */
        kR1(6),
        /** Left trigger 2 button. */
        kL2(7),
        /** Right trigger 2 button. */
        kR2(8),
        /** Create button. */
        kCreate(9),
        /** Options button. */
        kOptions(10),
        /** L3 (left stick) button. */
        kL3(11),
        /** R3 (right stick) button. */
        kR3(12),
        /** PlayStation button. */
        kPS(13),
        /** Touchpad button. */
        kTouchpad(14);

        /** Button value. */
        public final int value;

        PS5ControllerButton(int value) {
            this.value = value;
        }
    }

    public enum PS5ControllerAxis {
        /** Left X axis. */
        kLeftX(0),
        /** Left Y axis. */
        kLeftY(1),
        /** Right X axis. */
        kRightX(2),
        /** Right Y axis. */
        kRightY(5),
        /** Left trigger 2. */
        kL2(3),
        /** Right trigger 2. */
        kR2(4);

        /** Axis value. */
        public final int value;

        PS5ControllerAxis(int value) {
            this.value = value;
        }
    }

    public enum PS4ControllerButton {
        /** Square button. */
        kSquare(1),
        /** Cross button. */
        kCross(2),
        /** Circle button. */
        kCircle(3),
        /** Triangle button. */
        kTriangle(4),
        /** Left trigger 1 button. */
        kL1(5),
        /** Right trigger 1 button. */
        kR1(6),
        /** Left trigger 2 button. */
        kL2(7),
        /** Right trigger 2 button. */
        kR2(8),
        /** Share button. */
        kShare(9),
        /** Options button. */
        kOptions(10),
        /** L3 (left stick) button. */
        kL3(11),
        /** R3 (right stick) button. */
        kR3(12),
        /** PlayStation button. */
        kPS(13),
        /** Touchpad button. */
        kTouchpad(14);

        /** Button value. */
        public final int value;

        PS4ControllerButton(int value) {
            this.value = value;
        }
    }

    public enum PS4ControllerAxis {
        /** Left X axis. */
        kLeftX(0),
        /** Left Y axis. */
        kLeftY(1),
        /** Right X axis. */
        kRightX(2),
        /** Right Y axis. */
        kRightY(5),
        /** Left trigger 2. */
        kL2(3),
        /** Right trigger 2. */
        kR2(4);

        /** Axis value. */
        public final int value;

        PS4ControllerAxis(int value) {
            this.value = value;
        }
    }

    private static SendableChooser<String> dropdown = new SendableChooser<String>();
    private static final String[] DROPDOWN_OPTIONS = { "XBox Controller", "PS5 Controller", "PS4 Controller" };
    private static SelectedController[] SelectedControllerArray = { SelectedController.XBOX, SelectedController.PS5,
            SelectedController.PS4 };
    private static SelectedController selection;

    public static GameController controller;

    private GenericHID internalController;

    /**
     * Must be ran before interacting with the controller. Takes selection from
     * SmartDashboard and sets up class for it.
     */
    public static void initController() {
        String selectedFromDashboard = dropdown.getSelected();

        for (int i = 0; i < DROPDOWN_OPTIONS.length; i++) {
            if (selectedFromDashboard.equals(DROPDOWN_OPTIONS[i])) {
                selection = SelectedControllerArray[i];
                controller = new GameController(DRIVER);
                return; // Ends method early
            }
        }

        // If the method gets here something has gone wrong, defaulting to Xbox
        selection = SelectedControllerArray[0];
        controller = new GameController(DRIVER);
    }

    /**
     * Or shuffleboard, they use pretty much the same API
     * 
     * Initializes dropdowns and such that are controller related
     */
    public static void initSmartboard() {
        for (int i = 0; i < DROPDOWN_OPTIONS.length; i++) { // Sets up the dropdown
            dropdown.addOption(DROPDOWN_OPTIONS[i], DROPDOWN_OPTIONS[i]);
        }

        dropdown.setDefaultOption(DROPDOWN_OPTIONS[0], DROPDOWN_OPTIONS[0]); // Makes sure we you don't have to do
                                                                             // anything as long as you're using an XBox
                                                                             // Controller

        SmartDashboard.putData(dropdown); // actually puts it on the dashboard
    }

    public GameController(int port) {
        internalController = new GenericHID(port);
    }

    public double getLeftX() {
        switch (selection) {
            case XBOX:
                return internalController.getRawAxis(XboxControllerAxis.kLeftX.value);
            case PS5:
                return internalController.getRawAxis(PS5ControllerAxis.kLeftX.value);
            case PS4:
                return internalController.getRawAxis(PS4ControllerAxis.kLeftX.value);
            default:
                return 0;
        }

    }

    public double getLeftY() {
        switch (selection) {
            case XBOX:
                return internalController.getRawAxis(XboxControllerAxis.kLeftY.value);
            case PS5:
                return internalController.getRawAxis(PS5ControllerAxis.kLeftY.value);
            case PS4:
                return internalController.getRawAxis(PS4ControllerAxis.kLeftY.value);
            default:
                return 0;
        }
    }

    public double getRightX() {
        switch (selection) {
            case XBOX:
                return internalController.getRawAxis(XboxControllerAxis.kRightX.value);
            case PS5:
                return internalController.getRawAxis(PS5ControllerAxis.kRightX.value);
            case PS4:
                return internalController.getRawAxis(PS4ControllerAxis.kRightX.value);
            default:
                return 0;
        }
    }

    public double getRightY() {
        switch (selection) {
            case XBOX:
                return internalController.getRawAxis(XboxControllerAxis.kRightY.value);
            case PS5:
                return internalController.getRawAxis(PS5ControllerAxis.kRightY.value);
            case PS4:
                return internalController.getRawAxis(PS4ControllerAxis.kRightY.value);
            default:
                return 0;
        }
    }

    public double getLeftTrigger() {
        switch (selection) {
            case XBOX:
                return internalController.getRawAxis(XboxControllerAxis.kLeftTrigger.value);
            case PS5:
                return internalController.getRawAxis(PS5ControllerAxis.kL2.value);
            case PS4:
                return internalController.getRawAxis(PS4ControllerAxis.kL2.value);
            default:
                return 0;
        }
    }

    public double getRightTrigger() {
        switch (selection) {
            case XBOX:
                return internalController.getRawAxis(XboxControllerAxis.kRightTrigger.value);
            case PS5:
                return internalController.getRawAxis(PS5ControllerAxis.kR2.value);
            case PS4:
                return internalController.getRawAxis(PS4ControllerAxis.kR2.value);
            default:
                return 0;
        }
    }

    public boolean getLeftBumper() {
        switch (selection) {
            case XBOX:
                return internalController.getRawButton(XboxControllerButton.kLeftBumper.value);

            case PS5:
                return internalController.getRawButton(PS5ControllerButton.kL1.value);

            case PS4:
                return internalController.getRawButton(PS4ControllerButton.kL1.value);

            default:
                return false;
        }
    }

    public boolean getRightBumper() {
        switch (selection) {
            case XBOX:
                return internalController.getRawButton(XboxControllerButton.kRightBumper.value);

            case PS5:
                return internalController.getRawButton(PS5ControllerButton.kR1.value);

            case PS4:
                return internalController.getRawButton(PS4ControllerButton.kR1.value);

            default:
                return false;
        }
    }

    public boolean getFaceButtonDown() {
        switch (selection) {
            case XBOX:
                return internalController.getRawButton(XboxControllerButton.kA.value);

            case PS5:
                return internalController.getRawButton(PS5ControllerButton.kCross.value);

            case PS4:
                return internalController.getRawButton(PS4ControllerButton.kCross.value);

            default:
                return false;
        }
    }

    public boolean getFaceButtonLeft() {
        switch (selection) {
            case XBOX:
                return internalController.getRawButton(XboxControllerButton.kX.value);

            case PS5:
                return internalController.getRawButton(PS5ControllerButton.kSquare.value);

            case PS4:
                return internalController.getRawButton(PS4ControllerButton.kSquare.value);

            default:
                return false;
        }
    }

    public boolean getFaceButtonRight() {
        switch (selection) {
            case XBOX:
                return internalController.getRawButton(XboxControllerButton.kB.value);

            case PS5:
                return internalController.getRawButton(PS5ControllerButton.kCircle.value);

            case PS4:
                return internalController.getRawButton(PS4ControllerButton.kCircle.value);

            default:
                return false;
        }
    }

    public boolean getFaceButtonUp() {
        switch (selection) {
            case XBOX:
                return internalController.getRawButton(XboxControllerButton.kY.value);

            case PS5:
                return internalController.getRawButton(PS5ControllerButton.kTriangle.value);

            case PS4:
                return internalController.getRawButton(PS4ControllerButton.kTriangle.value);

            default:
                return false;
        }
    }

}
