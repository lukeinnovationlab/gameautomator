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
 * Test Calculator.
 */
@RunWith(AndroidJUnit4.class)
public class CalculatorTest {

    private static final int LAUNCH_TIMEOUT = 5000;

    private static final String PACKAGE_CALCULATOR = "com.android.calculator2";

    private UiDevice mDevice;
    private int mDisplayHeight;
    private int mDisplayWidth;

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
                (PACKAGE_CALCULATOR);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(PACKAGE_CALCULATOR).depth(0)), LAUNCH_TIMEOUT);
    }

    @After
    public void closeMainActivity() {
        try {
            int centerX = mDisplayWidth / 2;
            int centerY = mDisplayHeight / 2;
            int rightCenterX = mDisplayWidth;
            int rightCenterY = mDisplayHeight / 2;

            mDevice.pressRecentApps();
            assertTrue(mDevice.wait(Until.gone(By.pkg(PACKAGE_CALCULATOR).depth(0)),
                    LAUNCH_TIMEOUT));
            assertTrue(mDevice.swipe(centerX, centerY, rightCenterX, rightCenterY, 10));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void calculator_additionBasic() {

    }

    @Test
    public void calculator_subtractionBasic() {

    }

    @Test
    public void calculator_multiplicationBasic() {

    }

    @Test
    public void calculator_divisionBasic() {

    }
}
