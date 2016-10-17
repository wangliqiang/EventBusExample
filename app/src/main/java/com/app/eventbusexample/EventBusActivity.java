package com.app.eventbusexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.eventbusexample.eventbus.EventBusData;
import com.app.eventbusexample.rxbus.RxBus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventBusActivity extends AppCompatActivity {

    @Bind(R.id.event_click)
    Button eventClick;
    @Bind(R.id.eventbus_text)
    TextView eventbusText;
    @Bind(R.id.toPage2)
    Button toPage2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @OnClick({R.id.event_click,R.id.toPage2})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.event_click:
                Log.e("time", System.currentTimeMillis() + "");
                EventBus.getDefault().post(new EventBusData("hello EventBus", "1"));
                finish();
                break;
            case R.id.toPage2:
              startActivity(new Intent(this,EventBusSecondActivity.class));
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBus(EventBusData eventData) {
        Log.e("time",System.currentTimeMillis()+"");
        eventbusText.setText("EventBus---页面2:"+eventData.getMsg());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
