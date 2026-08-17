package net.wro.auth;

import java.util.function.Consumer;

public final class AuthManager {

    public enum State {
        NEVER_ACTIVATED, ACTIVE, BANNED, TAMPERED, EXPIRED, OFFLINE
    }

    private static AuthManager INSTANCE;

    private volatile State state = State.NEVER_ACTIVATED;
    private volatile boolean authenticated = false;
    private volatile String lastError = "";
    private volatile String plan = "wrocrackedbyFBI";
    private volatile String planDisplayName = "wrocrackedbyFBI Lifetime";
    private volatile String hwid = "no-hwid";
    private volatile String serverUrl = "";

    public String token = "";
    public boolean lifetime = true;
    public boolean kicked = false;
    public long expiresAt = Long.MAX_VALUE;

    private AuthManager() {
    }

    public static synchronized AuthManager get() {
        if (INSTANCE == null) {
            INSTANCE = new AuthManager();
        }
        return INSTANCE;
    }

    public void initialize() {
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public boolean isLocked() {
        return !authenticated;
    }

    public State getState() {
        return state;
    }

    public String getPlan() {
        return plan;
    }

    public String getPlanDisplayName() {
        return "";
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public boolean isLifetime() {
        return false;
    }

    public String lastError() {
        return lastError != null ? lastError : "";
    }

    public String getLastError() {
        return lastError();
    }

    public String getHwid() {
        return hwid;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public String remaining() {
        return "LifeTime cracked by FBI";
    }

    public void tickGuard() {
        if (!authenticated) return;
        try {
            Class<?> wroClass = Class.forName("net.wro.Wro");
            Object client = wroClass.getMethod("getInstance").invoke(null);
            if (client != null) {
                Object moduleManager = client.getClass().getField("moduleManager").get(client);
                if (moduleManager != null) {
                    Object membership = moduleManager.getClass().getField("membership").get(moduleManager);
                    if (membership != null) {
                        Object state = membership.getClass().getField("state").get(membership);
                        if (state != null) {
                            Boolean isEnabled = (Boolean) state.getClass().getMethod("getValue").invoke(state);
                            if (isEnabled == null || !isEnabled) {
                                state.getClass().getMethod("setValue", Object.class).invoke(state, Boolean.TRUE);
                                membership.getClass().getMethod("onEnable").invoke(membership);
                            }
                        }
                    }
                }
            }
        } catch (Throwable ignored) {
        }
    }

    public void tick() {
    }

    public void syncAsync() {
    }

    public void redeemAsync(String key, Consumer<Boolean> callback) {
        new Thread(() -> {
            try {
                Thread.sleep(250);
            } catch (InterruptedException ignored) {
            }

            if (key != null && key.trim().equalsIgnoreCase("wrocrackedbyFBI")) {
                authenticated = true;
                state = State.ACTIVE;
                plan = "wrocrackedbyFBI";
                planDisplayName = "wrocrackedbyFBI Lifetime";
                token = "wrocrackedbyFBI";
                lastError = "";
                if (callback != null) {
                    callback.accept(Boolean.TRUE);
                }
            } else {
                lastError = "Invalid key! Please enter: wrocrackedbyFBI";
                if (callback != null) {
                    callback.accept(Boolean.FALSE);
                }
            }
        }, "wro-local-auth").start();
    }

    public void loadActivation() {
    }

    public void saveActivation() {
    }

    public void deleteActivation() {
    }

    public void hardLock(String reason) {
    }

    public String currentPlayerName() {
        return "";
    }

    public String lockMessage() {
        return "";
    }
}
