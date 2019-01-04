package com.company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Main {
    public static final String FILENAME = "E:/code/jan2scan-04.csv";
    public static String WIRELESS_DEVICE = "wlan0mon";
    public static int DEAUTH_COUNT = 1;

    public static void main(String[] args) {
        try {
            ACParser parser = new ACParser(FILENAME);
            List<String> lines = parser.getLines();
            List<AccessPoint> accessPoints = parser.getAccessPoints(lines);
            Map<String, AccessPoint> apMap = new HashMap<>();
            accessPoints.forEach(a -> apMap.put(a.bssid, a));
            apMap.put("(not associated)", null);

            List<WirelessClient> wirelessClients = parser.getClients(lines);
            List<AccessPoint> accessPointsWithClients = wirelessClients.stream()
                    .map(o -> o.bssid)
                    .map(bssid -> apMap.get(bssid))
                    .filter(x -> x != null)
                    .distinct()
                    .collect(Collectors.toList());
            for (AccessPoint p : accessPointsWithClients) {
                p.setWirelessClients(wirelessClients);
                System.out.println(String.format("\n%s @ %ddb ]==[%d clients]%s", p.essid, p.power, p.getClientCount(), pad("=", 120 - p.essid.length() - String.valueOf(p.power).length() - String.valueOf(p.getClientCount()).length())));
                System.out.println(String.format("\t%s", p.getAiroDumpCommand()));
                System.out.println("\t" + combine(p.getAireplayCommands()));
            }

        } catch (Exception eX) {
            System.out.println(eX.getMessage());

        }
    }

    public static String pad(String with, int count) {
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++)
            sb.append(with);
        return sb.toString();
    }

    public static String combine(List<String> commands) {
        StringJoiner joiner = new StringJoiner(" && ");
        for (String s : commands)
            joiner.add(s);
        return joiner.toString();
    }
}
