package de.bischinger.parrot.commands.multimedia;

import de.bischinger.parrot.commands.Acknowledge;
import de.bischinger.parrot.commands.ChannelType;
import de.bischinger.parrot.commands.CommandKey;
import de.bischinger.parrot.commands.FrameType;
import de.bischinger.parrot.lib.command.Command;


/**
 * @author  Alexander Bischof
 * @author  Tobias Schneider
 */
public final class VideoStreaming implements Command {

    private final CommandKey commandKey = CommandKey.commandKey(3, 18, 0);
    private final byte enable;

    protected VideoStreaming(byte enable) {

        this.enable = enable;
    }

    public static VideoStreaming enableVideoStreaming() {

        return new VideoStreaming((byte) 1);
    }


    public static VideoStreaming disableVideoStreaming() {

        return new VideoStreaming((byte) 0);
    }


    @Override
    public byte[] getBytes(int counter) {

        return new byte[] {
                (byte) FrameType.ARNETWORKAL_FRAME_TYPE_DATA_WITH_ACK.ordinal(),
                ChannelType.JUMPINGSUMO_CONTROLLER_TO_DEVICE_ACK_ID.getId(), (byte) counter, 12, 0, 0, 0,
                commandKey.getProjectId(), commandKey.getClazzId(), commandKey.getCommandId(), 0, enable, 0
            };
    }


    @Override
    public Acknowledge getAcknowledge() {

        return Acknowledge.AckAfter;
    }
}
