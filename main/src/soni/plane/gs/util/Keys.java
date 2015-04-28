package soni.plane.gs.util;

import java.awt.event.KeyEvent;

public class Keys {
    /* definitions for key names */
    public static final int ESCAPE =       256;
    public static final int ENTER =        257;
    public static final int TAB =          258;
    public static final int BACKSPACE =    259;
    public static final int INSERT =       260;
    public static final int DELETE =       261;
    public static final int RIGHT_ARROW =  262;
    public static final int LEFT_ARROW =   263;
    public static final int DOWN_ARROW =   264;
    public static final int UP_ARROW =     265;
    public static final int PAGE_UP =      266;
    public static final int PAGE_DOWN =    267;
    public static final int HOME =         268;
    public static final int END =          269;

    public static final int CAPSLOCK =     280;
    public static final int SCROLL_LOCK =  281;
    public static final int NUM_LOCK =     282;
    public static final int PRINTSCREEN =  283;
    public static final int PAUSE =        284;

    public static final int F1 =  290;
    public static final int F2 =  291;
    public static final int F3 =  292;
    public static final int F4 =  293;
    public static final int F5 =  294;
    public static final int F6 =  295;
    public static final int F7 =  296;
    public static final int F8 =  297;
    public static final int F9 =  298;
    public static final int F10 = 299;
    public static final int F11 = 300;
    public static final int F12 = 301;

    public static final int NUMPAD0 =      320;
    public static final int NUMPAD1 =      321;
    public static final int NUMPAD2 =      322;
    public static final int NUMPAD3 =      323;
    public static final int NUMPAD4 =      324;
    public static final int NUMPAD5 =      325;
    public static final int NUMPAD6 =      326;
    public static final int NUMPAD7 =      327;
    public static final int NUMPAD8 =      328;
    public static final int NUMPAD9 =      329;

    public static final int DECIMAL =      330;
    public static final int DIVIDE =       331;
    public static final int MULTIPLY =     332;
    public static final int SUBTRACT =     333;
    public static final int ADD =          334;
    public static final int SEPARATOR =    335;
    public static final int CONTROL2 =     341;
    public static final int ALT =          342;
    public static final int SHIFT =        344;
    public static final int CONTROL =      345;
    public static final int ALTGR =        346;

    public static final int SPACE =        KeyEvent.VK_SPACE;       // 0x20

    public static final int COMMA =        KeyEvent.VK_COMMA;      // 0x2C
    public static final int MINUS =        KeyEvent.VK_MINUS;      // 0x2D
    public static final int PERIOD =       KeyEvent.VK_PERIOD;     // 0x2E
    public static final int SLASH =        KeyEvent.VK_SLASH;      // 0x2F

    public static final int NUM0 =    KeyEvent.VK_0;    // 0x30
    public static final int NUM1 =    KeyEvent.VK_1;
    public static final int NUM2 =    KeyEvent.VK_2;
    public static final int NUM3 =    KeyEvent.VK_3;
    public static final int NUM4 =    KeyEvent.VK_4;    // through
    public static final int NUM5 =    KeyEvent.VK_5;
    public static final int NUM6 =    KeyEvent.VK_6;
    public static final int NUM7 =    KeyEvent.VK_7;
    public static final int NUM8 =    KeyEvent.VK_8;
    public static final int NUM9 =    KeyEvent.VK_9;    // 0x39

    public static final int SEMICOLON =     KeyEvent.VK_SEMICOLON;      // 0x3B
    public static final int EQUALS =        KeyEvent.VK_EQUALS;         // 0x3D

    public static final int A = KeyEvent.VK_A;  // 0x41
    public static final int B = KeyEvent.VK_B;
    public static final int C = KeyEvent.VK_C;
    public static final int D = KeyEvent.VK_D;
    public static final int E = KeyEvent.VK_E;
    public static final int F = KeyEvent.VK_F;
    public static final int G = KeyEvent.VK_G;
    public static final int H = KeyEvent.VK_H;
    public static final int I = KeyEvent.VK_I;
    public static final int J = KeyEvent.VK_J;
    public static final int K = KeyEvent.VK_K;
    public static final int L = KeyEvent.VK_L;
    public static final int M = KeyEvent.VK_M;
    public static final int N = KeyEvent.VK_N;
    public static final int O = KeyEvent.VK_O;
    public static final int P = KeyEvent.VK_P;
    public static final int Q = KeyEvent.VK_Q;
    public static final int R = KeyEvent.VK_R;
    public static final int S = KeyEvent.VK_S;
    public static final int T = KeyEvent.VK_T;
    public static final int U = KeyEvent.VK_U;
    public static final int V = KeyEvent.VK_V;
    public static final int W = KeyEvent.VK_W;
    public static final int X = KeyEvent.VK_X;
    public static final int Y = KeyEvent.VK_Y;
    public static final int Z = KeyEvent.VK_Z;  // 0x5A

    public static final int OPEN_BRACKET =  KeyEvent.VK_OPEN_BRACKET;   // 0x5B
    public static final int BACKSLASH =     KeyEvent.VK_BACK_SLASH;     // 0x5C
    public static final int CLOSE_BRACKET = KeyEvent.VK_CLOSE_BRACKET;  // 0x5D

    /* button press states
     * bit 0 = pressed
     * bit 1 = held */
    private static byte[] states = new byte[ALTGR];

    public static void event(int key, int action) {
        switch (action){
            /* set key as pressed */
            case 0:
                states[key] = 2;
                break;

            /* set key as not pressed */
            case 1:
                states[key] = 0;
                break;
        }
    }

    public static void step() {
        /* loop for all the buttons */
        for(int i = 0;i < states.length;i ++){
            /* clear pressed state */
            states[i] &= 2;
        }
    }

    /* check if button is pressed */
    public static boolean isPressed(int key){
        return (states[key] & 1) != 0;
    }

    /* check if button is held */
    public static boolean isHeld(int key){
        return (states[key] & 2) != 0;
    }
}
