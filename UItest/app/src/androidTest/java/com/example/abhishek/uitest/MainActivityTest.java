package com.example.abhishek.uitest;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void UIdeviceTest() throws RemoteException, UiObjectNotFoundException, InterruptedException {
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        UiSelector uiSelector = new UiSelector();
        uiSelector.className("android.widgets.Button").text("ODD").clickable(true);

        UiObject object = uiDevice.findObject(uiSelector);
        object.click();

        Thread.sleep(2000);
    }

}
