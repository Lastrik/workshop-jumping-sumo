package de.bischinger.parrot.commands;

/**
 * Created by Alexander Bischof on 11.10.15.
 */
public class CommandReader {

    private final byte[] data;

    protected CommandReader(byte[] data) {

        this.data = data.clone();
    }

    public static CommandReader commandReader(byte[] data) {

        return new CommandReader(data);
    }


    public boolean isPing() {

        return data[0] == 2 && data[1] == 0;
    }


    public boolean isLinkQualityChanged() {

        return isProjectClazzCommand(3, 11, 4);
    }


    public boolean isWifiSignalChanged() {

        return isProjectClazzCommand(0, 5, 7);
    }


    private boolean isProjectClazzCommand(int project, int clazz, int command) {

        return data[7] == project && data[8] == clazz && data[9] == command;
    }
}
