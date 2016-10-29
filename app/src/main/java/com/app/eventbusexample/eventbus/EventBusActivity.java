package com.app.eventbusexample.eventbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.eventbusexample.utils.Log;
import com.app.eventbusexample.R;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventBusActivity extends AppCompatActivity {

    @Bind(R.id.event_click)
    Button eventClick;
    @Bind(R.id.eventbus_text)
    TextView eventbusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @OnClick({R.id.event_click})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.event_click:
                Log.e("time", System.currentTimeMillis() + "");
                EventBus.getDefault().post(new EventBusData("hello EventBus", "1"),"flag");
                break;
            default:
                break;
        }
    }

    @Subscriber(tag = "",mode = ThreadMode.POST)
    public void getEventBus(EventBusData eventData) {
        Log.e("time",System.currentTimeMillis()+"");
        eventbusText.setText("EventBus:"+eventData.getMsg());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
