package com.cmarquez.android.testing.library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

/**
 * 
 * @author Cristian Marquez Russo 
 * @see <link>http://www.cristianmarquez.com.ar</link>
 * 
 */
public class EmulatorBridge {

    private static final String TAG = EmulatorBridge.class.getSimpleName();

    /**
     * Receive SMS text message
     * 
     * 
     * @param number
     * @param message
     */
    public static void receiveSMS(String number, String message) {
        String utf8message = "";
        for (char c : message.toCharArray()) {
            if (!(Character.isLetter(c) || Character.isDigit(c) || Character
                    .isWhitespace(c))) {
                String hex = String.format("%04x", (int) c);
                utf8message += "\\u" + hex + " "; // IGNORE

            } else {
                utf8message += c;
            }
        }
        sendCommand("sms send " + number + " " + utf8message);
    }

    /**
     * Send a simple GPS fix
     * 
     * @param lat
     * @param lon
     */
    public static void simulateLocation(Double lat, Double lon) {
        sendCommand("geo fix " + lat + lon);
    }

    /**
     * Send an GPS NMEA sentence
     * 
     * @param nmea
     *            Nmea sentence
     */
    public static void simulateLocationNmea(String nmea) {
        sendCommand("geo nmea " + nmea);
    }

    /**
     * Create inbound phone call
     * 
     * @param phoneNumber
     */
    public static void simulateIncomingCall(String phoneNumber) {
        sendCommand("gsm call " + phoneNumber);
    }

    /**
     * Disconnect an inbound or outbound phone call
     * 
     * @param phoneNumber
     */
    public static void simulateIncomingCallHang(String phoneNumber) {
        sendCommand("gsm cancel " + phoneNumber);
    }

    /**
     * Accept an inbound phone call
     * 
     * @param phoneNumber
     */
    public static void simulateIncomingCallAnswer(String phoneNumber) {
        sendCommand("gsm accept " + phoneNumber);
    }

    /**
     * Put on hold an inbound phone call
     * 
     * @param phoneNumber
     */
    public static void simulateIncomingCallHold(String phoneNumber) {
        sendCommand("gsm hold " + phoneNumber);
    }

    /**
     * Close waiting outbound call as busy
     * 
     * @param phoneNumber
     */
    public static void simulateCallBusy(String phoneNumber) {
        sendCommand("gsm busy " + phoneNumber);
    }

    /**
     * Send an Android console command
     * 
     * @param command
     * @return
     */
    private static String sendCommand(String command) {

        Log.d(TAG, "Sending Android command to emulator: " + command);

        String result = "";
        Socket socket;

        try {
            socket = new Socket("10.0.2.2", 5554);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            out.println(command);
            result = "";
            // FIXME Return the command result
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Response of android terminal: " + result);
        return result;
    }

    /**
     * Allows you to set battery capacity to a value 0 - 100
     * 
     * @param percentage
     */
    public static void setBatteryCapacity(int percentage) {
        sendCommand("power capacity " + percentage);
    }

    /**
     * Allows you to set battery status
     * 
     * @param batteryStatus
     */
    public static void setStatus(BatteryStatusType batteryStatus) {
        sendCommand("power status " + batteryStatus.getValue());
    }

    /**
     * Allows you to set battery health state
     * 
     * @param batteryHealth
     */
    public static void setHealth(BatteryHealthType batteryHealth) {
        sendCommand("power health " + batteryHealth.getValue());
    }

    /**
     * Allows you to set battery present state to true or false
     * 
     * @param present
     */
    public static void setBatteryPresent(boolean present) {
        sendCommand("power present " + present);
    }

    /**
     * Allows you to set the AC charging state to on or off
     * 
     * @param ac
     */
    public static void setAC(boolean ac) {
        sendCommand("power ac " + ac);
    }

    public static void hangUpActiveCalls() {
        Log.d("hangUpActiveCalls", "Closing all active calls");
        String activeCalls = sendCommand("gsm list");
        activeCalls = "inbound from 123        : incoming"
                + "inbound from 12354      : incoming";
        List<String> activeCall = new ArrayList<String>();
        for (String call : activeCall) {
            simulateIncomingCallHang(call);
        }
        Log.d("ACTIVE-CALLS", activeCalls);
    }

    /**
     * Allows you to change the state of your GPRS connection
     * 
     * @see GsmStates
     * @param gsmstate
     */
    public static void setGsmData(GsmStates gsmstate) {
        sendCommand("gsm data " + gsmstate.toString());
    }

    /**
     * Allows you to change the state of your GPRS connection
     * 
     * @see GsmStates
     * @param gsmstate
     */
    public static void setGsmVoice(GsmStates gsmstate) {
        sendCommand("gsm voice " + gsmstate.toString());
    }

    /**
     * Enable or disable the internet connection in the emulator
     * 
     * @param internet
     *            Internet enabled if <code>true</code> Disabled if
     *            <code>false</code>
     */
    public static void setInternetConnection(boolean internet) {
        if (internet) {
            setGsmData(GsmStates.HOME);
        } else {
            setGsmData(GsmStates.UNREGISTERED);
        }
    }
}
