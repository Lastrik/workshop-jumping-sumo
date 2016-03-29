package de.bischinger.parrot.control.driver.naturallanguage;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.AWTException;

import java.io.IOException;

import java.lang.invoke.MethodHandles;


public class JumpingSumoLang {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String CMD = "/*\r\n" + "* A simple example demonstrating the basic features.\r\n" + "*/\r\n"
        + "delay 300; // sleep for 300ms\r\n"
        + "forward 101;\r\n"
        + "backward 100;\r\n"
        + "left 100;\r\n"
        + "right 100;\r\n"
        + "jump high;\r\n"
        + "jump long;\r\n"
        + "jump long;\r\n";

    public void execute() throws IOException, AWTException {

        LOGGER.info(CMD);

        de.bischinger.parrot.driver.naturallanguage.JumpingSumoLexer lexer =
            new de.bischinger.parrot.driver.naturallanguage.JumpingSumoLexer(new ANTLRInputStream(CMD));

        de.bischinger.parrot.driver.naturallanguage.JumpingSumoParser parser =
            new de.bischinger.parrot.driver.naturallanguage.JumpingSumoParser(new CommonTokenStream(lexer));

        parser.addParseListener(new de.bischinger.parrot.driver.naturallanguage.JumpingSumoBaseListener() {

                @Override
                public void exitInstructionDelay(
                    de.bischinger.parrot.driver.naturallanguage.JumpingSumoParser.InstructionDelayContext ctx) {

                    int delayParam = Integer.parseInt(ctx.paramMs.getText());
                    LOGGER.info("delay(" + delayParam + ")");
                }


                @Override
                public void exitInstructionForward(
                    de.bischinger.parrot.driver.naturallanguage.JumpingSumoParser.InstructionForwardContext ctx) {

                    int delayParam = Integer.parseInt(ctx.paramMs.getText());
                    LOGGER.info("forward(" + delayParam + ")");
                }


                @Override
                public void exitInstructionBackward(
                    de.bischinger.parrot.driver.naturallanguage.JumpingSumoParser.InstructionBackwardContext ctx) {

                    int delayParam = Integer.parseInt(ctx.paramMs.getText());
                    LOGGER.info("backward(" + delayParam + ")");
                }


                @Override
                public void exitInstructionLeft(
                    de.bischinger.parrot.driver.naturallanguage.JumpingSumoParser.InstructionLeftContext ctx) {

                    int delayParam = Integer.parseInt(ctx.degrees.getText());
                    LOGGER.info("left(" + delayParam + ")");
                }


                @Override
                public void exitInstructionRight(
                    de.bischinger.parrot.driver.naturallanguage.JumpingSumoParser.InstructionRightContext ctx) {

                    int delayParam = Integer.parseInt(ctx.degrees.getText());
                    LOGGER.info("right(" + delayParam + ")");
                }


                @Override
                public void exitInstructionJump(
                    de.bischinger.parrot.driver.naturallanguage.JumpingSumoParser.InstructionJumpContext ctx) {

                    LOGGER.info("jump(" + ctx.type.getText() + ")");
                }
            });
        parser.instructions();
    }
}
