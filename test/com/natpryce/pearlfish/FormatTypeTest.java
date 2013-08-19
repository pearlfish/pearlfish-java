package com.natpryce.pearlfish;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FormatTypeTest {
    @Test
    public void comparesForEquality() {
        assertThat(FormatType.of("text"), equalTo(FormatType.of("text")));
        assertThat(FormatType.of("text"), not(equalTo(FormatType.of("jpeg"))));

        assertThat(FormatType.of("text","json"), equalTo(FormatType.of("text","json")));
        assertThat(FormatType.of("text","json"), not(equalTo(FormatType.of("text"))));
        assertThat(FormatType.of("text","json"), not(equalTo(FormatType.of("json"))));
        assertThat(FormatType.of("text","json"), not(equalTo(FormatType.of("json", "text"))));

        assertFalse(FormatType.of("text", "json").equals(null));
    }

    @Test
    public void hasStringForm() throws IOException {
        assertThat(FormatType.of("text").toString(), equalTo("text"));
        assertThat(FormatType.of("text", "json").toString(), equalTo("text.json"));
        assertThat(FormatType.of("text", "json", "geojson").toString(), equalTo("text.json.geojson"));
    }

    @Test
    public void canBeGeneralised() throws IOException {
        assertTrue(FormatType.of("text", "json", "geojson").canBeGeneralised());
        assertThat(FormatType.of("text","json","geojson").generalised(), equalTo(FormatType.of("text","json")));

        assertTrue(FormatType.of("text", "json").canBeGeneralised());
        assertThat(FormatType.of("text","json").generalised(), equalTo(FormatType.of("text")));

        assertFalse(FormatType.of("text").canBeGeneralised());
    }

}
