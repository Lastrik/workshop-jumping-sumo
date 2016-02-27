package de.bischinger.parrot.commands;

public class CommandKey {

    private final byte projectId;
    private final byte clazzId;
    private final byte commandId;

    protected CommandKey(int projectId, int clazzId, int commandId) {

        this.projectId = (byte) projectId;
        this.clazzId = (byte) clazzId;
        this.commandId = (byte) commandId;
    }

    public static CommandKey commandKey(int projectId, int clazzId, int commandId) {

        return new CommandKey(projectId, clazzId, commandId);
    }


    public byte getProjectId() {

        return projectId;
    }


    public byte getClazzId() {

        return clazzId;
    }


    public byte getCommandId() {

        return commandId;
    }
}
