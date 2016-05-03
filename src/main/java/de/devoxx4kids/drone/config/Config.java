package de.devoxx4kids.drone.config;

/**
 * Configuration over properties.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
public final class Config {

    private final String ip;
    private final int port;
    private final String wlanName;
    private final Driver driver;
    private final int speed;
    private final int turn;

    Config(String ip, int port, String wlanName, Driver driver, int speed, int turn) {

        this.ip = ip;
        this.port = port;
        this.wlanName = wlanName;
        this.driver = driver;
        this.speed = speed;
        this.turn = turn;
    }

    public String getIp() {

        return ip;
    }


    public int getPort() {

        return port;
    }


    public String getWlanName() {

        return wlanName;
    }


    public Driver getDriver() {

        return driver;
    }


    public int getSpeed() {

        return speed;
    }


    public int getTurn() {

        return turn;
    }


    @Override
    public String toString() {

        return "Config{"
            + "ip='" + ip + '\''
            + ", port=" + port
            + ", wlanName='" + wlanName + '\''
            + ", driver=" + driver
            + ", speed=" + speed
            + ", turn=" + turn + '}';
    }
}
