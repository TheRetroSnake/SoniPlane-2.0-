package soni.plane.api.exceptions;

import soni.plane.gs.Main;

public final class plugin {
    /* displays error when plugin with unexpected type was met
    * FATAL */
    public static void TypeError(String expected, String found, String caller){
        try {
            throw new IllegalAccessException("Cannot load plugin with type '"+ found +"' when type '"+ expected +"' is expected!");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        /* Fatal error, close tool */
        Main.exit(plugin.class +"TypeError("+ caller +")");
    }
}
