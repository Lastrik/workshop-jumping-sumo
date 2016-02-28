package de.bischinger.parrot.commands.common;

import java.io.UnsupportedEncodingException;


/**
 * @author  Alexander Bischof
 */
public final class NullTerminatedString {

    private final String string;

    public NullTerminatedString(String string) {

        this.string = string;
    }

    public byte[] getNullTerminatedString() {

        try {
            byte[] stringBytes = string.getBytes("UTF-8");
            byte[] ntBytes = new byte[stringBytes.length + 1];
            System.arraycopy(stringBytes, 0, ntBytes, 0, stringBytes.length);

            return ntBytes;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
