package com.lukeinnovationlab.gameautomator;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test PvZ2 assuming PvZ2 has been launched.
 */
@RunWith(AndroidJUnit4.class)
public class PvZ2InsideTest_LG_D820 {
    private static final int LAUNCH_TIMEOUT = 5 * 1000;
    private static final int BTN_COINS_POST_CLICK_TIMEOUT = 5 * 1000;
    private static final int BTN_COINS_YES_POST_CLICK_TIMEOUT = 60 * 1000;

    private static final String PVZ2_PACKAGE = "com.ea.game.pvz2_na";

    private static final UiDevice UI_DEVICE;
    private static final int DISPLAY_HEIGHT;
    private static final int DISPLAY_WIDTH;

    private static final int PVZ2_VIEW_WIDTH;
    private static final int PVZ2_VIEW_HEIGHT;
    private static final int PVZ2_VIEW_WIDTH_PHY = 1015;
    private static final int PVZ2_VIEW_HEIGHT_PHY = 615;

    static {
        // Initialize UiDevice instance
        UI_DEVICE = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Initialize display constants
        DISPLAY_WIDTH = UI_DEVICE.getDisplayWidth();
        DISPLAY_HEIGHT = UI_DEVICE.getDisplayHeight();
        PVZ2_VIEW_WIDTH = DISPLAY_WIDTH;
        PVZ2_VIEW_HEIGHT = DISPLAY_HEIGHT;
    }

    private static final int PVZ2_BTN_COINS_RADIO_X = (int) (900 * 1000 / PVZ2_VIEW_WIDTH_PHY);
    private static final int PVZ2_BTN_COINS_CLICK_X = PVZ2_VIEW_WIDTH * PVZ2_BTN_COINS_RADIO_X /
            1000;
    private static final int PVZ2_BTN_COINS_RADIO_Y = (int) (70 * 1000 / PVZ2_VIEW_HEIGHT_PHY);
    private static final int PVZ2_BTN_COINS_CLICK_Y = PVZ2_VIEW_HEIGHT * PVZ2_BTN_COINS_RADIO_Y /
            1000;

    private static final int PVZ2_BTN_COINS_YES_RADIO_X = (int) (680 * 1000 / PVZ2_VIEW_WIDTH_PHY);
    private static final int PVZ2_BTN_COINS_YES_CLICK_X = PVZ2_VIEW_WIDTH *
            PVZ2_BTN_COINS_YES_RADIO_X / 1000;
    private static final int PVZ2_BTN_COINS_YES_RADIO_Y = (int) (370 * 1000 / PVZ2_VIEW_HEIGHT_PHY);
    private static final int PVZ2_BTN_COINS_YES_CLICK_Y = PVZ2_VIEW_HEIGHT *
            PVZ2_BTN_COINS_YES_RADIO_Y / 1000;

    final private Object mLock = new Object();

    @Before
    public void setup() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void pvz2_start() {
        testWait(LAUNCH_TIMEOUT);

        UI_DEVICE.click(PVZ2_BTN_COINS_CLICK_X, PVZ2_BTN_COINS_CLICK_Y);

        testWait(BTN_COINS_POST_CLICK_TIMEOUT);

        UI_DEVICE.click(PVZ2_BTN_COINS_YES_CLICK_X, PVZ2_BTN_COINS_YES_CLICK_Y);

        testWait(BTN_COINS_YES_POST_CLICK_TIMEOUT);
    }

    private void testWait(long time) {
        try {
            synchronized (mLock) {
                mLock.wait(time);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
