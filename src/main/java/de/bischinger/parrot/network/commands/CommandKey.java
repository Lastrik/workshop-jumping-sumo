package de.bischinger.parrot.network.commands;

public class CommandKey {

    private byte projectId;
    private byte clazzId;
    private byte commandId;

    public CommandKey(int projectId, int clazzId, int commandId) {

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
