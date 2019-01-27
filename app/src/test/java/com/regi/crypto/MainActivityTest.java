package com.regi.crypto;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.regi.crypto.ui.MainActivity;
import com.regi.crypto.ui.MainViewModel;
import com.robinhood.ticker.TickerView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(shadows = ShadowResourcesCompat.class)
public class MainActivityTest {

    private MainActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull( activity );
    }

    @Test
    public void shouldShowAdvancedChart() {
        Button btn = activity.findViewById(R.id.btn_advanced);
        btn.performClick();
        assertEquals("Basic", btn.getText().toString());
        btn.performClick();
        assertEquals("Advanced", btn.getText().toString());
    }

    @Test
    public void shouldShowHiddenText(){
        TickerView ticker = activity.findViewById(R.id.tickerView);
        ticker.performClick();
        TextView hiddenView = activity.findViewById(R.id.tv_ath_text);
        assertEquals("To the moon!", hiddenView.getText().toString());
    }

}
