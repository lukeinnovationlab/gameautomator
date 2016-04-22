package com.lukeinnovationlab.gameautomator;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Test PvZ2.
 */
@RunWith(AndroidJUnit4.class)
public class PvZ2Test {
    private static final int LAUNCH_TIMEOUT = 5000;

    private static final String PACKAGE_PVZ2 = "com.ea.game.pvz2_na";

    private UiDevice mDevice;
    private int mDisplayHeight;
    private int mDisplayWidth;

    final private Object mLock = new Object();

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDisplayHeight = mDevice.getDisplayHeight();
        mDisplayWidth = mDevice.getDisplayWidth();

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch the app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage
                (PACKAGE_PVZ2);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(PACKAGE_PVZ2).depth(0)), LAUNCH_TIMEOUT);
    }

    @After
    public void closeMainActivity() {
        try {
            int centerX = mDisplayWidth / 2;
            int centerY = mDisplayHeight / 2;
            int rightCenterX = mDisplayWidth;
            int rightCenterY = mDisplayHeight / 2;

            mDevice.pressRecentApps();
            assertTrue(mDevice.wait(Until.gone(By.pkg(PACKAGE_PVZ2).depth(0)),
                    LAUNCH_TIMEOUT));
            assertTrue(mDevice.swipe(centerX, centerY, rightCenterX, rightCenterY, 10));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void pvz2_start() {
        try {
            synchronized (mLock) {
                mLock.wait(LAUNCH_TIMEOUT);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
