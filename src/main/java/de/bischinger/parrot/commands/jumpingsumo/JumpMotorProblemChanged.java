package de.bischinger.parrot.commands.jumpingsumo;

import de.bischinger.parrot.commands.Acknowledge;
import de.bischinger.parrot.commands.ChannelType;
import de.bischinger.parrot.commands.Command;
import de.bischinger.parrot.commands.CommandKey;
import de.bischinger.parrot.commands.FrameType;


/**
 * @author  Alexander Bischof
 */
public final class JumpMotorProblemChanged implements Command {

    private final CommandKey commandKey = CommandKey.commandKey(3, 3, 2);

    protected JumpMotorProblemChanged() {

        // use fabric method
    }

    public static JumpMotorProblemChanged jumpMotorProblemChanged() {

        return new JumpMotorProblemChanged();
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

        return Acknowledge.None;
    }
}
