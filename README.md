# BatteryView
自定义电池电量View

```java
  <com.wy.view.BatteryView
      android:id="@+id/verticalBattery"
      android:layout_width="100dp"
      android:layout_height="200dp"
      android:layout_marginTop="5dp"
      android:background="#fff"
      android:gravity="center"
      app:batteryColor="@android:color/white"
      app:batteryOrientation="vertical" />
      
      
  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      verticalBattery = (BatteryView) findViewById(R.id.verticalBattery);
      verticalBattery.setPower(40, true);
  }
 /**
   * 设置电池电量
   *
   * @param power    电量值（0-100）
   * @param isCharge 是否正在充电
   */
  public void setPower(int power, boolean isCharge);
```
