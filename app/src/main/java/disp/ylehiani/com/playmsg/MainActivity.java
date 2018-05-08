package disp.ylehiani.com.playmsg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private List<Message> msgList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MsgAdapter mAdapter;
    private EditText mEditTextText;
    private Button mButton;
    private String str;
    private int i = 0;
    private int mSize = 0;
    private String mTextSize = "";
    private String mTextMsg = "";
    private boolean mMsgBeginWithDolar = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new MsgAdapter(msgList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        mEditTextText = (EditText)findViewById(R.id.edit_msg);

        mButton = findViewById(R.id.disp_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                str  = mEditTextText.getText().toString();
                mEditTextText.setText("");
                handle(str);
            }

        });
    }

    void init() {
        mSize = 0;
        mTextSize = "";
        mTextMsg = "";
        mMsgBeginWithDolar = false;
    }

    private void handle(String str) {
        if (str.length() > 0 && i < 100) {
            if (mSize == 0) {
                if (mTextSize.length() < 3 && mMsgBeginWithDolar) {
                    Log.d(TAG,"init size");
                    mTextSize += str.substring(0, (3 - mTextSize.length() < str.length() ? 3 - mTextSize.length() : str.length()));
                    if (mTextSize.length() == 3) {
                        try {
                            mSize = Integer.valueOf(mTextSize);
                        } catch (NumberFormatException e) {
                            Log.d(TAG,"bad syntax reste" +str);
                            Toast.makeText(this, "Bad Syntax",
                                    Toast.LENGTH_LONG).show();
                            init();

                        }
                        Log.d(TAG,"mSize = " + mSize);
                        mTextSize = "";
                    }
                    handle(str.substring((3 - mTextSize.length() < str.length() ? 3 - mTextSize.length() : str.length()), str.length()));
                } else if (mTextSize.length() == 0 && !mMsgBeginWithDolar && str.substring(0, 1).equals("$")) {
                    mMsgBeginWithDolar = true;
                    handle(str.substring(1, str.length()));
                } else {
                    Toast.makeText(this, "Bad Syntax",
                            Toast.LENGTH_LONG).show();
                    init();

                }

            } else {
                int size = mSize - mTextMsg.length();
                mTextMsg += str.substring(0, ( size < str.length() ? size : str.length()));
                Log.d(TAG,"add text = " + mTextMsg);
                if(mTextMsg.length() == mSize) {
                    Message msg = new Message(mTextMsg);
                    msgList.add(msg);
                    i++;
                    mAdapter.notifyDataSetChanged();
                    init();
                }
                handle(str.substring(size  < str.length() ? size : str.length(), str.length()));

            }

        }
    }


}
