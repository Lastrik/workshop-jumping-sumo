package de.bischinger.parrot.network.handshake;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.net.Socket;


/**
 * @author  Alexander Bischof
 */
public class TcpHandshake implements AutoCloseable {

    private final Socket tcpSocket;
    private final PrintWriter tcpOut;
    private final BufferedReader tcpIn;

    public TcpHandshake(String deviceIp, int tcpPort) throws IOException {

        tcpSocket = new Socket(deviceIp, tcpPort);
        tcpOut = new PrintWriter(tcpSocket.getOutputStream(), true);
        tcpIn = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
    }

    public HandshakeResponse shake(HandshakeRequest handshakeRequest) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        StringWriter shakeData = new StringWriter();
        objectMapper.writeValue(shakeData, handshakeRequest);

        return tcpHandshakeResult(shakeData.toString());
    }


    private HandshakeResponse tcpHandshakeResult(String shakeData) throws IOException {

        // Send to device
        tcpOut.println(shakeData);

        // Reads json response
        String responseLine;
        HandshakeResponse deviceAnswer = null;
        ObjectMapper objectMapper = new ObjectMapper();

        while ((responseLine = tcpIn.readLine()) != null) {
            responseLine = responseLine.substring(0, responseLine.lastIndexOf('}') + 1);
            deviceAnswer = objectMapper.readValue(responseLine, HandshakeResponse.class);
        }

        return deviceAnswer;
    }


    @Override
    public void close() throws IOException {

        tcpOut.close();
        tcpSocket.close();
        tcpIn.close();
    }
}
