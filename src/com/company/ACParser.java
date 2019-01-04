package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ACParser {
    private String filename;

    public ACParser(String filename) {
        this.filename = filename;
    }

    public List<String> getLines() throws IOException {
        try(FileReader fr = new FileReader(filename)) {
            BufferedReader br = new BufferedReader(fr);
            return br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
            throw e;
        }
    }

    public List<AccessPoint> getAccessPoints(List<String> lines) {
        List<AccessPoint> aps = new ArrayList<>();
        for(String line : lines) {
            String[] result = line.split(",", 15);
            int len = result.length;
            switch (len) {
                case 7://This is a Client entry
                    continue;
                case 15: //This is an AccessPoint entry
                    if(result[0] != null && result[0].trim().equalsIgnoreCase("BSSID"))
                        continue;
                    aps.add(new AccessPoint(result[0],
                                            result[5],
                                            result[6],
                                            result[7],
                                            result[11],
                                            result[12],
                                            result[13],
                                            result[14])
                            .wFirstTimeSeen(result[1])
                            .wLastTimeSeen(result[2])
                            .wChannel(result[3])
                            .wSpeed(result[4])
                            .wPower(result[8])
                            .wBeaconCount(result[9])
                            .wIVCount(result[10]));
                    break;
                default:
                    continue;
            }
        }
        return aps;
    }

    public List<WirelessClient> getClients(List<String> lines) {
        List<WirelessClient> clients = new ArrayList<>();
        for(String line : lines) {
            String[] result = line.split(",",15);
            int len = result.length;
            switch (len) {
                case 7://This is a Client entry
                    if(result[0] != null && result[0].trim().equalsIgnoreCase("Station MAC"))
                        continue;
                    clients.add(new WirelessClient(result[0], result[5], result[6])
                            .wFirstTimeSeen(result[1])
                            .wLastTimeSeen(result[2])
                            .wPower(result[3])
                            .wPacketCount(result[4]));
                    break;
                case 15: //This is an AccessPoint entry
                    continue;
                default:
                    continue;
            }
        }
        return clients;
    }
}
