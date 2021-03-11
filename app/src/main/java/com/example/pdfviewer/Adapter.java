package com.example.pdfviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;

public class Adapter extends ArrayAdapter<File> {
    Context context;
    ViewHolder viewHolder;
    ArrayList<File> pdf_array_list;

    public Adapter(@NonNull Context context, ArrayList<File> pdf_array_list) {
        super(context, R.layout.adapter_pdf,pdf_array_list);
        this.context = context;
        this.pdf_array_list = pdf_array_list;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (pdf_array_list.size() >0){
            return pdf_array_list.size();
        }else return 1;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.adapter_pdf,parent,false);
            viewHolder=new ViewHolder();

            viewHolder.filename_txtView =(TextView) convertView.findViewById(R.id.file_name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.filename_txtView.setText(pdf_array_list.get(position).getName());
        return convertView;
    }

    public class ViewHolder{

        TextView filename_txtView;
    }
}
