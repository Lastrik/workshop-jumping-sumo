package de.bischinger.parrot.commands.common;

import de.bischinger.parrot.commands.Acknowledge;
import de.bischinger.parrot.commands.ChannelType;
import de.bischinger.parrot.commands.Command;
import de.bischinger.parrot.commands.CommandException;
import de.bischinger.parrot.commands.CommandKey;
import de.bischinger.parrot.commands.FrameType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.time.Clock;

import static java.time.LocalDate.now;
import static java.time.format.DateTimeFormatter.ISO_DATE;


/**
 * @author  Alexander Bischof
 * @author  Tobias Schneider
 */
public final class CurrentDate implements Command {

    private final CommandKey commandKey = CommandKey.commandKey(0, 4, 0);
    private final Clock clock;

    protected CurrentDate(Clock clock) {

        this.clock = clock;
    }

    public static CurrentDate currentDate(Clock clock) {

        return new CurrentDate(clock);
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
            outputStream.write(new NullTerminatedString(now(clock).format(ISO_DATE)).getNullTerminatedString());

            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new CommandException("Could not generate CurrentDate command.", e);
        }
    }


    @Override
    public Acknowledge getAcknowledge() {

        return Acknowledge.AckAfter;
    }
}
