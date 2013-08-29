boolean(no).
boolean(yes).

device('BLUETOOTH').
device('HDMI').
device('NETWORK').
device('POWER').

event('CONNECTED').
event('DISCONNECTED').
event('FAILED').

led(0).
led(1).
led(2).
led(3).

ledCtl('FLASH').
ledCtl('OFF').
ledCtl('ON').


:- modeh(1, translateDeviceEvent(+device, +event, +led, +ledCtl, +boolean, +boolean))?

:- translateDeviceEvent('POWER', 'CONNECTED', _, _, _, _).
translateDeviceEvent('POWER', 'DISCONNECTED', 0, 'FLASH', yes, yes).
translateDeviceEvent('POWER', 'FAILED', 0, 'FLASH', yes, yes).
translateDeviceEvent('NETWORK', 'CONNECTED', 1, 'ON', no, no).
translateDeviceEvent('NETWORK', 'DISCONNECTED', 1, 'OFF', no, no).
translateDeviceEvent('NETWORK', 'FAILED', 1, 'FLASH', yes, no).
translateDeviceEvent('BLUETOOTH', 'CONNECTED', 2, 'ON', no, no).
translateDeviceEvent('BLUETOOTH', 'DISCONNECTED', 2, 'OFF', no, no).
translateDeviceEvent('BLUETOOTH', 'FAILED', 2, 'FLASH', yes, no).
translateDeviceEvent('HDMI', 'CONNECTED', 3, 'ON', no, no).
translateDeviceEvent('HDMI', 'DISCONNECTED', 3, 'OFF', no, no).
translateDeviceEvent('HDMI', 'FAILED', 3, 'FLASH', yes, no).
