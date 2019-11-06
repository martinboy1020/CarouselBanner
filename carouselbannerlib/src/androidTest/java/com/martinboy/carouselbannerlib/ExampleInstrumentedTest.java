/*
 *  Copyright (c) 2019 Mattel, Inc. All rights reserved.
 *  All information and code contained herein is the property of Mattel, Inc.
 *  Any unauthorized use, storage, duplication, and redistribution of this
 *  material without written permission from Mattel, Inc. is strictly prohibited.
 *
 *
 */

package com.martinboy.carouselbannerlib;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.martinboy.carouselbannerlib.test", appContext.getPackageName());
    }
}
