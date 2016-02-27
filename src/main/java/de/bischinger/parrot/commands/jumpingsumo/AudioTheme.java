package de.bischinger.parrot.commands.jumpingsumo;

import de.bischinger.parrot.commands.ChannelType;
import de.bischinger.parrot.commands.Command;
import de.bischinger.parrot.commands.CommandKey;
import de.bischinger.parrot.commands.FrameType;


/**
 * @author  Alexander Bischof
 */
public class AudioTheme implements Command {

    public enum Theme {

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
                (byte) (theme.ordinal() + 1), 0, 0, 0
            };
    }
}