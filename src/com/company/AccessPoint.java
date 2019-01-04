package com.company;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccessPoint {
    public String bssid;
    public LocalDateTime firstTimeSeen;
    public LocalDateTime lastTimeSeen;
    public int channel;
    public int speed;
    public String privacy;
    public String cipher;
    public String authentication;
    public int power;
    public int beaconCount;
    public int IVCount;
    public String lanIP;
    public String idLength;
    public String essid;
    public String key;

    public AccessPoint(String bssid, String privacy, String cipher, String authentication, String lanIP, String idLength, String essid, String key) {
        this.bssid = bssid.trim();
        this.privacy = privacy.trim();
        this.cipher = cipher.trim();
        this.authentication = authentication.trim();
        this.lanIP = lanIP.trim().replaceAll(" ","");
        this.idLength = idLength.trim();
        this.essid = essid.trim();
        this.key = key.trim();

        if(this.essid.isBlank())
            this.essid = "unprintable";
    }

    public AccessPoint wFirstTimeSeen(String firstTimeSeen) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.firstTimeSeen = LocalDateTime.parse(firstTimeSeen.trim(), formatter);
        return this;
    }

    public AccessPoint wLastTimeSeen(String lastTimeSeen) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.lastTimeSeen = LocalDateTime.parse(lastTimeSeen.trim(), formatter);
        return this;
    }

    public AccessPoint wChannel(String channel) {
        this.channel = Integer.parseInt(channel.trim());
        return this;
    }

    public AccessPoint wSpeed(String speed) {
        this.speed = Integer.parseInt(speed.trim());
        return this;
    }

    public AccessPoint wPower(String power) {
        this.power = Integer.parseInt(power.trim());
        return this;
    }

    public AccessPoint wBeaconCount(String beaconCount) {
        this.beaconCount = Integer.parseInt(beaconCount.trim());
        return this;
    }

    public AccessPoint wIVCount(String IVCount) {
        this.IVCount = Integer.parseInt(IVCount.trim());
        return this;
    }

    public String getAiroDumpCommand() {
        return String.format("airodump-ng -c %d --bssid %s -w %s %s", channel, bssid, essid.isBlank() ? "unprintable" : essid, Main.WIRELESS_DEVICE);
    }

    public List<String> getAireplayCommands(List<WirelessClient> clients) {
        List<String> commands = new ArrayList<>();
        List<WirelessClient> ownedClients = clients.stream().filter(o -> o.bssid.equalsIgnoreCase(bssid)).collect(Collectors.toList());

        for(WirelessClient client : ownedClients) {
            commands.add(String.format("aireplay-ng -0 %d -a %s -c %s %s",Main.DEAUTH_COUNT, bssid, client.mac, Main.WIRELESS_DEVICE));
        }
        return commands;
    }

    @Override
    public String toString() {
        return "AccessPoint{" +
                "bssid='" + bssid + '\'' +
                ", essid='" + essid + '\'' +
                ", channel=" + channel +
                ", speed=" + speed +
                ", privacy='" + privacy + '\'' +
                ", cipher='" + cipher + '\'' +
                ", authentication='" + authentication + '\'' +
                ", power=" + power +
                ", beaconCount=" + beaconCount +
                ", IVCount=" + IVCount +
                ", lanIP='" + lanIP + '\'' +
                ", idLength='" + idLength + '\'' +
                ", firstTS=" + firstTimeSeen +
                ", lastTS=" + lastTimeSeen +
                ", key='" + key + '\'' +
                '}';
    }
}