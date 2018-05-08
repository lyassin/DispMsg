package disp.ylehiani.com.playmsg;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ylehiani on 07/05/18.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MyViewHolder> {
    private List<Message> msgList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView msg;

        public MyViewHolder(View view) {
            super(view);
            msg = (TextView) view.findViewById(R.id.msg);
        }
    }

    public MsgAdapter(List<Message> msgList) {
        this.msgList = msgList;
    }

    @Override
    public MsgAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.msg_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MsgAdapter.MyViewHolder holder, int position) {
        Message msg = msgList.get(position);
        holder.msg.setText(msg.getMsg());

    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }
}
