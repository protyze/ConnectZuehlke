package ch.zuehlke.fullstack.ConnectZuehlke.domain;

public enum Location {

    Bern("bern"), Eschborn("eschborn"), Hamburg("hamburg"), Hannover("hannover"), Hong_Kong("hong kong"),
    London("london"), Manchester("manchester"), Muenchen("münchen"),
    New_Belgrade("belgrade"), Schlieren("schlieren"), Singapore("singapore"),
    Sofia("sofia"), Stuttgart("stuttgart"), Wien("wien");

    private String cityName;

    Location(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }
}