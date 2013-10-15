package info.pearlfish;

import org.junit.Test;

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
    public void canBeSpecialised() {
        assertThat(FormatType.of("text","json").specialised("geojson"), equalTo(FormatType.of("text","json", "geojson")));
        assertThat(FormatType.of("a").specialised("b", "c", "d"), equalTo(FormatType.of("a","b", "c", "d")));
    }

    @Test
    public void canBeGeneralised() {
        assertTrue(FormatType.of("text", "json", "geojson").canBeGeneralised());
        assertThat(FormatType.of("text","json","geojson").generalised(), equalTo(FormatType.of("text","json")));

        assertTrue(FormatType.of("text", "json").canBeGeneralised());
        assertThat(FormatType.of("text","json").generalised(), equalTo(FormatType.of("text")));

        assertFalse(FormatType.of("text").canBeGeneralised());
    }

    @Test
    public void hasStringForm() {
        assertThat(FormatType.of("text").toString(), equalTo("text"));
        assertThat(FormatType.of("text", "json").toString(), equalTo("text.json"));
        assertThat(FormatType.of("text", "json", "geojson").toString(), equalTo("text.json.geojson"));
    }

    @Test
    public void canBeParsed() {
        assertCanRoundtrip(FormatType.of("text"));
        assertCanRoundtrip(FormatType.of("text", "json"));
        assertCanRoundtrip(FormatType.of("text", "json", "geojson"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void parsingRejectsInvalidTypeSyntax() {
        FormatType.valueOf("");
    }

    private void assertCanRoundtrip(FormatType t) {
        assertThat(FormatType.valueOf(t.toString()), equalTo(t));
    }

}
