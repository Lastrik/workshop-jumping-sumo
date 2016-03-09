package de.bischinger.parrot.commands.multimedia;

import de.bischinger.parrot.commands.Acknowledge;
import de.bischinger.parrot.commands.ChannelType;
import de.bischinger.parrot.commands.CommandKey;
import de.bischinger.parrot.commands.FrameType;
import de.bischinger.parrot.lib.command.Command;


/**
 * Audio theme command.
 *
 * <p>Responsible for the selection of the audio theme</p>
 *
 * @author  Alexander Bischof
 * @author  Tobias Schneider
 */
public final class AudioTheme implements Command {

    public enum Theme {

        Default,
        Robot,
        Insect,
        Monster;
    }

    private final CommandKey commandKey = CommandKey.commandKey(3, 12, 1);
    private final Theme theme;

    protected AudioTheme(AudioTheme.Theme theme) {

        this.theme = theme;
    }

    public static AudioTheme audioTheme(AudioTheme.Theme theme) {

        return new AudioTheme(theme);
    }


    @Override
    public byte[] getBytes(int counter) {

        return new byte[] {
                (byte) FrameType.ARNETWORKAL_FRAME_TYPE_DATA_WITH_ACK.ordinal(),
                ChannelType.JUMPINGSUMO_CONTROLLER_TO_DEVICE_ACK_ID.getId(), (byte) counter, 15, 0, 0, 0,
                commandKey.getProjectId(), commandKey.getClazzId(), commandKey.getCommandId(), 0,
                (byte) theme.ordinal(), 0, 0, 0
            };
    }


    @Override
    public Acknowledge getAcknowledge() {

        return Acknowledge.AckBefore;
    }


    @Override
    public String toString() {

        return "AudioTheme{"
            + "theme=" + theme + '}';
    }
}
