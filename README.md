# NetView

![image](https://github.com/HpWens/NetView/blob/master/app/src/main/assets/view1.png)

![image](https://github.com/HpWens/NetView/blob/master/app/src/main/assets/view2.png)

#using

##xml文件

    <com.github.ws.linedemo.widget.NetView
        android:id="@+id/netView"
        widget:textSize="32"
        widget:overlayColor="#abc123"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
        
##Activity

```
public class MainActivity extends AppCompatActivity {
    private NetView netView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        netView= (NetView) findViewById(R.id.netView);
        netView.setTextArr(new String[]{"数学", "语文", "外语", "地理", "历史", "化学"});
        netView.setOverlayPercent(new float[]{0.3f, 0.8f, 0.9f, 0.6f, 0.7f, 1.0f, 1f});
    }
}
```
