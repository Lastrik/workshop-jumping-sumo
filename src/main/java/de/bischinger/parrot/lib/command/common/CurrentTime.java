package de.bischinger.parrot.lib.command.common;

import de.bischinger.parrot.lib.command.Acknowledge;
import de.bischinger.parrot.lib.command.ChannelType;
import de.bischinger.parrot.lib.command.CommandException;
import de.bischinger.parrot.lib.command.CommandKey;
import de.bischinger.parrot.lib.command.FrameType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


/**
 * @author  Alexander Bischof
 * @author  Tobias Schneider
 */
public final class CurrentTime implements CommonCommand {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("'T'HHmmssZZZ");
    private final CommandKey commandKey = CommandKey.commandKey(0, 4, 1);
    private final Clock clock;

    protected CurrentTime(Clock clock) {

        this.clock = clock;
    }

    public static CurrentTime currentTime(Clock clock) {

        return new CurrentTime(clock);
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
            outputStream.write(new NullTerminatedString(ZonedDateTime.now(clock).format(TIME_FORMATTER))
                .getNullTerminatedString());

            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new CommandException("Could not generate CurrentTime command.", e);
        }
    }


    @Override
    public Acknowledge getAcknowledge() {

        return Acknowledge.AckAfter;
    }


    @Override
    public String toString() {

        return "CurrentTime{" + clock.instant() + '}';
    }


    @Override
    public int waitingTime() {

        return 150;
    }
}
