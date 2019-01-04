package com.company;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class WirelessClient {
    public String mac;
    public LocalDateTime firstTS;
    public LocalDateTime lastTS;
    public int power;
    public int packetCount;
    public String bssid;
    public String essid;


    public WirelessClient(String clientMAC, String bssid, String essid) {
        this.mac = clientMAC.trim();
        this.bssid = bssid.trim();
        this.essid = essid.trim();
    }

    public WirelessClient wFirstTimeSeen(String firstTimeSeen) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.firstTS = LocalDateTime.parse(firstTimeSeen.trim(), formatter);
        return this;
    }

    public WirelessClient wLastTimeSeen(String lastTimeSeen) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.lastTS =  LocalDateTime.parse(lastTimeSeen.trim(), formatter);
        return this;
    }

    public WirelessClient wPower(String power) {
        this.power = Integer.parseInt(power.trim());
        return this;
    }

    public WirelessClient wPacketCount(String packetCount) {
        this.packetCount = Integer.parseInt(packetCount.trim());
        return this;
    }

    public boolean bssidLooksLegit() {
        if(bssid == null || bssid.isEmpty() || bssid.isBlank() || bssid.split(":").length != 6)
            return false;
        return true;
    }

    public Duration getLastSeen() {
        return Duration.between(lastTS, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    }

    public Duration getFirstSeen() {
        return Duration.between(firstTS, LocalDateTime.now()).truncatedTo(ChronoUnit.SECONDS);
    }

    @Override
    public String toString() {
        return "WirelessClient{" +
                "mac='" + mac + '\'' +
                ", firstTS=" + getFirstSeen().toString().substring(2) + " ago" +
                ", lastTS=" + getLastSeen().toString().substring(2) + " ago" +
                ", power=" + power +
                ", packetCount=" + packetCount +
                ", bssid='" + bssid + '\'' +
                ", essid='" + essid + '\'' +
                '}';
    }
}
