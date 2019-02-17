package com.kcirqueit.spinadmin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kcirqueit.spinadmin.PaymentDetailsActivity;
import com.kcirqueit.spinadmin.PaymentRequestActivity;
import com.kcirqueit.spinadmin.R;
import com.kcirqueit.spinadmin.model.PaymentRequest;
import com.kcirqueit.spinadmin.model.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {

    private Context context;
    private List<PaymentRequest> paymentRequestList;


    public PaymentAdapter(Context context, List<PaymentRequest> paymentRequestList) {
        this.context = context;
        this.paymentRequestList = paymentRequestList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_payment_request_item,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PaymentRequest paymentRequest = paymentRequestList.get(position);
        holder.userNameTv.setText(paymentRequest.getName());
        holder.payAmountTv.setText("Withdraw Amount: "+paymentRequest.getWithdrawPoint()+"");
        holder.payStatusTv.setText(paymentRequest.getTransactionStatus());
        if (paymentRequest.getTransactionStatus().equals("completed")) {
            holder.payStatusTv.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_back_green));
        }
        holder.userMobileTv.setText("Mobile No: "+paymentRequest.getMobileNo());


        holder.paymentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
                Intent paymentIntent = new Intent(context, PaymentDetailsActivity.class);
                paymentIntent.putExtra("paymentRequest", paymentRequest);
                context.startActivity(paymentIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return paymentRequestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;

        @BindView(R.id.user_name_tv)
        TextView userNameTv;

        @BindView(R.id.user_mobile_tv)
        TextView userMobileTv;

        @BindView(R.id.pay_amount_tv)
        TextView payAmountTv;

        @BindView(R.id.pay_status_tv)
        TextView payStatusTv;

        @BindView(R.id.payment_layout)
        RelativeLayout paymentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            this.itemView = itemView;
            


        }
    }
}
