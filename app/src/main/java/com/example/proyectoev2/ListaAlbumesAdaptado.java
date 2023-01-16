package com.example.proyectoev2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListaAlbumesAdaptado extends BaseAdapter {

    private Context context;
    private int layaout;
    private ArrayList<Model> recordList;

    public ListaAlbumesAdaptado(Context context, int layaout, ArrayList<Model> recordList) {
        this.context = context;
        this.layaout = layaout;
        this.recordList = recordList;
    }

    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int position) {
        return recordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtTitulo,txtAutor,txtDiscografica;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = new ViewHolder();

        if (row==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            holder.txtTitulo = row.findViewById(R.id.txtTitulo);
            holder.txtAutor = row.findViewById(R.id.txtAutor);
            holder.txtDiscografica = row.findViewById(R.id.txtDiscografica);
            holder.imageView = row.findViewById(R.id.imgIcon);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        Model model = recordList.get(position);

        holder.txtTitulo.setText(model.getTitulo());
        holder.txtAutor.setText(model.getAutor());
        holder.txtDiscografica.setText(model.getDiscografica());

        byte[] recordImagen = model.getImagen();
        Bitmap bitmap = BitmapFactory.decodeByteArray(recordImagen,0,recordImagen.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
