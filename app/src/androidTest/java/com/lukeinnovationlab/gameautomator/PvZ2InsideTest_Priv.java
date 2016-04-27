package com.lukeinnovationlab.gameautomator;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Test PvZ2 assuming PvZ2 has been launched.
 */
@RunWith(AndroidJUnit4.class)
public class PvZ2InsideTest_Priv {
    private static final String TAG = "PVZ2_TEST";

    private static final String PVZ2_PACKAGE = "com.ea.game.pvz2_na";

    private static final int TIMES_RUN = 10;

    private static final int WAIT_TIMEOUT = 5 * 1000;
    private static final int BTN_PLAY_PRE_CLICK_TIMEOUT = 30 * 1000;
    private static final int BTN_PLAY_POST_CLICK_TIMEOUT = 10 * 1000;
    private static final int BTN_COINS_POST_CLICK_TIMEOUT = 5 * 1000;
    private static final int BTN_COINS_YES_POST_CLICK_TIMEOUT = 45 * 1000;
    private static final int BACK_POST_AD_POST_CLICK_TIMEOUT = 7 * 1000;
    private static final int BTN_COINS_CONT_POST_CLICK_TIMEOUT = 7 * 1000;

    private static final UiDevice UI_DEVICE;
    private static final int DISPLAY_HEIGHT;
    private static final int DISPLAY_WIDTH;

    private static final int PVZ2_VIEW_WIDTH;
    private static final int PVZ2_VIEW_HEIGHT;
    private static final int PVZ2_VIEW_WIDTH_PHY = 1120; // mm * 10
    private static final int PVZ2_VIEW_HEIGHT_PHY = 680; // mm * 10

    static {
        // Initialize UiDevice instance
        UI_DEVICE = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Initialize display constants
        DISPLAY_WIDTH = UI_DEVICE.getDisplayWidth();
        DISPLAY_HEIGHT = UI_DEVICE.getDisplayHeight();
        PVZ2_VIEW_WIDTH = DISPLAY_WIDTH;
        PVZ2_VIEW_HEIGHT = DISPLAY_HEIGHT;
    }

    // PVZ2 Button PLAY
    private static final int PVZ2_BTN_PLAY_CLICK_X_PHY = 640; // mm * 10
    private static final int PVZ2_BTN_PLAY_CLICK_Y_PHY = 640; // mm * 10

    private static final int PVZ2_BTN_PLAY_RADIO_X = (int) (PVZ2_BTN_PLAY_CLICK_X_PHY
            * 1000 / PVZ2_VIEW_WIDTH_PHY);
    private static final int PVZ2_BTN_PLAY_CLICK_X = PVZ2_VIEW_WIDTH *
            PVZ2_BTN_PLAY_RADIO_X / 1000;
    private static final int PVZ2_BTN_PLAY_RADIO_Y = (int) (PVZ2_BTN_PLAY_CLICK_Y_PHY
            * 1000 / PVZ2_VIEW_HEIGHT_PHY);
    private static final int PVZ2_BTN_PLAY_CLICK_Y = PVZ2_VIEW_HEIGHT *
            PVZ2_BTN_PLAY_RADIO_Y / 1000;

    // PVZ2 Button Coins
    private static final int PVZ2_BTN_COINS_CLICK_X_PHY = 1020; // mm * 10
    private static final int PVZ2_BTN_COINS_CLICK_Y_PHY = 85; // mm * 10

    private static final int PVZ2_BTN_COINS_RADIO_X = (int) (PVZ2_BTN_COINS_CLICK_X_PHY * 1000 /
            PVZ2_VIEW_WIDTH_PHY);
    private static final int PVZ2_BTN_COINS_CLICK_X = PVZ2_VIEW_WIDTH * PVZ2_BTN_COINS_RADIO_X /
            1000;
    private static final int PVZ2_BTN_COINS_RADIO_Y = (int) (PVZ2_BTN_COINS_CLICK_Y_PHY * 1000 /
            PVZ2_VIEW_HEIGHT_PHY);
    private static final int PVZ2_BTN_COINS_CLICK_Y = PVZ2_VIEW_HEIGHT * PVZ2_BTN_COINS_RADIO_Y /
            1000;

    // PVZ2 Button Yes for Coins
    private static final int PVZ2_BTN_COINS_YES_CLICK_X_PHY = 750; // mm * 10
    private static final int PVZ2_BTN_COINS_YES_CLICK_Y_PHY = 410; // mm * 10

    private static final int PVZ2_BTN_COINS_YES_RADIO_X = (int) (PVZ2_BTN_COINS_YES_CLICK_X_PHY *
            1000 / PVZ2_VIEW_WIDTH_PHY);
    private static final int PVZ2_BTN_COINS_YES_CLICK_X = PVZ2_VIEW_WIDTH *
            PVZ2_BTN_COINS_YES_RADIO_X / 1000;
    private static final int PVZ2_BTN_COINS_YES_RADIO_Y = (int) (PVZ2_BTN_COINS_YES_CLICK_Y_PHY *
            1000 / PVZ2_VIEW_HEIGHT_PHY);
    private static final int PVZ2_BTN_COINS_YES_CLICK_Y = PVZ2_VIEW_HEIGHT *
            PVZ2_BTN_COINS_YES_RADIO_Y / 1000;

    // PVZ2 Button Continue done Coins
    private static final int PVZ2_BTN_COINS_CONT_CLICK_X_PHY = 600; // mm * 10
    private static final int PVZ2_BTN_COINS_CONT_CLICK_Y_PHY = 550; // mm * 10

    private static final int PVZ2_BTN_COINS_CONT_RADIO_X = (int) (PVZ2_BTN_COINS_CONT_CLICK_X_PHY
            * 1000 / PVZ2_VIEW_WIDTH_PHY);
    private static final int PVZ2_BTN_COINS_CONT_CLICK_X = PVZ2_VIEW_WIDTH *
            PVZ2_BTN_COINS_CONT_RADIO_X / 1000;
    private static final int PVZ2_BTN_COINS_CONT_RADIO_Y = (int) (PVZ2_BTN_COINS_CONT_CLICK_Y_PHY
            * 1000 / PVZ2_VIEW_HEIGHT_PHY);
    private static final int PVZ2_BTN_COINS_CONT_CLICK_Y = PVZ2_VIEW_HEIGHT *
            PVZ2_BTN_COINS_CONT_RADIO_Y / 1000;

    final private Object mLock = new Object();

    @Before
    public void setup() {
    }

    @After
    public void tearDown() {
        closeMainActivity();
    }

    @Test
    public void pvz2_test() {
        Log.i(TAG, "To start pvz2_test");

        while (true) {
            restartMainActivity();

            startPlay();

            int i = TIMES_RUN;
            while (i-- > 0) {
                runOnce(i);
            }
        }
    }

    private void startPlay() {
        Log.i(TAG, "To start play");

        // Wait for play to appear
        testWait(BTN_PLAY_PRE_CLICK_TIMEOUT);

        Log.i(TAG, "To click PVZ2_BTN_PLAY");
        UI_DEVICE.click(PVZ2_BTN_PLAY_CLICK_X, PVZ2_BTN_PLAY_CLICK_Y);

        testWait(BTN_PLAY_POST_CLICK_TIMEOUT);
    }

    private void runOnce(int testNo) {
        Log.i(TAG, "To run test #" + testNo);

        long timeStart = System.currentTimeMillis();

        testWait(WAIT_TIMEOUT);

        Log.i(TAG, "to click PVZ2_BTN_COINS");
        UI_DEVICE.click(PVZ2_BTN_COINS_CLICK_X, PVZ2_BTN_COINS_CLICK_Y);

        testWait(BTN_COINS_POST_CLICK_TIMEOUT);

        Log.i(TAG, "to click PVZ2_BTN_COINS_YES");
        UI_DEVICE.click(PVZ2_BTN_COINS_YES_CLICK_X, PVZ2_BTN_COINS_YES_CLICK_Y);

        testWait(BTN_COINS_YES_POST_CLICK_TIMEOUT);

        Log.i(TAG, "to click PVZ2_BTN_COINS_BACK");
        UI_DEVICE.pressBack();

        testWait(BACK_POST_AD_POST_CLICK_TIMEOUT);

        Log.i(TAG, "to click PVZ2_BTN_COINS_CONTINUE");
        UI_DEVICE.click(PVZ2_BTN_COINS_CONT_CLICK_X, PVZ2_BTN_COINS_CONT_CLICK_Y);

        testWait(BTN_COINS_CONT_POST_CLICK_TIMEOUT);

        long timeEnd = System.currentTimeMillis();
        Log.i(TAG, "Test #" + testNo + " lasted " + ((timeEnd - timeStart) / 1000) + " seconds");
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

    private void restartMainActivity() {
        Log.i(TAG, "To restart main activity");

        closeMainActivity();
        startMainActivityFromHomeScreen();
    }

    private void startMainActivityFromHomeScreen() {
        // Start from the home screen
        UI_DEVICE.pressHome();

        // Wait for launcher
        final String launcherPackage = UI_DEVICE.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        UI_DEVICE.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), WAIT_TIMEOUT);

        // Launch the app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(PVZ2_PACKAGE);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        UI_DEVICE.wait(Until.hasObject(By.pkg(PVZ2_PACKAGE).depth(0)), WAIT_TIMEOUT);
    }

    private void closeMainActivity() {
        try {
            int centerX = DISPLAY_WIDTH / 2;
            int centerY = DISPLAY_HEIGHT / 2;
            int rightCenterX = DISPLAY_WIDTH;
            int rightCenterY = DISPLAY_HEIGHT / 2;

            UI_DEVICE.pressRecentApps();
            assertTrue(UI_DEVICE.wait(Until.gone(By.pkg(PVZ2_PACKAGE).depth(0)), WAIT_TIMEOUT));
            assertTrue(UI_DEVICE.swipe(centerX, centerY, rightCenterX, rightCenterY, 10));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
