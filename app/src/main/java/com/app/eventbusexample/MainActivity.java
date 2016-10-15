package com.app.eventbusexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.eventbusexample.eventbus.EventBusData;
import com.app.eventbusexample.rxbus.RxBus;
import com.app.eventbusexample.rxbus.RxBusData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.event_btn)
    Button eventBtn;
    @Bind(R.id.event_text)
    TextView eventText;

    private Observable<RxBusData> rxBusDataObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        getRxBus();
    }

    @OnClick({R.id.event_btn,R.id.rx_btn})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.event_btn:
                startActivity(new Intent(this,EventBusActivity.class));
                break;
            case R.id.rx_btn:
                startActivity(new Intent(this,RxBusActivity.class));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBus(EventBusData eventData) {
        Log.e("time",System.currentTimeMillis()+"");
        eventText.setText("EventBus:"+eventData.getMsg());
    }

    public void getRxBus(){
        rxBusDataObservable = RxBus.get().register("rxbus",RxBusData.class);
        rxBusDataObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RxBusData>() {
                    @Override
                    public void call(RxBusData rxBusData) {
                        Log.e("time",System.currentTimeMillis()+"");
                        eventText.setText("RxBus"+rxBusData.getMsg());
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        RxBus.get().unregister("rxbus",rxBusDataObservable);
    }
}
