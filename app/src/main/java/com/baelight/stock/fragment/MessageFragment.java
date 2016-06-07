package com.baelight.stock.fragment;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baelight.stock.R;
import com.baelight.stock.app.StockApplication;
import com.baelight.stock.entity.Message;
import com.baelight.stock.helper.MessageHelper;

import java.util.List;

/**
 * Created by Nick on 2016/6/2.
 */
public class MessageFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter adapter;

    private List<Message> messages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_message, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.message_list);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(linearLayoutManager);
        }
        messages = MessageHelper.getInstance(StockApplication.getAppContext()).getMessages();
        updateAdapter();
    }

    public void updateAdapter() {
        if (adapter == null) {
            adapter = new MessageAdapter();
        }
        if (recyclerView.getAdapter() == null) {
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.item_list_fragment_message, parent, false);
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Message message = messages.get(position);
            holder.binding.setVariable(com.baelight.stock.BR.message, message);
            holder.binding.executePendingBindings();
            holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO
                }
            });
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            final ViewDataBinding binding;
            ViewHolder(ViewDataBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}
