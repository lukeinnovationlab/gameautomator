# gameautomator
An automator that can play games.

To run:
adb shell am instrument -w -r -e debug false -e class com.lukeinnovationlab.gameautomator.PvZ2InsideTest_Priv com.lukeinnovationlab.gameautomator.test/android.support.test.runner.AndroidJUnitRunner
