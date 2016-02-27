package de.bischinger.parrot.commands.jumpingsumo;

import de.bischinger.parrot.commands.Command;
import de.bischinger.parrot.commands.CommandKey;

import static de.bischinger.parrot.commands.ChannelType.JUMPINGSUMO_CONTROLLER_TO_DEVICE_NONACK_ID;
import static de.bischinger.parrot.commands.FrameType.ARNETWORKAL_FRAME_TYPE_DATA;


/**
 * @author  Alexander Bischof
 */
public class Pcmd implements Command {

    private final CommandKey commandKey = CommandKey.commandKey(3, 0, 0);
    private final byte speed;
    private final byte turn;

    protected Pcmd(int speed, int turn) {

        this.speed = (byte) speed;
        this.turn = (byte) turn;
    }

    public static Pcmd pcmd(int speed, int turn) {

        return new Pcmd(speed, turn);
    }


    @Override
    public byte[] getBytes(int counter) {

        byte touchscreen = 1;

        return new byte[] {
                (byte) ARNETWORKAL_FRAME_TYPE_DATA.ordinal(), JUMPINGSUMO_CONTROLLER_TO_DEVICE_NONACK_ID.getId(),
                (byte) counter, 14, 0, 0, 0, commandKey.getProjectId(), commandKey.getClazzId(),
                commandKey.getCommandId(), 0, touchscreen, speed, turn
            };
    }
}
