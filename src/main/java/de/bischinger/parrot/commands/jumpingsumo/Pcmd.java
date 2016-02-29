package de.bischinger.parrot.commands.jumpingsumo;

import de.bischinger.parrot.commands.Acknowledge;
import de.bischinger.parrot.commands.Command;
import de.bischinger.parrot.commands.CommandKey;

import static de.bischinger.parrot.commands.ChannelType.JUMPINGSUMO_CONTROLLER_TO_DEVICE_NONACK_ID;
import static de.bischinger.parrot.commands.FrameType.ARNETWORKAL_FRAME_TYPE_DATA;

import static java.lang.String.format;


/**
 * Parrot command.
 *
 * <p>Responsible for the movements of the drone.</p>
 *
 * @author  Alexander Bischof
 * @author  Tobias Schneider
 */
public final class Pcmd implements Command {

    private final CommandKey commandKey = CommandKey.commandKey(3, 0, 0);
    private final byte speed;
    private final byte turn;

    protected Pcmd(int speed, int degrees) {

        if (speed < -128 || speed > 127) {
            throw new IllegalArgumentException(format("Movement: Speed must be between -128 and 127 but is %s", speed));
        }

        this.speed = (byte) speed;
        this.turn = (byte) degreeToPercent(degrees);
    }

    /**
     * This is one of the main methods to move a parrot drone.
     *
     * @param  speed  how fast the electronic engine of the drone should spin
     * @param  degrees  how much the drone will turn around his own axe in degree (°)
     *
     * @return  an immutable command with the given inputs
     */
    public static Pcmd pcmd(int speed, int degrees) {

        return new Pcmd(speed, degrees);
    }


    /**
     * Converts the degrees to percent on a circle.
     *
     * <p>R/360 = P/100 -> P = R*100/360</p>
     *
     * @param  degrees  to convert in percents of a circle
     *
     * @return  percents from given degrees
     */
    private int degreeToPercent(int degrees) {

        return degrees * 100 / 360;
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


    @Override
    public Acknowledge getAcknowledge() {

        return Acknowledge.NoAckBefore;
    }
}
