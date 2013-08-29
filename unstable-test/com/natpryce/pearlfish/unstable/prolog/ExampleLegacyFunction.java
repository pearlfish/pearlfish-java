package com.natpryce.pearlfish.unstable.prolog;

public class ExampleLegacyFunction {
    public static enum Device {
        POWER,
        NETWORK,
        BLUETOOTH,
        HDMI
    }

    public static enum DeviceEvent {
        CONNECTED,
        DISCONNECTED,
        FAILED
    }

    public static enum LEDCtl {
        ON,
        OFF,
        FLASH,
    }

    static class Action {
        int led;
        LEDCtl ledCtl;
        boolean beep = false;
        boolean shutdown = false;
    }

    public static Action translateDeviceEvent(Device source, DeviceEvent event) {
        Action action = new Action();

        switch (source) {
            case POWER:
                switch (event) {
                    case DISCONNECTED:
                    case FAILED:
                        action.led = 0;
                        action.ledCtl = LEDCtl.FLASH;
                        action.beep = true;
                        action.shutdown = true;
                        break;
                    case CONNECTED:
                        throw new IllegalArgumentException("should never get a power connected event");
                }
                break;

            case NETWORK:
                switch (event) {
                    case CONNECTED:
                        action.led = 1;
                        action.ledCtl = LEDCtl.ON;
                        break;
                    case DISCONNECTED:
                        action.led = 1;
                        action.ledCtl = LEDCtl.OFF;
                        break;
                    case FAILED:
                        action.led = 1;
                        action.ledCtl = LEDCtl.FLASH;
                        action.beep = true;
                }
                break;

            case BLUETOOTH:
                switch (event) {
                    case CONNECTED:
                        action.led = 2;
                        action.ledCtl = LEDCtl.ON;
                        break;
                    case DISCONNECTED:
                        action.led = 2;
                        action.ledCtl = LEDCtl.OFF;
                        break;
                    case FAILED:
                        action.led = 2;
                        action.ledCtl = LEDCtl.FLASH;
                        action.beep = true; // can't talk to USB controller, alert user
                }
                break;

            case HDMI:
                switch (event) {
                    case CONNECTED:
                        action.led = 3;
                        action.ledCtl = LEDCtl.ON;
                        break;
                    case DISCONNECTED:
                        action.led = 3;
                        action.ledCtl = LEDCtl.OFF;
                        break;
                    case FAILED:
                        action.led = 3;
                        action.ledCtl = LEDCtl.FLASH;
                        action.beep = true;
                }
                break;
        }

        return action;
    }
}
