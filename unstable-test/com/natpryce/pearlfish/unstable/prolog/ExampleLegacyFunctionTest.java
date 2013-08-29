package com.natpryce.pearlfish.unstable.prolog;

import com.natpryce.pearlfish.adaptor.junit.ApprovalRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static com.natpryce.pearlfish.internal.InternalApprovals.unstableSelfTestApprover;
import static com.natpryce.pearlfish.unstable.prolog.PrologFormat.PROLOG;

public class ExampleLegacyFunctionTest {
    @Rule
    public ApprovalRule<FactBase> approval = unstableSelfTestApprover(PROLOG);

    @Test
    public void exhaustiveTest() throws IOException {
        FactBase factBase = new FactBase();
        final Relation translateDeviceEventFacts = factBase.newFact("translateDeviceEvent", "device", "event", "led", "ledCtl", "boolean", "boolean");

        for (ExampleLegacyFunction.Device device : ExampleLegacyFunction.Device.values()) {
            for (ExampleLegacyFunction.DeviceEvent event : ExampleLegacyFunction.DeviceEvent.values()) {
                try {
                    ExampleLegacyFunction.Action action = ExampleLegacyFunction.translateDeviceEvent(device, event);

                    translateDeviceEventFacts.declare(true,
                            device, event,
                            action.led, action.ledCtl, action.beep, action.shutdown);

                } catch (Exception e) {
                    translateDeviceEventFacts.declare(false,
                            device, event,
                            Fact.ANYTHING, Fact.ANYTHING, Fact.ANYTHING, Fact.ANYTHING);
                }
            }
        }

        approval.check(factBase);
    }

    @Test
    public void multipleFacts() throws IOException {
        FactBase factBase = new FactBase();
        final Relation ledId = factBase.newFact("ledId", "device", "event", "led");
        final Relation ledAction = factBase.newFact("ledAction", "device", "event", "ledCtl");
        final Relation beep = factBase.newFact("beep", "device", "event");
        final Relation shutdown = factBase.newFact("shutdown", "device", "event");

        for (ExampleLegacyFunction.Device device : ExampleLegacyFunction.Device.values()) {
            for (ExampleLegacyFunction.DeviceEvent event : ExampleLegacyFunction.DeviceEvent.values()) {
                try {
                    ExampleLegacyFunction.Action action = ExampleLegacyFunction.translateDeviceEvent(device, event);
                    ledId.declare(true, device, event, "led_" + action.led);
                    ledAction.declare(true, device, event, action.ledCtl);
                    beep.declare(action.beep, device, event);
                    shutdown.declare(action.shutdown, device, event);

                } catch (Exception e) {
                    // Todo
                }
            }
        }

        approval.check(factBase);
    }
}
