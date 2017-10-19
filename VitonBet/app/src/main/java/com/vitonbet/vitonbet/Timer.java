/**
 * Name: VitonBet
 * Type: Group Assignment
 * Class: Timer
 * Revision: 1.0
 * Notes: TBA
 */

package com.vitonbet.vitonbet;

import android.app.Application;
import android.os.SystemClock;


public class Timer extends Application
{
    //TODO
    //This acts as a dummy, simulate a call start later.
    @Override
    public void onCreate()
    {
        super.onCreate();
        SystemClock.sleep(2000);
    }
}
