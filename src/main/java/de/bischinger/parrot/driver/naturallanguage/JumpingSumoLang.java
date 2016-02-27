package de.bischinger.parrot.driver.naturallanguage;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.awt.AWTException;

import java.io.IOException;


public class JumpingSumoLang {

    public static final String cmd = "/*\r\n" + "* A simple example demonstrating the basic features.\r\n" + "*/\r\n"
        + "delay 300; // sleep for 300ms\r\n"
        + "forward 101;\r\n"
        + "backward 100;\r\n"
        + "left 100;\r\n"
        + "right 100;\r\n"
        + "jump high;\r\n"
        + "jump long;\r\n"
        + "jump long;\r\n";

    public void execute() throws IOException, AWTException {

        System.out.println(cmd);

        de.bischinger.parrot.driver.naturallanguage.JumpingSumoLexer lexer =
            new de.bischinger.parrot.driver.naturallanguage.JumpingSumoLexer(new ANTLRInputStream(cmd));

        de.bischinger.parrot.driver.naturallanguage.JumpingSumoParser parser =
            new de.bischinger.parrot.driver.naturallanguage.JumpingSumoParser(new CommonTokenStream(lexer));

        parser.addParseListener(new de.bischinger.parrot.driver.naturallanguage.JumpingSumoBaseListener() {

                @Override
                public void exitInstructionDelay(
                    de.bischinger.parrot.driver.naturallanguage.JumpingSumoParser.InstructionDelayContext ctx) {

                    int delayParam = Integer.parseInt(ctx.paramMs.getText());
                    System.out.println("delay(" + delayParam + ")");
                }


                @Override
                public void exitInstructionForward(
                    de.bischinger.parrot.driver.naturallanguage.JumpingSumoParser.InstructionForwardContext ctx) {

                    int delayParam = Integer.parseInt(ctx.paramMs.getText());
                    System.out.println("forward(" + delayParam + ")");
                }


                @Override
                public void exitInstructionBackward(
                    de.bischinger.parrot.driver.naturallanguage.JumpingSumoParser.InstructionBackwardContext ctx) {

                    int delayParam = Integer.parseInt(ctx.paramMs.getText());
                    System.out.println("backward(" + delayParam + ")");
                }


                @Override
                public void exitInstructionLeft(
                    de.bischinger.parrot.driver.naturallanguage.JumpingSumoParser.InstructionLeftContext ctx) {

                    int delayParam = Integer.parseInt(ctx.degrees.getText());
                    System.out.println("left(" + delayParam + ")");
                }


                @Override
                public void exitInstructionRight(
                    de.bischinger.parrot.driver.naturallanguage.JumpingSumoParser.InstructionRightContext ctx) {

                    int delayParam = Integer.parseInt(ctx.degrees.getText());
                    System.out.println("right(" + delayParam + ")");
                }


                @Override
                public void exitInstructionJump(
                    de.bischinger.parrot.driver.naturallanguage.JumpingSumoParser.InstructionJumpContext ctx) {

                    System.out.println("jump(" + ctx.type.getText() + ")");
                }
            });
        parser.instructions();
    }
}
