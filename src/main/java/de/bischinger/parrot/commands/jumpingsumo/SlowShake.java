package de.bischinger.parrot.commands.jumpingsumo;

import de.bischinger.parrot.commands.ChannelType;
import de.bischinger.parrot.commands.Command;
import de.bischinger.parrot.commands.CommandKey;
import de.bischinger.parrot.commands.FrameType;


/**
 * @author  Alexander Bischof
 */
public final class SlowShake implements Command {

    private final CommandKey commandKey = CommandKey.commandKey(3, 2, 4);

    protected SlowShake() {

        // use fabric method
    }

    public static SlowShake slowShake() {

        return new SlowShake();
    }


    @Override
    public byte[] getBytes(int counter) {

        return new byte[] {
                (byte) FrameType.ARNETWORKAL_FRAME_TYPE_DATA_WITH_ACK.ordinal(),
                ChannelType.JUMPINGSUMO_CONTROLLER_TO_DEVICE_ACK_ID.getId(), (byte) counter, 15, 0, 0, 0,
                commandKey.getProjectId(), commandKey.getClazzId(), commandKey.getCommandId(), 0, 3, 0, 0, 0
            };
    }
}
