package com.example.myallergy.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myallergy.Activities.AlarmActivity;
import com.example.myallergy.Activities.AllergySelectActivity;
import com.example.myallergy.Activities.MyMedicineActivity;
import com.example.myallergy.DataBase.Medicine;
import com.example.myallergy.DataBase.MedicineDAO;
import com.example.myallergy.DataBase.UserDataBase;
import com.example.myallergy.R;
import com.example.myallergy.Receiver.AlarmReceiver;


import java.util.ArrayList;
import java.util.List;


public class MyMedicineAdapter extends BaseAdapter {

    LayoutInflater mLayoutInflater;
    List<Medicine> medicineList;
    TextView textView;
    ImageButton btnAlarm, btnDelete;

    public MyMedicineAdapter() {
        medicineList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return medicineList.size();
    }
    @Override
    public Medicine getItem(int position) {
        return medicineList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();

        //뷰 초기화
        if(convertView == null) {
            mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mLayoutInflater.inflate(R.layout.my_medicine_item, viewGroup, false);
        }
        initializeViews(convertView);

        //약 이름으로 textview 설정
        final String medicineName = medicineList.get(position).getMedicineName();
        textView.setText(medicineName);

        //삭제 버튼 클릭 시
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogHandler(context, position);
                notifyDataSetChanged();
            }
        });
        //알람 설정 버튼

        btnAlarm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent2 = new Intent(v.getContext(), AlarmReceiver.class);
                intent2.putExtra("medicineName",medicineName);
                v.getContext().startActivity(intent2);
            }
        });

        return convertView;
    }

    public void initializeViews(View view) {
        textView = view.findViewById(R.id.my_medicine_name);
        btnAlarm = view.findViewById(R.id.btn_my_medicine_setAlarm);
        btnDelete = view.findViewById(R.id.btn_my_medicine_delete);
    }

    public void addMedicine(Medicine medicine) {
        medicineList.add(medicine);
    }

    //삭제 확인 알림창 팝업
    public void alertDialogHandler(final Context context, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("내 복용약에서 삭제");
        builder.setMessage("내 복용약에서 삭제하시겠습니까??");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                UserDataBase db = UserDataBase.getInstance(context);
                MedicineDAO medicineDAO = db.getMedicineDAO();
                //db에서 해당 약 삭제
                deleteThisMedicine(medicineDAO, medicineList.get(position));
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    public void updateMedicineList(List<Medicine> medicineList) {
        this.medicineList = medicineList;
    }

    public void deleteThisMedicine(final MedicineDAO medicineDAO, final Medicine medicine) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                //db에서 삭제하고 리스트 새로 받아옴
                medicineDAO.deleteMedicine(medicine);
                updateMedicineList(medicineDAO.getMedicineList());
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //리스트뷰 새로고침
        notifyDataSetChanged();
    }
}
