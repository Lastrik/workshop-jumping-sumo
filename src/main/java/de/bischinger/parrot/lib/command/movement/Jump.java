package de.bischinger.parrot.lib.command.movement;

import de.bischinger.parrot.lib.command.Acknowledge;
import de.bischinger.parrot.lib.command.ChannelType;
import de.bischinger.parrot.lib.command.Command;
import de.bischinger.parrot.lib.command.CommandKey;
import de.bischinger.parrot.lib.command.FrameType;


/**
 * @author  Alexander Bischof
 * @author  Tobias Schneider
 */
public final class Jump implements Command {

    public enum Type {

        Long,
        High;
    }

    private final CommandKey commandKey = CommandKey.commandKey(3, 2, 3);
    private final Type type;

    protected Jump(Type type) {

        this.type = type;
    }

    public static Jump jump(Type type) {

        return new Jump(type);
    }


    @Override
    public byte[] getBytes(int counter) {

        return new byte[] {
                (byte) FrameType.ARNETWORKAL_FRAME_TYPE_DATA_WITH_ACK.ordinal(),
                ChannelType.JUMPINGSUMO_CONTROLLER_TO_DEVICE_ACK_ID.getId(), (byte) counter, 15, 0, 0, 0,
                commandKey.getProjectId(), commandKey.getClazzId(), commandKey.getCommandId(), 0, (byte) type.ordinal(),
                0, 0, 0
            };
    }


    @Override
    public Acknowledge getAcknowledge() {

        return Acknowledge.AckBefore;
    }


    @Override
    public String toString() {

        return "Jump";
    }


    @Override
    public int waitingTime() {

        return 5000;
    }
}
