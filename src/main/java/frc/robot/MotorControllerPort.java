package frc.robot;

import java.util.List;

public class MotorControllerPort {
    public static final IntEnum FRONT_LEFT;
    public static final IntEnum BACK_LEFT;
    public static final IntEnum FRONT_RIGHT;

    public static final int BACK_RIGHT = 4;

    static {
        List<IntEnum> ports = IntEnum.range("MOTOR_CONTROLLER", 1, 7);
        BACK_LEFT = ports.get(1);
        FRONT_LEFT = ports.get(2);
        FRONT_RIGHT = ports.get(3);
    }
}
