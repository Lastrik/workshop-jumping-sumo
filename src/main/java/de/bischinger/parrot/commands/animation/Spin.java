package de.bischinger.parrot.commands.animation;

import de.bischinger.parrot.commands.Acknowledge;
import de.bischinger.parrot.commands.ChannelType;
import de.bischinger.parrot.commands.Command;
import de.bischinger.parrot.commands.CommandKey;
import de.bischinger.parrot.commands.FrameType;


/**
 * @author  Alexander Bischof
 * @author  Tobias Schneider
 */
public final class Spin implements Command {

    private final CommandKey commandKey = CommandKey.commandKey(3, 2, 4);

    protected Spin() {

        // use fabric method
    }

    public static Spin spin() {

        return new Spin();
    }


    @Override
    public byte[] getBytes(int counter) {

        return new byte[] {
                (byte) FrameType.ARNETWORKAL_FRAME_TYPE_DATA_WITH_ACK.ordinal(),
                ChannelType.JUMPINGSUMO_CONTROLLER_TO_DEVICE_ACK_ID.getId(), (byte) counter, 15, 0, 0, 0,
                commandKey.getProjectId(), commandKey.getClazzId(), commandKey.getCommandId(), 0, 1, 0, 0, 0
            };
    }


    @Override
    public Acknowledge getAcknowledge() {

        return Acknowledge.AckBefore;
    }


    @Override
    public String toString() {

        return "Spin";
    }


    @Override
    public int waitingTime() {

        return 3000;
    }
}