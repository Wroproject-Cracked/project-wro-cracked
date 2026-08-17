package net.wro.auth;

public final class Hwid {

    private static final String HWID = "no-hwid";

    private Hwid() {}

    public static String get()    { return HWID; }
    public static String masked() { return "****"; }
}
