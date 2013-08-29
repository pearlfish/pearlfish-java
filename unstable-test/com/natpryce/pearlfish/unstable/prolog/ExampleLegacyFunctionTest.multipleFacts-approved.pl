device('BLUETOOTH').
device('HDMI').
device('NETWORK').
device('POWER').

event('CONNECTED').
event('DISCONNECTED').
event('FAILED').

led(led_0).
led(led_1).
led(led_2).
led(led_3).

ledCtl('FLASH').
ledCtl('OFF').
ledCtl('ON').


:- modeh(1, ledId(+device, +event, +led))?

ledId('POWER', 'DISCONNECTED', led_0).
ledId('POWER', 'FAILED', led_0).
ledId('NETWORK', 'CONNECTED', led_1).
ledId('NETWORK', 'DISCONNECTED', led_1).
ledId('NETWORK', 'FAILED', led_1).
ledId('BLUETOOTH', 'CONNECTED', led_2).
ledId('BLUETOOTH', 'DISCONNECTED', led_2).
ledId('BLUETOOTH', 'FAILED', led_2).
ledId('HDMI', 'CONNECTED', led_3).
ledId('HDMI', 'DISCONNECTED', led_3).
ledId('HDMI', 'FAILED', led_3).

:- modeh(1, ledAction(+device, +event, +ledCtl))?

ledAction('POWER', 'DISCONNECTED', 'FLASH').
ledAction('POWER', 'FAILED', 'FLASH').
ledAction('NETWORK', 'CONNECTED', 'ON').
ledAction('NETWORK', 'DISCONNECTED', 'OFF').
ledAction('NETWORK', 'FAILED', 'FLASH').
ledAction('BLUETOOTH', 'CONNECTED', 'ON').
ledAction('BLUETOOTH', 'DISCONNECTED', 'OFF').
ledAction('BLUETOOTH', 'FAILED', 'FLASH').
ledAction('HDMI', 'CONNECTED', 'ON').
ledAction('HDMI', 'DISCONNECTED', 'OFF').
ledAction('HDMI', 'FAILED', 'FLASH').

:- modeh(1, beep(+device, +event))?

beep('POWER', 'DISCONNECTED').
beep('POWER', 'FAILED').
:- beep('NETWORK', 'CONNECTED').
:- beep('NETWORK', 'DISCONNECTED').
beep('NETWORK', 'FAILED').
:- beep('BLUETOOTH', 'CONNECTED').
:- beep('BLUETOOTH', 'DISCONNECTED').
beep('BLUETOOTH', 'FAILED').
:- beep('HDMI', 'CONNECTED').
:- beep('HDMI', 'DISCONNECTED').
beep('HDMI', 'FAILED').

:- modeh(1, shutdown(+device, +event))?

shutdown('POWER', 'DISCONNECTED').
shutdown('POWER', 'FAILED').
:- shutdown('NETWORK', 'CONNECTED').
:- shutdown('NETWORK', 'DISCONNECTED').
:- shutdown('NETWORK', 'FAILED').
:- shutdown('BLUETOOTH', 'CONNECTED').
:- shutdown('BLUETOOTH', 'DISCONNECTED').
:- shutdown('BLUETOOTH', 'FAILED').
:- shutdown('HDMI', 'CONNECTED').
:- shutdown('HDMI', 'DISCONNECTED').
:- shutdown('HDMI', 'FAILED').
