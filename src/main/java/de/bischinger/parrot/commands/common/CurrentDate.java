package de.bischinger.parrot.commands.common;

import de.bischinger.parrot.commands.ChannelType;
import de.bischinger.parrot.commands.Command;
import de.bischinger.parrot.commands.CommandKey;
import de.bischinger.parrot.commands.FrameType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;


/**
 * @author  Alexander Bischof
 */
public final class CurrentDate implements Command {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);
    private final CommandKey commandKey = CommandKey.commandKey(0, 4, 0);

    protected CurrentDate() {

        // use fabric method
    }

    public static CurrentDate currentDate() {

        return new CurrentDate();
    }


    @Override
    public byte[] getBytes(int counter) {

        byte[] header = {
            (byte) FrameType.ARNETWORKAL_FRAME_TYPE_DATA_WITH_ACK.ordinal(),
            ChannelType.JUMPINGSUMO_CONTROLLER_TO_DEVICE_ACK_ID.getId(), (byte) counter, 15, 0, 0, 0,
            commandKey.getProjectId(), commandKey.getClazzId(), commandKey.getCommandId(), 0
        };

        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            outputStream.write(header);
            outputStream.write(new NullTerminatedString(DATE_FORMAT.format(new Date())).getNullTerminatedString());

            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[] {};
    }
}
