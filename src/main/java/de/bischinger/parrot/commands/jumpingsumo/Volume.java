package de.bischinger.parrot.commands.jumpingsumo;

import de.bischinger.parrot.commands.Acknowledge;
import de.bischinger.parrot.commands.ChannelType;
import de.bischinger.parrot.commands.Command;
import de.bischinger.parrot.commands.CommandKey;
import de.bischinger.parrot.commands.FrameType;


/**
 * @author  Alexander Bischof
 * @author  Tobias Schneider
 */
public final class Volume implements Command {

    private final CommandKey commandKey = CommandKey.commandKey(3, 12, 0);
    private final byte volume;

    protected Volume(int volume) {

        if (volume < 0 || volume > 100) {
            throw new IllegalArgumentException("Audio: Volume must be between 0 and 100.");
        }

        this.volume = (byte) volume;
    }

    public static Volume volume(int volume) {

        return new Volume(volume);
    }


    @Override
    public byte[] getBytes(int counter) {

        return new byte[] {
                (byte) FrameType.ARNETWORKAL_FRAME_TYPE_DATA_WITH_ACK.ordinal(),
                ChannelType.JUMPINGSUMO_CONTROLLER_TO_DEVICE_ACK_ID.getId(), (byte) counter, 12, 0, 0, 0,
                commandKey.getProjectId(), commandKey.getClazzId(), commandKey.getCommandId(), 0, volume, 0
            };
    }


    @Override
    public Acknowledge getAcknowledge() {

        return Acknowledge.AckBefore;
    }


    @Override
    public String toString() {

        return "Volume{"
            + "volume=" + volume + '}';
    }
}
