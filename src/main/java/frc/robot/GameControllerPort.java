package frc.robot;

import java.util.List;

public class GameControllerPort {
    public static final IntEnum DRIVER;

    static {
        List<IntEnum> ports = IntEnum.range("GAME_CONTROLER", 1, 1);
        DRIVER = ports.get(0);
    }
}
