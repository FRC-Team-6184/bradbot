package frc.robot.IO;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.AddressableLEDBufferView;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.LEDPattern.GradientType;
import edu.wpi.first.wpilibj.LEDReader.IndexedColorIterator;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.Units.*;
import edu.wpi.first.units.measure.*;

public class LEDControl {

    public static final int SIDE_LED_LENGTH = 42;
    public static final int ALL_LED_LENGTH = 84;

    public static AddressableLED allLEDS = new AddressableLED(0);
    public static AddressableLEDBuffer allLEDSBuffer = new AddressableLEDBuffer(ALL_LED_LENGTH);

    public static AddressableLEDBufferView ledBufferRight = allLEDSBuffer.createView(0, SIDE_LED_LENGTH - 1);
    public static AddressableLEDBufferView ledBufferLeft = allLEDSBuffer.createView(SIDE_LED_LENGTH, ALL_LED_LENGTH - 1).reversed();

    public static LEDPattern solidWhite = LEDPattern.solid(Color.kWhite);
    public static LEDPattern solidBlack = LEDPattern.solid(Color.kBlack);
    public static LEDPattern rainbow = LEDPattern.rainbow(255, 150);
    public static LEDPattern scrollingRainbow = rainbow.scrollAtRelativeSpeed(Units.Percent.per(Units.Second).of(35)).atBrightness(Units.Percent.of(55));
    public static LEDPattern rainbowMask = LEDPattern.solid(Color.kSlateGray);
    public static LEDPattern maskedScrollingRainbow = scrollingRainbow.mask(rainbowMask);

    public static LEDPattern speedGradient = LEDPattern.gradient(GradientType.kDiscontinuous, Color.kGreen, Color.kYellow, Color.kOrangeRed, Color.kRed); 
    public static LEDPattern leftMask = LEDPattern.solid(Color.kWhite);
    public static LEDPattern rightMask = LEDPattern.solid(Color.kWhite);
    public static LEDPattern leftSpeed = speedGradient.mask(leftMask);
    public static LEDPattern rightSpeed = speedGradient.mask(rightMask);



    //for testing
    public static void setLeftWhite() {
        solidWhite.applyTo(ledBufferLeft);
    }

    //pretty!
    public static void setRainbow() {
        scrollingRainbow.applyTo(allLEDSBuffer);
        // maskedScrollingRainbow.applyTo(allLEDSBuffer);
    }

    public static void setSpeedPattern(double leftValue, double rightValue) {
        //Creates and then applies a mask so that the pattern only shows 
        //the level of "speed" that they actually have
        leftMask = LEDPattern.progressMaskLayer(() -> Math.abs(leftValue));
        rightMask = LEDPattern.progressMaskLayer(() -> Math.abs(rightValue));

        // leftSpeed = speedGradient.mask(leftMask);
        // rightSpeed = speedGradient.mask(rightMask);

        leftSpeed = scrollingRainbow.mask(leftMask);
        rightSpeed = scrollingRainbow.mask(rightMask);

        //Reverse the patterns if they're going back!
        if(leftValue > 0) {
            leftSpeed.reversed().applyTo(ledBufferLeft);
        } else {
            leftSpeed.applyTo(ledBufferLeft);
        }

        if(rightValue > 0) {
            rightSpeed.reversed().applyTo(ledBufferRight);
        } else {
            rightSpeed.applyTo(ledBufferRight);
        }
        
    }

    public static void update() {
        allLEDS.setData(allLEDSBuffer);
    }

    public static void init() {
        allLEDS.setLength(84);
        allLEDS.start();
        solidBlack.applyTo(allLEDSBuffer);

    }
}
