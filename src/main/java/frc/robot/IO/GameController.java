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

    private static SendableChooser<String> dropdown = new SendableChooser<String>();
    private static final String[] DROPDOWN_OPTIONS = {"XBox Controller", "PS5 Controller", "PS4 Controller"};
    private static SelectedController[] SelectedControllerArray = {SelectedController.XBOX, SelectedController.PS5, SelectedController.PS4};
    private static SelectedController selection;

    public static GameController controller;

    private GenericHID internalController;

    /**
     * Must be ran before interacting with the controller. Takes selection from SmartDashboard and sets up class for it.
     */
    public static void initController() {
        String selectedFromDashboard = dropdown.getSelected();

        for(int i = 0; i < DROPDOWN_OPTIONS.length; i++) {
            if(selectedFromDashboard.equals(DROPDOWN_OPTIONS[i])) {
                selection = SelectedControllerArray[i];
                controller = new GameController(DRIVER);
                return; //Ends method early
            }
        }

        //If the method gets here something has gone wrong, defaulting to Xbox
        selection = SelectedControllerArray[0];
        controller = new GameController(DRIVER);
    }

    /**
     * Or shuffleboard, they use pretty much the same API
     * 
     * Initializes dropdowns and such that are controller related
     */
    public static void initSmartboard() {
        for(int i = 0; i < DROPDOWN_OPTIONS.length; i++) { //Sets up the dropdown
            dropdown.addOption(DROPDOWN_OPTIONS[i], DROPDOWN_OPTIONS[i]); 
        }

        dropdown.setDefaultOption(DROPDOWN_OPTIONS[0], DROPDOWN_OPTIONS[0]); //Makes sure we you don't have to do anything as long as you're using an XBox Controller

        SmartDashboard.putData(dropdown); //actually puts it on the dashboard
    }

    public GameController(int port) {
        //This entire implementation is ugly but if it works...
        switch(selection) {
            case XBOX:
                internalController = new XboxController(port);
                break;
            case PS5:
                internalController = new PS5Controller(port);
                break; 
            case PS4:
                internalController = new PS4Controller(port);
                break;
        }
    }
    
    public double getLeftX() {
        switch(selection) {
            case XBOX:
                return ((XboxController)(internalController)).getLeftX();

            case PS5:
                return ((PS5Controller)(internalController)).getLeftX();

            case PS4:
                return ((PS4Controller)(internalController)).getLeftX();

            default:
                return 0;
        }

    }

    public double getLeftY() {
        switch(selection) {
            case XBOX:
                return ((XboxController)(internalController)).getLeftY();

            case PS5:
                return ((PS5Controller)(internalController)).getLeftY();

            case PS4:
                return ((PS4Controller)(internalController)).getLeftY();

            default:
                return 0;
        }
    }

    public double getRightX() {
        switch(selection) {
            case XBOX:
                return ((XboxController)(internalController)).getRightX();

            case PS5:
                return ((PS5Controller)(internalController)).getRightX();

            case PS4:
                return ((PS4Controller)(internalController)).getRightX();

            default:
                return 0;
        } 
    }

    public double getRightY() {
        switch(selection) {
            case XBOX:
                return ((XboxController)(internalController)).getRightY();

            case PS5:
                return ((PS5Controller)(internalController)).getRightY();

            case PS4:
                return ((PS4Controller)(internalController)).getRightY();

            default:
                return 0;
        }
    }

    public double getLeftTrigger() {
        switch(selection) {
            case XBOX:
                return ((XboxController)(internalController)).getLeftTriggerAxis();

            case PS5:
                return ((PS5Controller)(internalController)).getL2Axis();

            case PS4:
                return ((PS4Controller)(internalController)).getL2Axis();

            default:
                return 0;
        }
    }

    public double getRightTrigger() {
        switch(selection) {
            case XBOX:
                return ((XboxController)(internalController)).getRightTriggerAxis();

            case PS5:
                return ((PS5Controller)(internalController)).getR2Axis();

            case PS4:
                return ((PS4Controller)(internalController)).getR2Axis();

            default:
                return 0;
        }
    }

    public boolean getLeftBumper() {
        switch(selection) {
            case XBOX:
                return ((XboxController)(internalController)).getLeftBumperButton();

            case PS5:
                return ((PS5Controller)(internalController)).getL1Button();

            case PS4:
                return ((PS4Controller)(internalController)).getL1Button();

            default:
                return false;
        }
    }

    public boolean getRightBumper() {
        switch(selection) {
            case XBOX:
                return ((XboxController)(internalController)).getRightBumperButton();

            case PS5:
                return ((PS5Controller)(internalController)).getR1Button();

            case PS4:
                return ((PS4Controller)(internalController)).getR1Button();

            default:
                return false;
        }
    }

    public boolean getFaceButtonDown() {
        switch(selection) {
            case XBOX:
                return ((XboxController)(internalController)).getAButton();

            case PS5:
                return ((PS5Controller)(internalController)).getCrossButton();

            case PS4:
                return ((PS4Controller)(internalController)).getCrossButton();

            default:
                return false;
        }
    }

    public boolean getFaceButtonLeft() {
        switch(selection) {
            case XBOX:
                return ((XboxController)(internalController)).getXButton();

            case PS5:
                return ((PS5Controller)(internalController)).getSquareButton();

            case PS4:
                return ((PS4Controller)(internalController)).getSquareButton();

            default:
                return false;
        }
    }

    public boolean getFaceButtonRight() {
        switch(selection) {
            case XBOX:
                return ((XboxController)(internalController)).getBButton();

            case PS5:
                return ((PS5Controller)(internalController)).getCircleButton();

            case PS4:
                return ((PS4Controller)(internalController)).getCircleButton();

            default:
                return false;
        }
    }

    public boolean getFaceButtonUp() {
        switch(selection) {
            case XBOX:
                return ((XboxController)(internalController)).getYButton();

            case PS5:
                return ((PS5Controller)(internalController)).getTriangleButton();

            case PS4:
                return ((PS4Controller)(internalController)).getTriangleButton();

            default:
                return false;
        }
    }

    



}
